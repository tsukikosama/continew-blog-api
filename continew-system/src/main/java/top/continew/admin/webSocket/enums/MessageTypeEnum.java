package top.continew.admin.webSocket.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import top.continew.admin.common.constant.UiConstants;
import top.continew.starter.core.enums.BaseEnum;

@Getter
@RequiredArgsConstructor
public enum MessageTypeEnum{
    CHAT_MESSAGE(1,"chat", "文本消息"),
    BULLET_MESSAGE(2,"bullet", "弹幕消息"),
    EXCEPTION(-999,"exception", "未知类型"),
    SYSTEM_MESSAGE(999,"system", "系统消息");
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
    public static MessageTypeEnum from(Integer value) {
        if (value == null) {
            return null;
        }
        for (MessageTypeEnum type : MessageTypeEnum.values()) {
            if (type.getCode().equals(value)) {
                return type;
            }
        }
        return EXCEPTION;
    }

}
