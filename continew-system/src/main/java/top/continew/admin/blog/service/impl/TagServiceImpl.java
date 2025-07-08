package top.continew.admin.blog.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.TagMapper;
import top.continew.admin.blog.model.entity.TagDO;
import top.continew.admin.blog.model.query.TagQuery;
import top.continew.admin.blog.model.req.TagReq;
import top.continew.admin.blog.model.resp.TagDetailResp;
import top.continew.admin.blog.model.resp.TagResp;
import top.continew.admin.blog.service.TagService;

/**
 * 标签业务实现
 *
 * @author weilai
 * @since 2025/07/08 13:45
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends BaseServiceImpl<TagMapper, TagDO, TagResp, TagDetailResp, TagQuery, TagReq> implements TagService {}