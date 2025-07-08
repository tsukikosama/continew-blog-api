package top.continew.admin.blog.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.req.BlogReq;
import top.continew.admin.blog.model.resp.BlogDetailResp;
import top.continew.admin.blog.model.resp.BlogResp;

/**
 * 博客 业务接口
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
public interface BlogService extends BaseService<BlogResp, BlogDetailResp, BlogQuery, BlogReq> {


}