package top.continew.admin.biz.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.biz.model.query.TagQuery;
import top.continew.admin.biz.model.req.TagReq;
import top.continew.admin.biz.model.resp.TagDetailResp;
import top.continew.admin.biz.model.resp.TagResp;

/**
 * 标签业务接口
 *
 * @author weilai
 * @since 2025/07/08 13:44
 */
public interface TagService extends BaseService<TagResp, TagDetailResp, TagQuery, TagReq> {}