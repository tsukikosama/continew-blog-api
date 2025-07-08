package top.continew.admin.blog.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.BlogLikeMapper;
import top.continew.admin.blog.model.entity.BlogLikeDO;
import top.continew.admin.blog.model.query.BlogLikeQuery;
import top.continew.admin.blog.model.req.BlogLikeReq;
import top.continew.admin.blog.model.resp.BlogLikeDetailResp;
import top.continew.admin.blog.model.resp.BlogLikeResp;
import top.continew.admin.blog.service.BlogLikeService;

/**
 * 点赞业务实现
 *
 * @author weilai
 * @since 2025/07/08 15:03
 */
@Service
@RequiredArgsConstructor
public class BlogLikeServiceImpl extends BaseServiceImpl<BlogLikeMapper, BlogLikeDO, BlogLikeResp, BlogLikeDetailResp, BlogLikeQuery, BlogLikeReq> implements BlogLikeService {}