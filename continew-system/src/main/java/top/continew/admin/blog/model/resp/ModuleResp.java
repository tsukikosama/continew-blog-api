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

package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;
import java.io.Serial;
import java.time.*;

/**
 * 模块信息
 *
 * @author weilai
 * @since 2025/07/20 10:56
 */
@Data
@Schema(description = "模块信息")
public class ModuleResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模块名字
     */
    @Schema(description = "模块名字")
    private String moduleName;

    /**
     * 0表示没有开发的 ， 1 表示开发完成的
     */
    @Schema(description = "0表示没有开发的 ，  1 表示开发完成的")
    private Integer moduleStatus;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private Long updateUser;
}