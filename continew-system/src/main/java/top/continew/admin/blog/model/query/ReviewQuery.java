package top.continew.admin.blog.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 评论查询条件
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
@Data
@Schema(description = "评论查询条件")
public class ReviewQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论的类型 0是主评论1是子评论
     */
    @Schema(description = "评论的类型 0是主评论1是子评论")
    @Query(type = QueryType.EQ)
    private Integer reviewType;

    @Schema(description = "博客id")
    @Query(type = QueryType.EQ)
    private Long blogId;
}