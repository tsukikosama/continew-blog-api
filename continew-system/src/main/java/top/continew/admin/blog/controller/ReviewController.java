package top.continew.admin.blog.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.admin.common.controller.BaseController;
import top.continew.admin.blog.model.query.ReviewQuery;
import top.continew.admin.blog.model.req.ReviewReq;
import top.continew.admin.blog.model.resp.ReviewDetailResp;
import top.continew.admin.blog.model.resp.ReviewResp;
import top.continew.admin.blog.service.ReviewService;

/**
 * 评论管理 API
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
@Tag(name = "评论管理 API")
@RestController
@CrudRequestMapping(value = "/blog/review", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class ReviewController extends BaseController<ReviewService, ReviewResp, ReviewDetailResp, ReviewQuery, ReviewReq> {

}