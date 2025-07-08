package top.continew.admin.blog.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.BlogLikeQuery;
import top.continew.admin.blog.model.req.BlogLikeReq;
import top.continew.admin.blog.model.resp.BlogLikeDetailResp;
import top.continew.admin.blog.model.resp.BlogLikeResp;

/**
 * 点赞业务接口
 *
 * @author weilai
 * @since 2025/07/08 15:03
 */
public interface BlogLikeService extends BaseService<BlogLikeResp, BlogLikeDetailResp, BlogLikeQuery, BlogLikeReq> {}