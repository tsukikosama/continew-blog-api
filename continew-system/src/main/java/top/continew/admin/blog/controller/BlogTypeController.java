package top.continew.admin.blog.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.blog.model.query.BlogTypeQuery;
import top.continew.admin.blog.model.req.BlogTypeReq;
import top.continew.admin.blog.model.resp.BlogTypeDetailResp;
import top.continew.admin.blog.model.resp.BlogTypeResp;
import top.continew.admin.blog.service.BlogTypeService;

/**
 * 分类管理 API
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
@Tag(name = "分类管理 API")
@RestController
@CrudRequestMapping(value = "/admin/blogType", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class BlogTypeController extends BaseController<BlogTypeService, BlogTypeResp, BlogTypeDetailResp, BlogTypeQuery, BlogTypeReq> {}