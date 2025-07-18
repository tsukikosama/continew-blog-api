package top.continew.admin.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import top.continew.admin.blog.model.resp.*;
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
    public  List<ArchiveResp> getArchive(long loginIdAsLong) {
        List<BlogDO> blogDOS = this.baseMapper.selectList(Wrappers.<BlogDO>lambdaQuery().eq(BlogDO::getUserId, loginIdAsLong));
        List<ArchiveResp.ArchiveItem> itemList = BeanUtil.copyToList(blogDOS, ArchiveResp.ArchiveItem.class);
        List<ArchiveResp> archiveResps = groupByYear(itemList);
        return archiveResps;
    }

    public List<ArchiveResp> groupByYear(List<ArchiveResp.ArchiveItem> itemList) {
        // 1. 按年份分组，Map<String, List<ArchiveItem>>
        Map<String, List<ArchiveResp.ArchiveItem>> grouped = itemList.stream()
                .collect(Collectors.groupingBy(item -> item.getCreateTime().toString().substring(0, 4)));

        // 2. 转换成 List<ArchiveResp>
        List<ArchiveResp> result = grouped.entrySet().stream()
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