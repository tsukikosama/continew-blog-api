/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.admin.blog.model.req.ApiReviewReq;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.ReviewMapper;
import top.continew.admin.blog.model.entity.ReviewDO;
import top.continew.admin.blog.model.query.ReviewQuery;
import top.continew.admin.blog.model.req.ReviewReq;
import top.continew.admin.blog.model.resp.ReviewDetailResp;
import top.continew.admin.blog.model.resp.ReviewResp;
import top.continew.admin.blog.service.ReviewService;

import java.util.List;

/**
 * 评论业务实现
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl extends BaseServiceImpl<ReviewMapper, ReviewDO, ReviewResp, ReviewDetailResp, ReviewQuery, ReviewReq> implements ReviewService {
    @Override
    public void review(ApiReviewReq req) {
        ReviewDO reviewDO = BeanUtil.copyProperties(req, ReviewDO.class);
        reviewDO.setUserId(StpUtil.getLoginIdAsLong());
        this.baseMapper.insert(reviewDO);
    }

    @Override
    public List<ReviewResp> getChildReview(Long id) {
        List<ReviewResp> list = this.baseMapper.selectChildReviewList(Wrappers.<ReviewDO>lambdaQuery()
            .eq(ReviewDO::getReplyId, id));

        return list;
    }
}