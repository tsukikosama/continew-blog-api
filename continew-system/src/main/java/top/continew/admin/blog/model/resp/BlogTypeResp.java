package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;
import java.io.Serial;
import java.time.*;

/**
 * 分类信息
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
@Data
@Schema(description = "分类信息")
public class BlogTypeResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @Schema(description = "博客id")
    private Long blogId;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Long tagId;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private Long updateUser;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}