package top.continew.admin.blog.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;


import java.io.Serial;

/**
 * 博客 实体
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Data
@TableName("cc_blog")
public class BlogDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String picture;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否有效
     */
    private Integer isValid;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 流量数量
     */
    private Integer visit;

    /**
     * 简化标题
     */
    private String simpleTitle;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0保存 1发布
     */
    private Integer state;
}
