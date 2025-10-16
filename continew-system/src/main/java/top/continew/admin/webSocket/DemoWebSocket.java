package top.continew.admin.webSocket;

import com.alibaba.fastjson.JSON;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.continew.admin.webSocket.enums.MessageScopeEnum;
import top.continew.admin.webSocket.enums.MessageTypeEnum;
import top.continew.admin.webSocket.message.HeartConfig;
import top.continew.admin.webSocket.message.WsMessageEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static io.undertow.util.URLUtils.parseQueryString;

@ServerEndpoint("/ws/demo")
@Component
@Slf4j
public class DemoWebSocket {

    private static   Map<String, Map<Session, HeartConfig>> sessionPool = new HashMap<>();

    // 连接与key的反向索引，用于断开连接时清理
    private static   Map<Session, String> sessionKeyMap = new ConcurrentHashMap<>() ;

    /**
     * 连接的时候触发
     * @param session
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info("{}",session);
        //可以获取传递参数
        String query = session.getQueryString();
        Map<String, String> paramMap = parseQueryString(query);
        String key = paramMap.get("key");
        sessionKeyMap.putIfAbsent(session,key);
        sessionPool.computeIfAbsent(key, k -> new ConcurrentHashMap<>()).put(session,new HeartConfig(3,System.currentTimeMillis()));
        //如果不存在就存入
        log.info("数据{}存放在连接池{}",session,key);
    }

    @OnClose
    public void onClose(Session session) {
        //先获取当前的key
        String key = sessionKeyMap.get(session);
        if (key == null){
            log.error("连接池中不存在该连接");
        }
        Set<Session> sessions = sessionPool.get(key).keySet();
        sessions.remove(session);
        log.info("断开连接:{} 已经移除{}",session.getId(),session.getId());
    }

    /**
     * 接收消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        WsMessageEntity msg = ConversionWsMessage(message);
        //获取当前的连接池
        String key = sessionKeyMap.get(session);
        Set<Session> sessions = sessionPool.get(key).keySet();

        //判断是什么类型的消息 进行对应的操作
        // 目前只有广播和系统消息
        // 广播消息
        if (msg.getType().equals(MessageTypeEnum.SYSTEM_MESSAGE)){
            //这里是系统消息
            //判断是否是心跳消息
            if(msg.getContent().equals("ping")){
                //心跳消息 获重置心跳
                sessionPool.get(key).get(session).reset();
//                log.info("{}收到心跳消息",session.getId());
                //返回一个pong给客户端
                try {
                    String s = sendSystemMessage("pong");
                    session.getBasicRemote().sendText(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }else{
            //其他的消息  这里目前只实现了广播消息
            for (Session s : sessions) {
                try {
                    //要排除自己
                    if (s == session) {
                        continue;
                    }
                    WsMessageEntity entity = new WsMessageEntity();
                    entity.setType(MessageTypeEnum.BULLET_MESSAGE);
                    entity.setScope(MessageScopeEnum.RADIO_MESSAGE);
                    entity.setContent(msg.getContent());
                    s.getBasicRemote().sendText(Jackson.toJsonString(entity));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("连接错误：" + session.getId());
        error.printStackTrace();
    }

    private Map<String, String> parseQueryString(String query) {
        Map<String, String> map = new HashMap<>();
        if (query != null) {
            for (String pair : query.split("&")) {
                String[] kv = pair.split("=");
                if (kv.length == 2) {
                    map.put(kv[0], kv[1]);
                }
            }
        }
        return map;
    }


    public WsMessageEntity ConversionWsMessage(String msg) {
        ObjectMapper objectMapper = new ObjectMapper();
        WsMessageEntity wsMessage = new WsMessageEntity();
        try {
            wsMessage = objectMapper.readValue(msg, WsMessageEntity.class);
//            log.info("当前消息的内容{}",wsMessage);
        }catch (JsonProcessingException e){
            log.info("消息转换异常{}",msg);
        }

      return wsMessage;
    }

    /**
     * 定时检查心跳
     */
    // 每 30 秒执行一次心跳检查
    @Scheduled(fixedDelay = 1000 * 30)
    public void checkHeartBeat() {
        long now = System.currentTimeMillis();
        for (Map.Entry<String, Map<Session, HeartConfig>> groupEntry : sessionPool.entrySet()) {
            Map<Session, HeartConfig> sessionMap = groupEntry.getValue();
            for (Map.Entry<Session, HeartConfig> entry : sessionMap.entrySet()) {
                HeartConfig config = entry.getValue();
                long lastTime = config.getLastHeartTime();
                if ((now - lastTime) > config.getLastHeartTime()) {
                    // 断线处理逻辑
                    HeartConfig value = entry.getValue();
                    value.setMissHeartCount(value.getMissHeartCount()+1);
                    entry.setValue(value);
                    if (value.getLastHeartTime() >= 3) {
                        sessionMap.remove(entry.getKey());
                    }
                }
            }
        }
    }

    public String sendSystemMessage(String msg) throws IOException {
        WsMessageEntity entity = new WsMessageEntity();
        entity.setType(MessageTypeEnum.SYSTEM_MESSAGE);
        entity.setContent(msg);
        entity.setScope(MessageScopeEnum.SYSTEM_MESSAGE);
        return Jackson.toJsonString(entity);
    }

}
