package top.continew.admin.api;

import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.resp.ApiBlogResp;
import top.continew.admin.blog.model.resp.BlogResp;
import top.continew.admin.blog.service.BlogService;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;
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
    public BasePageResp<ApiBlogResp> page(BlogQuery query, PageQuery pageQuery){

        return blogService.blogPage(query,pageQuery);
    }
    @SaIgnore
    @GetMapping("/recent")
    @Operation(summary = "获取最近的5条博客",description = "获取最近的5条博客")
    public List<ApiBlogResp> recentBlog(){
        return blogService.getRecentBlog();
    }

    @SaIgnore
    @GetMapping("/{blogId}")
    @Operation(summary = "获取博客详情",description = "获取博客详情")
    public ApiBlogResp detail(@PathVariable("blogId")Long blogId){
        return blogService.getBlogByBlogId(blogId);
    }

}
