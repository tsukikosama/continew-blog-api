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
 * 友链查询条件
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
@Data
@Schema(description = "友链查询条件")
public class FriendlinkQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网站名字
     */
    @Schema(description = "网站名字")
    @Query(type = QueryType.EQ)
    private String webName;

    /**
     * 0为为审核通过 1为审核通过
     */
    @Schema(description = "0为为审核通过 1为审核通过")
    @Query(type = QueryType.EQ)
    private Integer webAccess;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    @Query(type = QueryType.EQ)
    private String webEmail;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @Query(type = QueryType.EQ)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @Query(type = QueryType.EQ)
    private Long createUser;
}