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

import java.io.Serial;
import java.time.*;

/**
 * 友链实体
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
@Data
@TableName("cc_friendlink")
public class FriendlinkDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网站链接
     */
    private String webUrl;

    /**
     * 网站名字
     */
    private String webName;

    /**
     * 网站描述
     */
    private String webDescript;

    /**
     * 添加时间
     */
    private LocalDate webTime;

    /**
     * 网站图片
     */
    private String webImg;

    /**
     * 0为为审核通过 1为审核通过
     */
    private Integer webAccess;

    /**
     * 用户邮箱
     */
    private String webEmail;
}
