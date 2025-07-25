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

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 评论实体
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
@Data
@TableName("cc_review")
public class ReviewDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likes;

    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 回复的是哪个评论的id
     */
    private Long replyId;

    /**
     * 评论用户id
     */
    private Long userId;

    /**
     * 评论的类型 0是主评论1是子评论
     */
    private Integer reviewType;

    /**
     * 回复的用户id
     */
    private Long replyUserId;

    @TableField(exist = false)
    private Long createUser;

    @TableField(exist = false)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableField(exist = false)
    private Long updateUser;

    /**
     * 修改时间
     */
    @TableField(exist = false)
    private LocalDateTime updateTime;

}
