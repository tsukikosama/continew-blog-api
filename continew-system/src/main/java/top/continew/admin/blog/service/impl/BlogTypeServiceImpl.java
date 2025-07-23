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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.BlogTypeMapper;
import top.continew.admin.blog.model.entity.BlogTypeDO;
import top.continew.admin.blog.model.query.BlogTypeQuery;
import top.continew.admin.blog.model.req.BlogTypeReq;
import top.continew.admin.blog.model.resp.BlogTypeDetailResp;
import top.continew.admin.blog.model.resp.BlogTypeResp;
import top.continew.admin.blog.service.BlogTypeService;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类业务实现
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
@Service
@RequiredArgsConstructor
public class BlogTypeServiceImpl extends BaseServiceImpl<BlogTypeMapper, BlogTypeDO, BlogTypeResp, BlogTypeDetailResp, BlogTypeQuery, BlogTypeReq> implements BlogTypeService {
    /**
     * 批量保存博客标签关系
     * 
     * @param tagId  标签集合
     * @param blogId 博客id
     */
    @Override
    public void batchSaveBlogType(List<Long> tagId, Long blogId) {
        List<BlogTypeDO> list = new ArrayList<>();
        tagId.forEach(item -> {
            BlogTypeDO blog = new BlogTypeDO();
            blog.setBlogId(blogId);
            blog.setTagId(item);
            list.add(blog);
        });
        this.baseMapper.insertBatch(list);
    }

    @Override
    public List<BlogTypeDO> getBlogTagByBlogId(Long id) {
        List<BlogTypeDO> blogTypeDOS = this.baseMapper.selectList(Wrappers.<BlogTypeDO>lambdaQuery()
            .eq(BlogTypeDO::getBlogId, id));

        return blogTypeDOS;
    }
}