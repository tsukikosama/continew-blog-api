package top.continew.admin.blog.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.blog.model.query.CustomerQuery;
import top.continew.admin.blog.model.req.CustomerReq;
import top.continew.admin.blog.model.resp.CustomerDetailResp;
import top.continew.admin.blog.model.resp.CustomerResp;
import top.continew.admin.blog.service.CustomerService;

/**
 * 用户管理 API
 *
 * @author weilai
 * @since 2025/07/09 15:15
 */
@Tag(name = "用户管理 API")
@RestController
@CrudRequestMapping(value = "/blog/customer", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class CustomerController extends BaseController<CustomerService, CustomerResp, CustomerDetailResp, CustomerQuery, CustomerReq> {}