package top.continew.admin.blog.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.TagQuery;
import top.continew.admin.blog.model.req.TagReq;
import top.continew.admin.blog.model.resp.TagDetailResp;
import top.continew.admin.blog.model.resp.TagResp;

/**
 * 标签业务接口
 *
 * @author weilai
 * @since 2025/07/08 13:45
 */
public interface TagService extends BaseService<TagResp, TagDetailResp, TagQuery, TagReq> {}