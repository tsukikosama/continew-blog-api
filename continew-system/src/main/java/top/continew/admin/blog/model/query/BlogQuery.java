package top.continew.admin.blog.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 博客 查询条件
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Data
@Schema(description = "博客 查询条件")
public class BlogQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @Query(type = QueryType.LIKE)
    private String title;

    /**
     * 是否有效
     */
    @Schema(description = "是否有效")
    @Query(type = QueryType.EQ)
    private Integer isValid;

    /**
     * 简化标题
     */
    @Schema(description = "简化标题")
    @Query(type = QueryType.LIKE)
    private String simpleTitle;

    /**
     * 0保存 1发布
     */
    @Schema(description = "0保存 1发布")
    @Query(type = QueryType.EQ)
    private Integer state;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @Query(type = QueryType.BETWEEN)
    private LocalDateTime[] createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @Query(type = QueryType.EQ)
    private Long createUser;
}