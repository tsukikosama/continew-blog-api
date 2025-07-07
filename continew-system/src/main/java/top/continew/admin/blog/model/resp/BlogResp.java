package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;
import java.io.Serial;
import java.time.*;

/**
 * 博客 信息
 *
 * @author weilai
 * @since 2025/07/07 17:36
 */
@Data
@Schema(description = "博客 信息")
public class BlogResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String picture;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 是否有效
     */
    @Schema(description = "是否有效")
    private Integer isValid;

    /**
     * 流量数量
     */
    @Schema(description = "流量数量")
    private Integer visit;

    /**
     * 简化标题
     */
    @Schema(description = "简化标题")
    private String simpleTitle;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 0保存 1发布
     */
    @Schema(description = "0保存 1发布")
    private Integer state;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private Long updateUser;
}