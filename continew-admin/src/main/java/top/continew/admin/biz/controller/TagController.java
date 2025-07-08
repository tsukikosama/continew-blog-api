package top.continew.admin.biz.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.biz.model.query.TagQuery;
import top.continew.admin.biz.model.req.TagReq;
import top.continew.admin.biz.model.resp.TagDetailResp;
import top.continew.admin.biz.model.resp.TagResp;
import top.continew.admin.biz.service.TagService;

/**
 * 标签管理 API
 *
 * @author weilai
 * @since 2025/07/08 13:44
 */
@Tag(name = "标签管理 API")
@RestController
@CrudRequestMapping(value = "/biz/tag", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class TagController extends BaseController<TagService, TagResp, TagDetailResp, TagQuery, TagReq> {}