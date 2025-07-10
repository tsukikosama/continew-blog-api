package top.continew.admin.blog.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;


import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 评论实体
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
@Data
@TableName("cc_review")
public class ReviewDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likes;

    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 回复的是哪个评论的id
     */
    private Long replyId;

    /**
     * 评论用户id
     */
    private Long userId;

    /**
     * 评论的类型 0是主评论1是子评论
     */
    private Integer reviewType;

    /**
     * 回复的用户id
     */
    private Long replyUserId;


    @TableField(exist = false)
    private Long createUser;

    @TableField(exist = false)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableField(exist = false)
    private Long updateUser;

    /**
     * 修改时间
     */
    @TableField(exist = false)
    private LocalDateTime updateTime;

}
