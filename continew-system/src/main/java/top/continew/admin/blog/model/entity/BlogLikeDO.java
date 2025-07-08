package top.continew.admin.blog.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;


import java.io.Serial;

/**
 * 点赞实体
 *
 * @author weilai
 * @since 2025/07/08 15:03
 */
@Data
@TableName("cc_blog_like")
public class BlogLikeDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 点赞博客id
     */
    private Long blogId;
}
