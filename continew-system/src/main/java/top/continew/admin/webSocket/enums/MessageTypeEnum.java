package top.continew.admin.webSocket.enums;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import top.continew.admin.common.constant.UiConstants;
import top.continew.starter.core.enums.BaseEnum;

@Getter
@RequiredArgsConstructor
public enum MessageTypeEnum{
    TEXT_MESSAGE("1", "文本消息");

    private final String value;
    private final String description;
}
