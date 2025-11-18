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
import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
import top.continew.starter.core.validation.CheckUtils;
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
            List<Long> collect = list.stream().map(BlogTypeDO::getTagId).toList();
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
    public BasePageResp<ApiBlogResp> customPageApi(BlogQuery query, PageQuery pageQuery) {
        QueryWrapper<BlogDO> queryWrapper = this.buildQueryWrapper(query);
        IPage<ApiBlogResp> page = this.baseMapper.selectBlogPage(new Page((long)pageQuery.getPage(), (long)pageQuery
            .getSize()), queryWrapper);
        PageResp<ApiBlogResp> pageResp = PageResp.build(page, ApiBlogResp.class);
        pageResp.getList().forEach(item -> {
            List<BlogTypeDO> list = blogTypeService.getBlogTagByBlogId(item.getId());
            item.setTags(BeanUtil.copyToList(list, TagDO.class));
        });
        return pageResp;
    }

    @Override
    public ApiCustomerResp getUserBlogDateById(long id) {
        ApiCustomerResp userBlogDateById = this.baseMapper.getUserBlogDateById(id);
        CheckUtils.throwIfNull(userBlogDateById, "传递id异常");
        return userBlogDateById;
    }

    @Override
    public List<ApiBlogResp> getRecentBlog() {
        return this.baseMapper.getRecentBlog();
    }

    @Override
    public ApiBlogResp getBlogByBlogId(Long blogId) {
        return this.baseMapper.getBlogByBlogId(blogId);
    }

    @Override
    public List<ArchiveResp> getArchive() {
        List<BlogDO> blogDoList = this.baseMapper.selectList(Wrappers.<BlogDO>lambdaQuery());
        List<ArchiveResp.ArchiveItem> itemList = BeanUtil.copyToList(blogDoList, ArchiveResp.ArchiveItem.class);
        return groupByYear(itemList);
    }

    /**
     * 后台的博客分页
     * @param query
     * @param pageQuery
     * @return
     */
    @Override
    public PageResp<BlogResp> customPage(BlogQuery query, PageQuery pageQuery) {
        QueryWrapper<BlogDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(query.getUserId() != null,"cb.user_id",query.getUserId());
        queryWrapper.eq(query.getTitle() != null,"cb.title",query.getTitle());
        queryWrapper.eq(query.getStatus() != null,"cb.status",query.getStatus());
        queryWrapper.eq(query.getTagId() != null,"cbt.tag_id",query.getTagId());
        // between 会先获取数值 不能像其他的一样的那样写
        if (ObjectUtil.isNotNull(query.getCreateTime())){
            queryWrapper.between( "cb.create_time", query.getCreateTime().get(0),query.getCreateTime().get(1));
        }
           IPage<BlogResp> page = this.baseMapper.customPage(new Page<>(pageQuery.getPage(),pageQuery.getSize()),queryWrapper);
        page.getRecords().forEach(item -> {
            List<BlogTypeDO> list = blogTypeService.getBlogTagByBlogId(item.getId());
            List<Long> collect = list.stream().map(BlogTypeDO::getTagId).collect(Collectors.toList());
            item.setTagId(collect);
        });
        return PageResp.build(page, BlogResp.class);
    }

    @Override
    public BlogDO getBlogByGoogleId(String googleDocId) {
        List<BlogDO> blogList = this.baseMapper.selectList(Wrappers.<BlogDO>lambdaQuery()
                .eq(BlogDO::getGoogleDocId, googleDocId));
        if(blogList.isEmpty()){
            return null;
        }
        return blogList.get(0);
    }

    /**
     * 批量保存博客
     * @param blogList
     */
    @Override
    public void batchSave(List<BlogDO> blogList) {
        this.baseMapper.insertBatch(blogList);
    }


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
                .toList();

        return result;
    }
}