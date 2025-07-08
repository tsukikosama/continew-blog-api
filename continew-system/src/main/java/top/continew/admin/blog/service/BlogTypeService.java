package top.continew.admin.blog.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.BlogTypeQuery;
import top.continew.admin.blog.model.req.BlogTypeReq;
import top.continew.admin.blog.model.resp.BlogTypeDetailResp;
import top.continew.admin.blog.model.resp.BlogTypeResp;

/**
 * 分类业务接口
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
public interface BlogTypeService extends BaseService<BlogTypeResp, BlogTypeDetailResp, BlogTypeQuery, BlogTypeReq> {}