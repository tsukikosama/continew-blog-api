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