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
import java.util.stream.Collectors;

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
     * @param tagId 标签集合
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
    public List<Long> getBlogTagByBlogId(Long id) {
        List<BlogTypeDO> blogTypeDOS = this.baseMapper.selectList(Wrappers.<BlogTypeDO>lambdaQuery().eq(BlogTypeDO::getBlogId, id));
        List<Long> collect = blogTypeDOS.stream().map(BlogTypeDO::getTagId).collect(Collectors.toList());
        return collect;
    }
}