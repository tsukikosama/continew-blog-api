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

package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;
import java.io.Serial;
import java.time.*;

/**
 * 评论详情信息
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "评论详情信息")
public class ReviewDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    @ExcelProperty(value = "评论内容")
    private String content;

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    @ExcelProperty(value = "点赞数")
    private Integer likes;

    /**
     * 博客id
     */
    @Schema(description = "博客id")
    @ExcelProperty(value = "博客id")
    private Long blogId;

    /**
     * 回复的是哪个评论的id
     */
    @Schema(description = "回复的是哪个评论的id")
    @ExcelProperty(value = "回复的是哪个评论的id")
    private Long replyId;

    /**
     * 评论用户id
     */
    @Schema(description = "评论用户id")
    @ExcelProperty(value = "评论用户id")
    private Long userId;

    /**
     * 评论的类型 0是主评论1是子评论
     */
    @Schema(description = "评论的类型 0是主评论1是子评论")
    @ExcelProperty(value = "评论的类型 0是主评论1是子评论")
    private Integer reviewType;

    /**
     * 回复的用户id
     */
    @Schema(description = "回复的用户id")
    @ExcelProperty(value = "回复的用户id")
    private Long replyUserId;
}