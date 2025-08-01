package top.continew.admin.webSocket.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 发送的范围
 */
@Getter
@RequiredArgsConstructor
public enum MessageScopeEnum {
    RADIO_MESSAGE(1,"radio", "广播消息"),
    GROUP_MESSAGE(2,"group", "群发消息"),
    PERSONAL_MESSAGE(3,"personal", "个人消息"),
    EXCEPTION(-999,"exception", "未识别消息"),
    SYSTEM_MESSAGE(999,"system", "系统消息")
    ;

    private final Integer code;
    private final String value;
    private final String description;

    @JsonValue
    public Integer getCode() {
        return code;
    }

    /**
     * 这个注解 标注的静态方法会被 Jackson 调用反序列化时使用
     * @param value
     * @return
     */
    @JsonCreator
    public static MessageScopeEnum from(Integer value) {
        if (value == null) {
            return null;
        }
        for (MessageScopeEnum type : MessageScopeEnum.values()) {
            if (type.getCode().equals(value)) {
                return type;
            }
        }
        return EXCEPTION;
    }

}
