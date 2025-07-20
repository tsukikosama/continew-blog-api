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

package top.continew.admin.blog.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 分类创建或修改参数
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
@Data
@Schema(description = "分类创建或修改参数")
public class BlogTypeReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @Schema(description = "博客id")
    @NotNull(message = "博客id不能为空")
    private Long blogId;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    @NotNull(message = "标签id不能为空")
    private Long tagId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @NotNull(message = "创建人不能为空")
    private Long createUser;
}