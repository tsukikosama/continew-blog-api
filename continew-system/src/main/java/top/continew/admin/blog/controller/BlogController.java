package top.continew.admin.blog.controller;

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
import top.continew.starter.extension.crud.model.resp.BasePageResp;

/**
 * 博客 管理 API
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Tag(name = "博客 管理 API")
@RestController
@CrudRequestMapping(value = "/admin/blog", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class BlogController extends BaseController<BlogService, BlogResp, BlogDetailResp, BlogQuery, BlogReq> {
    @Override
    public BasePageResp<BlogResp> page(BlogQuery query, PageQuery pageQuery) {
        return super.page(query, pageQuery);
    }
}