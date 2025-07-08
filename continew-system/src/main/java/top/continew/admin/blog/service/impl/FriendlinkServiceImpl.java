package top.continew.admin.blog.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.FriendlinkMapper;
import top.continew.admin.blog.model.entity.FriendlinkDO;
import top.continew.admin.blog.model.query.FriendlinkQuery;
import top.continew.admin.blog.model.req.FriendlinkReq;
import top.continew.admin.blog.model.resp.FriendlinkDetailResp;
import top.continew.admin.blog.model.resp.FriendlinkResp;
import top.continew.admin.blog.service.FriendlinkService;

/**
 * 友链业务实现
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
@Service
@RequiredArgsConstructor
public class FriendlinkServiceImpl extends BaseServiceImpl<FriendlinkMapper, FriendlinkDO, FriendlinkResp, FriendlinkDetailResp, FriendlinkQuery, FriendlinkReq> implements FriendlinkService {}