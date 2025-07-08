package top.continew.admin.blog.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.BlogTypeMapper;
import top.continew.admin.blog.model.entity.BlogTypeDO;
import top.continew.admin.blog.model.query.BlogTypeQuery;
import top.continew.admin.blog.model.req.BlogTypeReq;
import top.continew.admin.blog.model.resp.BlogTypeDetailResp;
import top.continew.admin.blog.model.resp.BlogTypeResp;
import top.continew.admin.blog.service.BlogTypeService;

/**
 * 分类业务实现
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
@Service
@RequiredArgsConstructor
public class BlogTypeServiceImpl extends BaseServiceImpl<BlogTypeMapper, BlogTypeDO, BlogTypeResp, BlogTypeDetailResp, BlogTypeQuery, BlogTypeReq> implements BlogTypeService {}