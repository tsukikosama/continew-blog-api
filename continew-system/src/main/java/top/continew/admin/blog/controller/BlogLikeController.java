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

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.blog.model.query.BlogLikeQuery;
import top.continew.admin.blog.model.req.BlogLikeReq;
import top.continew.admin.blog.model.resp.BlogLikeDetailResp;
import top.continew.admin.blog.model.resp.BlogLikeResp;
import top.continew.admin.blog.service.BlogLikeService;

/**
 * 点赞管理 API
 *
 * @author weilai
 * @since 2025/07/08 15:03
 */
@Tag(name = "点赞管理 API")
@RestController
@CrudRequestMapping(value = "/blog/blogLike", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class BlogLikeController extends BaseController<BlogLikeService, BlogLikeResp, BlogLikeDetailResp, BlogLikeQuery, BlogLikeReq> {

}