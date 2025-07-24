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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import top.continew.admin.blog.model.entity.BlogTypeDO;
import top.continew.admin.blog.model.entity.TagDO;
import top.continew.admin.blog.model.resp.*;
import top.continew.admin.blog.service.BlogTypeService;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.BasePageResp;
import top.continew.starter.extension.crud.model.resp.PageResp;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.BlogMapper;
import top.continew.admin.blog.model.entity.BlogDO;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.req.BlogReq;
import top.continew.admin.blog.service.BlogService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 博客 业务实现
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Service
@RequiredArgsConstructor
public class BlogServiceImpl extends BaseServiceImpl<BlogMapper, BlogDO, BlogResp, BlogDetailResp, BlogQuery, BlogReq> implements BlogService {

    private final BlogTypeService blogTypeService;

    @Override
    public PageResp<BlogResp> page(BlogQuery query, PageQuery pageQuery) {
        PageResp<BlogResp> page = super.page(query, pageQuery);
        page.getList().forEach(item -> {
            List<BlogTypeDO> list = blogTypeService.getBlogTagByBlogId(item.getId());
            List<Long> collect = list.stream().map(BlogTypeDO::getTagId).collect(Collectors.toList());
            item.setTagId(collect);
        });
        return page;
    }

    @Transactional
    @Override
    public Long create(BlogReq req) {
        req.setUserId(StpUtil.getLoginIdAsLong());
        //保存博客标签
        Long blogId = super.create(req);
        //批量保存博客标签关系
        blogTypeService.batchSaveBlogType(req.getTagId(), blogId);
        return blogId;
    }

    @Override
    public BasePageResp<ApiBlogResp> customPage(BlogQuery query, PageQuery pageQuery) {
        QueryWrapper<BlogDO> queryWrapper = this.buildQueryWrapper(query);
        IPage<ApiBlogResp> page = this.baseMapper.selectBlogPage(new Page((long)pageQuery.getPage(), (long)pageQuery
            .getSize()), queryWrapper);
        PageResp<ApiBlogResp> pageResp = PageResp.build(page, ApiBlogResp.class);
        pageResp.getList().forEach(item -> {
            List<BlogTypeDO> list = blogTypeService.getBlogTagByBlogId(item.getId());
            item.setTagList(BeanUtil.copyToList(list, TagDO.class));
        });
        return pageResp;
    }

    @Override
    public ApiCustomerResp getUserBlogDateById(long id) {

        return this.baseMapper.getUserBlogDateById(id);
    }

    @Override
    public List<ApiBlogResp> getRecentBlog() {
        List<ApiBlogResp> list = this.baseMapper.getRecentBlog();
        return list;
    }

    @Override
    public ApiBlogResp getBlogByBlogId(Long blogId) {
        return this.baseMapper.getBlogByBlogId(blogId);
    }

    @Override
    public List<ArchiveResp> getArchive(long loginIdAsLong) {
        List<BlogDO> blogDOS = this.baseMapper.selectList(Wrappers.<BlogDO>lambdaQuery()
            .eq(BlogDO::getUserId, loginIdAsLong));
        List<ArchiveResp.ArchiveItem> itemList = BeanUtil.copyToList(blogDOS, ArchiveResp.ArchiveItem.class);
        List<ArchiveResp> archiveResps = groupByYear(itemList);
        return archiveResps;
    }

//    /**
//     * 查询自己的博客
//     * @param query
//     * @param pageQuery
//     * @return
//     */
//    @Override
//    public BasePageResp<ApiBlogResp> customPage(BlogQuery query, PageQuery pageQuery) {
//        LambdaQueryWrapper<BlogDO> eq = Wrappers.<BlogDO>lambdaQuery().eq(BlogDO::getUserId, StpUtil.getLoginIdAsLong());
//        Page<BlogDO> blogDOPage = this.baseMapper.selectPage(new Page<>(pageQuery.getPage(), pageQuery.getSize()), eq);
//        return PageResp.build(blogDOPage, ApiBlogResp.class);
//    }

//    @Override
//    public BasePageResp<ApiBlogResp> customPageByTagId(Long tagId, PageQuery pageQuery) {
//        LambdaQueryWrapper<BlogDO> eq = Wrappers.<BlogDO>lambdaQuery().eq(BlogDO::getUserId, StpUtil.getLoginIdAsLong());
//        Page<BlogDO> blogDOPage = this.baseMapper.selectPage(new Page<>(pageQuery.getPage(), pageQuery.getSize()), eq);
//        return null;
//    }

    public List<ArchiveResp> groupByYear(List<ArchiveResp.ArchiveItem> itemList) {
        // 1. 按年份分组，Map<String, List<ArchiveItem>>
        Map<String, List<ArchiveResp.ArchiveItem>> grouped = itemList.stream()
            .collect(Collectors.groupingBy(item -> item.getCreateTime().toString().substring(0, 4)));

        // 2. 转换成 List<ArchiveResp>
        List<ArchiveResp> result = grouped.entrySet()
            .stream()
            // 按年份降序排序，最新年份排前面
            .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
            .map(e -> {
                ArchiveResp resp = new ArchiveResp();
                resp.setYear(e.getKey());
                resp.setArchiveList(e.getValue());
                return resp;
            })
            .collect(Collectors.toList());

        return result;
    }
}