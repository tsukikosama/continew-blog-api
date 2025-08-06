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

package top.continew.admin.api;

import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.resp.ApiBlogResp;
import top.continew.admin.blog.service.BlogService;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.BasePageResp;

import java.util.List;

@Tag(name = "博客API")
@RestController
@RequestMapping(value = "/api/blog")
@RequiredArgsConstructor
public class BlogApiController {
    private final BlogService blogService;


    @SaIgnore
    @GetMapping("/page")
    @Operation(summary = "分页查询列表", description = "分页查询列表")
    public BasePageResp<ApiBlogResp> page(BlogQuery query, PageQuery pageQuery) {
        return blogService.customPageApi(query, pageQuery);
    }

    @SaIgnore
    @GetMapping("/recent")
    @Operation(summary = "获取最近的5条博客", description = "获取最近的5条博客")
    public List<ApiBlogResp> recentBlog() {
        return blogService.getRecentBlog();
    }

    @SaIgnore
    @GetMapping("/{blogId}")
    @Operation(summary = "获取博客详情", description = "获取博客详情")
    public ApiBlogResp detail(@PathVariable("blogId") Long blogId) {
        return blogService.getBlogByBlogId(blogId);
    }

//    @GetMapping("/my/blog")
//    @Operation(summary = "查询自己的博客", description = "查询自己的博客")
//    public BasePageResp<ApiBlogResp> myBlog(@ParameterObject BlogQuery query, @ParameterObject PageQuery pageQuery) {
//        return blogService.customPage(query, pageQuery);
//    }
//
//    @GetMapping("/tag/{tagId}")
//    @Operation(summary = "通过标签来查询博客", description = "通过标签来查询博客")
//    public BasePageResp<ApiBlogResp> getBlogByTag(@PathVariable("tagId") Long tagId, PageQuery pageQuery) {
//        return blogService.customPageByTagId(tagId, pageQuery);
//    }

}
