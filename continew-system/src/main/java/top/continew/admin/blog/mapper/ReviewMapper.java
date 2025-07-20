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

package top.continew.admin.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import top.continew.admin.blog.model.resp.ReviewResp;
import top.continew.starter.data.mp.base.BaseMapper;
import top.continew.admin.blog.model.entity.ReviewDO;

import java.util.List;

/**
 * 评论 Mapper
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
public interface ReviewMapper extends BaseMapper<ReviewDO> {
    List<ReviewResp> selectChildReviewList(@Param(Constants.WRAPPER) LambdaQueryWrapper<ReviewDO> eq);
}