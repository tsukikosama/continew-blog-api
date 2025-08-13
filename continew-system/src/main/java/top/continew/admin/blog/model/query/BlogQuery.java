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
import java.util.List;

/**
 * 博客 查询条件
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Data
@Schema(description = "博客 查询条件")
public class BlogQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @Query(type = QueryType.LIKE)
    private String title;


    /**
     * 0保存 1发布
     */
    @Schema(description = "0保存 1发布")
    private Integer status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private List<LocalDateTime> createTime;


    @Schema(description = "创建用户")
    private Long userId;


    @Schema(description = "标签id")
    private Long tagId;

}