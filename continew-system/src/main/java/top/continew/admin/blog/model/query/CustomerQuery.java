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

package top.continew.admin.blog.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 用户查询条件
 *
 * @author weilai
 * @since 2025/07/09 15:15
 */
@Data
@Schema(description = "用户查询条件")
public class CustomerQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @Query(type = QueryType.EQ)
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @Query(type = QueryType.EQ)
    private String password;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Query(type = QueryType.EQ)
    private String email;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @Query(type = QueryType.EQ)
    private LocalDateTime createTime;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @Query(type = QueryType.EQ)
    private String nickname;
}