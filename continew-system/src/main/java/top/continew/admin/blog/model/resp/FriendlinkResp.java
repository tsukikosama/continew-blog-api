package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;
import java.io.Serial;
import java.time.*;

/**
 * 友链信息
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
@Data
@Schema(description = "友链信息")
public class FriendlinkResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网站链接
     */
    @Schema(description = "网站链接")
    private String webUrl;

    /**
     * 网站名字
     */
    @Schema(description = "网站名字")
    private String webName;

    /**
     * 网站描述
     */
    @Schema(description = "网站描述")
    private String webDescript;

    /**
     * 网站图片
     */
    @Schema(description = "网站图片")
    private String webImg;

    /**
     * 0为为审核通过 1为审核通过
     */
    @Schema(description = "0为为审核通过 1为审核通过")
    private Integer webAccess;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String webEmail;

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