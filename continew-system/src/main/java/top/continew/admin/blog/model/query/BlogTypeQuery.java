package top.continew.admin.blog.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 分类查询条件
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
@Data
@Schema(description = "分类查询条件")
public class BlogTypeQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @Schema(description = "博客id")
    @Query(type = QueryType.EQ)
    private Long blogId;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    @Query(type = QueryType.EQ)
    private Long tagId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @Query(type = QueryType.EQ)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @Query(type = QueryType.EQ)
    private Long createUser;
}