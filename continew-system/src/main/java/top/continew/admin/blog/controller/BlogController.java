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

package top.continew.admin.blog.controller;

import lombok.AllArgsConstructor;
import top.continew.admin.blog.model.entity.BlogTypeDO;
import top.continew.admin.blog.service.BlogTypeService;
import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.req.BlogReq;
import top.continew.admin.blog.model.resp.BlogDetailResp;
import top.continew.admin.blog.model.resp.BlogResp;
import top.continew.admin.blog.service.BlogService;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.req.IdsReq;
import top.continew.starter.extension.crud.model.resp.BasePageResp;

import java.util.List;

/**
 * 博客 管理 API
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Tag(name = "博客 管理 API")
@RestController
@CrudRequestMapping(value = "/admin/blog", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
@AllArgsConstructor
public class BlogController extends BaseController<BlogService, BlogResp, BlogDetailResp, BlogQuery, BlogReq> {

    private final BlogTypeService blogTypeService;
    @Override
    public BasePageResp<BlogResp> page(BlogQuery query, PageQuery pageQuery) {
        BasePageResp<BlogResp> page = super.page(query, pageQuery);
        page.getList().forEach(item -> {
            List<BlogTypeDO> blogTagByBlogId = blogTypeService.getBlogTagByBlogId(item.getId());
            item.setTagId(blogTagByBlogId.stream().map(BlogTypeDO::getTagId).toList());
        });
        return page;
    }

    @Override
    public void delete(IdsReq req) {
        super.delete(req);
    }

    @Override
    public BlogDetailResp get(Long id) {
        BlogDetailResp blogDetailResp = super.get(id);
        List<BlogTypeDO> blogTagByBlogId = blogTypeService.getBlogTagByBlogId(blogDetailResp.getId());
        blogDetailResp.setTagId(blogTagByBlogId.stream().map(BlogTypeDO::getTagId).toList());
        return blogDetailResp;
    }
}