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

package top.continew.admin.system.model.req.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.continew.admin.common.constant.RegexConstants;
import top.continew.admin.common.enums.DisEnableStatusEnum;
import top.continew.admin.common.enums.GenderEnum;
import top.continew.starter.core.validation.constraints.Mobile;
import top.continew.starter.extension.crud.validation.CrudValidationGroup;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户创建或修改请求参数
 *
 * @author Charles7c
 * @since 2023/2/20 21:03
 */
@Data
@Schema(description = "用户创建或修改请求参数")
public class UserReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "zhangsan")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = RegexConstants.USERNAME, message = "用户名长度为 4-64 个字符，支持大小写字母、数字、下划线，以字母开头")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_NAME, message = "昵称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    private String nickname;

    /**
     * 密码（加密）
     */
    @Schema(description = "密码（加密）", example = "E7c72TH+LDxKTwavjM99W1MdI9Lljh79aPKiv3XB9MXcplhm7qJ1BJCj28yaflbdVbfc366klMtjLIWQGqb0qw==")
    @NotBlank(message = "密码不能为空", groups = CrudValidationGroup.Create.class)
    private String password;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "123456789@qq.com")
    @Length(max = 255, message = "邮箱长度不能超过 {max} 个字符")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13811111111")
    @Mobile
    private String phone;

    /**
     * 性别
     */
    @Schema(description = "性别", example = "1")
    @NotNull(message = "性别无效")
    private GenderEnum gender;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门", example = "5")
    @NotNull(message = "所属部门不能为空")
    private Long deptId;

    /**
     * 所属角色
     */
    @Schema(description = "所属角色", example = "2")
    @NotEmpty(message = "所属角色不能为空")
    private List<Long> roleIds;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "张三描述信息")
    @Length(max = 200, message = "描述长度不能超过 {max} 个字符")
    private String description;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;
}
