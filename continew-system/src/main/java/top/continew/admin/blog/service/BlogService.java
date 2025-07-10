package top.continew.admin.blog.service;

import top.continew.admin.blog.model.resp.*;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.BasePageResp;
import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.req.BlogReq;

import java.util.List;

/**
 * 博客 业务接口
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
public interface BlogService extends BaseService<BlogResp, BlogDetailResp, BlogQuery, BlogReq> {


    BasePageResp<ApiBlogResp> blogPage(BlogQuery query, PageQuery pageQuery);

    ApiCustomerResp getUserBlogDateById(long loginIdAsLong);

    List<ApiBlogResp> getRecentBlog();

    ApiBlogResp getBlogByBlogId(Long blogId);

    List<ArchiveResp> getArchive(long loginIdAsLong);

}