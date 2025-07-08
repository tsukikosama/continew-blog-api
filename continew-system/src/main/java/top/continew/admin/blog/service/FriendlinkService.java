package top.continew.admin.blog.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.FriendlinkQuery;
import top.continew.admin.blog.model.req.FriendlinkReq;
import top.continew.admin.blog.model.resp.FriendlinkDetailResp;
import top.continew.admin.blog.model.resp.FriendlinkResp;

/**
 * 友链业务接口
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
public interface FriendlinkService extends BaseService<FriendlinkResp, FriendlinkDetailResp, FriendlinkQuery, FriendlinkReq> {}