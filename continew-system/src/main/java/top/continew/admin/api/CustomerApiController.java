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
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.continew.admin.blog.model.req.ApiCustomerUpdateReq;
import top.continew.admin.blog.model.req.CustomerLoginReq;
import top.continew.admin.blog.model.req.CustomerReq;
import top.continew.admin.blog.model.resp.ApiCustomerResp;
import top.continew.admin.blog.model.resp.CustomerDetailResp;
import top.continew.admin.blog.service.BlogService;
import top.continew.admin.blog.service.CustomerService;

@Tag(name = "用户API")
@RestController
@RequestMapping(value = "/api/customer")
@RequiredArgsConstructor
public class CustomerApiController {
    private final CustomerService customerService;

    private final BlogService blogService;

    @PostMapping("/register")
    @Operation(summary = "注册", description = "注册")
    @SaIgnore
    public Void page(@RequestBody CustomerReq req) {
        return customerService.register(req);
    }

    @PostMapping("/login")
    @Operation(summary = "登录", description = "登录")
    @SaIgnore
    public String login(@RequestBody CustomerLoginReq req) {
        return customerService.loginCustomer(req);
    }

    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "获取用户信息")
    public ApiCustomerResp info() {
        CustomerDetailResp customerDetailResp = customerService.get(StpUtil.getLoginIdAsLong());
        ApiCustomerResp apiCustomerResp = BeanUtil.copyProperties(customerDetailResp, ApiCustomerResp.class);
        ApiCustomerResp apiCustomerResp2 = blogService.getUserBlogDateById(StpUtil.getLoginIdAsLong());
        apiCustomerResp.setMonthCount(apiCustomerResp2.getMonthCount());
        apiCustomerResp.setBlogCount(apiCustomerResp2.getBlogCount());
        apiCustomerResp.setWeekCount(apiCustomerResp2.getWeekCount());
        return apiCustomerResp;
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户信息", description = "更新用户信息")
    public Void update(@RequestBody ApiCustomerUpdateReq req) {
        customerService.updateCustomer(req);
        return null;
    }

}
