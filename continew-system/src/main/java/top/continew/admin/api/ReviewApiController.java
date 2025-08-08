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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.blog.model.query.ReviewQuery;
import top.continew.admin.blog.model.req.ApiReviewReq;
import top.continew.admin.blog.model.req.ReviewReq;
import top.continew.admin.blog.model.resp.ApiNearlyReviewResp;
import top.continew.admin.blog.model.resp.ReviewDetailResp;
import top.continew.admin.blog.model.resp.ReviewResp;
import top.continew.admin.blog.service.CustomerService;
import top.continew.admin.blog.service.ReviewService;
import top.continew.admin.common.controller.BaseController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.BasePageResp;

import java.util.List;

/**
 * 评论管理 API
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
@Tag(name = "评论API")
@RestController
@CrudRequestMapping(value = "/api/review", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
@RequiredArgsConstructor
public class ReviewApiController extends BaseController<ReviewService, ReviewResp, ReviewDetailResp, ReviewQuery, ReviewReq> {

    private final CustomerService customerService;

    @SaIgnore
    @Override
    public BasePageResp<ReviewResp> page(ReviewQuery query, PageQuery pageQuery) {
        query.setReviewType(0);
        BasePageResp<ReviewResp> page = super.page(query, pageQuery);
        page.getList().forEach(item -> {
            item.setUserAvatar(customerService.get(item.getUserId()).getAvatar());
            List<ReviewResp> list = this.baseService.getChildReview(item.getId());
            item.setChildren(list);
        });
        return page;
    }

    @PostMapping("/reply")
    @Operation(summary = "评论", description = "评论")
    public void review(@RequestBody ApiReviewReq req) {
        this.baseService.review(req);
    }

    @GetMapping("/nearly")
    @Operation(summary = "最近评论", description = "最近评论")
    public List<ApiNearlyReviewResp> nearly() {
        return this.baseService.nearly();
    }
}