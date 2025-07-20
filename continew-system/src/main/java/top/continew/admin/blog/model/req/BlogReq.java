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

import org.hibernate.validator.constraints.Length;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;
import java.util.List;

/**
 * 博客 创建或修改参数
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Data
@Schema(description = "博客 创建或修改参数")
public class BlogReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    @Length(max = 128, message = "标题长度不能超过 {max} 个字符")
    private String title;

    /**
     * 图片
     */
    @Schema(description = "图片")
    @Length(max = 128, message = "图片长度不能超过 {max} 个字符")
    private String picture;

    /**
     * 内容
     */
    @Schema(description = "内容")
    @NotBlank(message = "内容不能为空")
    @Length(max = 2147483647, message = "内容长度不能超过 {max} 个字符")
    private String content;

    /**
     * 简化标题
     */
    @Schema(description = "简化标题")
    @NotBlank(message = "简化标题不能为空")
    @Length(max = 128, message = "简化标题长度不能超过 {max} 个字符")
    private String simpleTitle;

    /**
     * 0保存 1发布
     */
    @Schema(description = "0保存 1发布")
    @NotNull(message = "0保存 1发布不能为空")
    private Integer status;

    /**
     * 作者
     */
    @Schema(description = "作者")
    private Long userId;

    @Schema(description = "标签id")
    @NotNull(message = "至少要有一个标签")
    private List<Long> tagId;
}