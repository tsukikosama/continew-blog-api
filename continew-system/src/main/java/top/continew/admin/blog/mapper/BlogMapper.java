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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.continew.admin.blog.model.resp.ApiBlogResp;
import top.continew.admin.blog.model.resp.ApiCustomerResp;
import top.continew.starter.data.mp.base.BaseMapper;
import top.continew.admin.blog.model.entity.BlogDO;

import java.util.List;

/**
 * 博客 Mapper
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
public interface BlogMapper extends BaseMapper<BlogDO> {
    IPage<ApiBlogResp> selectBlogPage(@Param("page") Page page,
                                      @Param(Constants.WRAPPER) QueryWrapper<BlogDO> queryWrapper);

    ApiCustomerResp getUserBlogDateById(@Param("id") long id);

    List<ApiBlogResp> getRecentBlog();

    ApiBlogResp getBlogByBlogId(@Param("blogId") Long blogId);
}