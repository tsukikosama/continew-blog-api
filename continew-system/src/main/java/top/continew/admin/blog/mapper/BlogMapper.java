package top.continew.admin.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.continew.admin.blog.model.resp.ApiBlogResp;
import top.continew.starter.data.mp.base.BaseMapper;
import top.continew.admin.blog.model.entity.BlogDO;

/**
* 博客  Mapper
*
* @author weilai
* @since 2025/07/07 17:36
*/
public interface BlogMapper extends BaseMapper<BlogDO> {
    IPage<ApiBlogResp> selectBlogPage(@Param("page") Page page, @Param(Constants.WRAPPER) QueryWrapper<BlogDO> queryWrapper);
}