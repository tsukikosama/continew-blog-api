package top.continew.admin.blog.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.blog.model.query.FriendlinkQuery;
import top.continew.admin.blog.model.req.FriendlinkReq;
import top.continew.admin.blog.model.resp.FriendlinkDetailResp;
import top.continew.admin.blog.model.resp.FriendlinkResp;
import top.continew.admin.blog.service.FriendlinkService;

/**
 * 友链管理 API
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
@Tag(name = "友链管理 API")
@RestController
@CrudRequestMapping(value = "/admin/friendlink", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class FriendlinkController extends BaseController<FriendlinkService, FriendlinkResp, FriendlinkDetailResp, FriendlinkQuery, FriendlinkReq> {}