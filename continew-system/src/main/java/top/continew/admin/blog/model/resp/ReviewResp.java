package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;
import java.io.Serial;
import java.time.*;
import java.util.List;

/**
 * 评论信息
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
@Data
@Schema(description = "评论信息")
public class ReviewResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String content;

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    private Integer likes;

    /**
     * 博客id
     */
    @Schema(description = "博客id")
    private Long blogId;

    /**
     * 回复的是哪个评论的id
     */
    @Schema(description = "回复的是哪个评论的id")
    private Long replyId;

    /**
     * 评论用户id
     */
    @Schema(description = "评论用户id")
    private Long userId;

    /**
     * 评论的类型 0是主评论1是子评论
     */
    @Schema(description = "评论的类型 0是主评论1是子评论")
    private Integer reviewType;

    /**
     * 回复的用户id
     */
    @Schema(description = "回复的用户id")
    private Long replyUserId;

    @Schema(description = "用户头像")
    private String userAvatar;

    @Schema(description = "子评论")
    private List<ReviewResp> children;
}