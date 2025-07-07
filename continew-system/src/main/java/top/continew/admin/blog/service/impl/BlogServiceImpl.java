package top.continew.admin.blog.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.BlogMapper;
import top.continew.admin.blog.model.entity.BlogDO;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.req.BlogReq;
import top.continew.admin.blog.model.resp.BlogDetailResp;
import top.continew.admin.blog.model.resp.BlogResp;
import top.continew.admin.blog.service.BlogService;

/**
 * 博客 业务实现
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Service
@RequiredArgsConstructor
public class BlogServiceImpl extends BaseServiceImpl<BlogMapper, BlogDO, BlogResp, BlogDetailResp, BlogQuery, BlogReq> implements BlogService {}