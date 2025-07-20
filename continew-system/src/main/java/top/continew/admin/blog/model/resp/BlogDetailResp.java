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
 * 博客 详情信息
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "博客 详情信息")
public class BlogDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 图片
     */
    @Schema(description = "图片")
    @ExcelProperty(value = "图片")
    private String picture;

    /**
     * 内容
     */
    @Schema(description = "内容")
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 流量数量
     */
    @Schema(description = "流量数量")
    @ExcelProperty(value = "流量数量")
    private Integer visit;

    /**
     * 简化标题
     */
    @Schema(description = "简化标题")
    @ExcelProperty(value = "简化标题")
    private String simpleTitle;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 0保存 1发布
     */
    @Schema(description = "0保存 1发布")
    @ExcelProperty(value = "0保存 1发布")
    private Integer status;
}