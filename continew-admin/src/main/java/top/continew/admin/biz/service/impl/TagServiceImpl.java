package top.continew.admin.biz.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.biz.mapper.TagMapper;
import top.continew.admin.biz.model.entity.TagDO;
import top.continew.admin.biz.model.query.TagQuery;
import top.continew.admin.biz.model.req.TagReq;
import top.continew.admin.biz.model.resp.TagDetailResp;
import top.continew.admin.biz.model.resp.TagResp;
import top.continew.admin.biz.service.TagService;

/**
 * 标签业务实现
 *
 * @author weilai
 * @since 2025/07/08 13:44
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends BaseServiceImpl<TagMapper, TagDO, TagResp, TagDetailResp, TagQuery, TagReq> implements TagService {}