package top.continew.admin.api;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.req.CustomerLoginReq;
import top.continew.admin.blog.model.req.CustomerReq;
import top.continew.admin.blog.model.resp.ApiBlogResp;
import top.continew.admin.blog.model.resp.ApiCustomerResp;
import top.continew.admin.blog.model.resp.CustomerDetailResp;
import top.continew.admin.blog.service.CustomerService;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.BasePageResp;

@Tag(name = "用户API")
@RestController
@RequestMapping(value = "/api/customer")
@RequiredArgsConstructor
public class CustomerApiController {
    private final CustomerService customerService;

    @PostMapping("/register")
    @Operation(summary = "注册", description = "注册")
    @SaIgnore
    public Void page(@RequestBody CustomerReq req){
        return customerService.register(req);
    }

    @PostMapping("/login")
    @Operation(summary = "登录", description = "登录")
    @SaIgnore
    public String login(@RequestBody CustomerLoginReq req){
        return customerService.loginCustomer(req);
    }

    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "获取用户信息")
    public ApiCustomerResp info(){
        CustomerDetailResp customerDetailResp = customerService.get(StpUtil.getLoginIdAsLong());
        return BeanUtil.copyProperties(customerDetailResp, ApiCustomerResp.class);
    }

}
