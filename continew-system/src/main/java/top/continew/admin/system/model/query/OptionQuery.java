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

package top.continew.admin.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.continew.admin.system.enums.OptionCategoryEnum;
import top.continew.starter.core.validation.constraints.EnumValue;
import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 参数查询条件
 *
 * @author Bull-BCLS
 * @since 2023/8/26 19:38
 */
@Data
@Schema(description = "参数查询条件")
public class OptionQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 键列表
     */
    @Schema(description = "键列表", example = "SITE_TITLE,SITE_COPYRIGHT")
    @Query(type = QueryType.IN)
    private List<String> code;

    /**
     * 类别
     */
    @Schema(description = "类别", example = "SITE")
    @EnumValue(value = OptionCategoryEnum.class, message = "类别无效")
    private String category;
}