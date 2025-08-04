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

package top.continew.admin.blog.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;
import top.continew.starter.extension.crud.annotation.DictModel;

import java.io.Serial;

/**
 * 标签实体
 *
 * @author weilai
 * @since 2025/07/08 13:45
 */
@Data
@TableName("cc_tag")
@DictModel(labelKey = "name")
public class TagDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    private String name;

    private String imgUrl;
}
