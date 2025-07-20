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

/**
 * 友链创建或修改参数
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
@Data
@Schema(description = "友链创建或修改参数")
public class FriendlinkReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网站链接
     */
    @Schema(description = "网站链接")
    @NotBlank(message = "网站链接不能为空")
    @Length(max = 255, message = "网站链接长度不能超过 {max} 个字符")
    private String webUrl;

    /**
     * 网站名字
     */
    @Schema(description = "网站名字")
    @NotBlank(message = "网站名字不能为空")
    @Length(max = 100, message = "网站名字长度不能超过 {max} 个字符")
    private String webName;

    /**
     * 网站图片
     */
    @Schema(description = "网站图片")
    @NotBlank(message = "网站图片不能为空")
    @Length(max = 255, message = "网站图片长度不能超过 {max} 个字符")
    private String webImg;

    /**
     * 0为为审核通过 1为审核通过
     */
    @Schema(description = "0为为审核通过 1为审核通过")
    @NotNull(message = "0为为审核通过 1为审核通过不能为空")
    private Integer webAccess;
    /**
     * 网站描述
     */
    @Schema(description = "网站描述")
    private Integer webDescript;
    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    @NotBlank(message = "用户邮箱不能为空")
    @Email
    @Length(max = 255, message = "用户邮箱长度不能超过 {max} 个字符")
    private String webEmail;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    //    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    //    @NotNull(message = "创建人不能为空")
    private Long createUser;
}