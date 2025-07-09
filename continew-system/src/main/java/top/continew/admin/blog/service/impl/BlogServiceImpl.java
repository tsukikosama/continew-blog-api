package top.continew.admin.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import top.continew.admin.blog.model.resp.ApiBlogResp;
import top.continew.admin.blog.service.BlogTypeService;
import top.continew.starter.data.mp.base.BaseMapper;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.BasePageResp;
import top.continew.starter.extension.crud.model.resp.PageResp;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.BlogMapper;
import top.continew.admin.blog.model.entity.BlogDO;
import top.continew.admin.blog.model.query.BlogQuery;
import top.continew.admin.blog.model.req.BlogReq;
import top.continew.admin.blog.model.resp.BlogDetailResp;
import top.continew.admin.blog.model.resp.BlogResp;
import top.continew.admin.blog.service.BlogService;

import java.util.List;

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
            List<Long> list = blogTypeService.getBlogTagByBlogId(item.getId());
            item.setTagId(list);
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
        blogTypeService.batchSaveBlogType(req.getTagId(),blogId);
        return blogId;
    }


    @Override
    public BasePageResp<ApiBlogResp> blogPage(BlogQuery query, PageQuery pageQuery) {
        QueryWrapper<BlogDO> queryWrapper = this.buildQueryWrapper(query);
        IPage<ApiBlogResp> page = this.baseMapper.selectBlogPage(new Page((long)pageQuery.getPage(), (long)pageQuery.getSize()), queryWrapper);
        PageResp<ApiBlogResp> pageResp = PageResp.build(page, ApiBlogResp.class);

        return pageResp;
    }
}