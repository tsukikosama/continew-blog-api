package top.continew.admin.blog.service;

import top.continew.admin.blog.model.req.ApiReviewReq;
import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.ReviewQuery;
import top.continew.admin.blog.model.req.ReviewReq;
import top.continew.admin.blog.model.resp.ReviewDetailResp;
import top.continew.admin.blog.model.resp.ReviewResp;

import java.util.List;

/**
 * 评论业务接口
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
public interface ReviewService extends BaseService<ReviewResp, ReviewDetailResp, ReviewQuery, ReviewReq> {
    void review(ApiReviewReq req);

    List<ReviewResp> getChildReview(Long id);
}