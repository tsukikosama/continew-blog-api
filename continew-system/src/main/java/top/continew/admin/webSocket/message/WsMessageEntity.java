package top.continew.admin.webSocket.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.continew.admin.webSocket.enums.MessageScopeEnum;
import top.continew.admin.webSocket.enums.MessageTypeEnum;

@Data
@Schema(description = "消息实体")
public class WsMessageEntity {
    @Schema(description = "消息类型")
    private MessageTypeEnum type;

    @Schema(description = "发送范围")
    private MessageScopeEnum scope;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "消息发送者")
    private String sender;

    @Schema(description = "消息接收者")
    private String receiver;
}
