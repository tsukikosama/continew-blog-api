/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.system.model.resp.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.continew.admin.system.enums.MessageTypeEnum;

import java.io.Serial;
import java.io.Serializable;

/**
 * 各类型未读消息响应参数
 *
 * @author Charles7c
 * @since 2023/11/2 23:00
 */
@Data
@Schema(description = "各类型未读消息响应参数")
public class MessageTypeUnreadResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "1")
    private MessageTypeEnum type;

    /**
     * 数量
     */
    @Schema(description = "数量", example = "10")
    private Long count;
}