package top.continew.admin.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.resp.ApiBlogResp;
import top.continew.admin.blog.service.BlogService;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.BasePageResp;

@Tag(name = "博客API")
@RestController
@RequestMapping(value = "/api/blog")
@RequiredArgsConstructor
public class BlogApiController {
    private final BlogService blogService;

    @GetMapping("/page")
    @Operation(summary = "分页查询列表", description = "分页查询列表")
    public BasePageResp<ApiBlogResp> page(BlogQuery query, PageQuery pageQuery){

        return blogService.blogPage(query,pageQuery);
    }
}
