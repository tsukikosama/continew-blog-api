package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;
import java.io.Serial;
import java.time.*;

/**
 * 用户信息
 *
 * @author weilai
 * @since 2025/07/09 15:15
 */
@Data
@Schema(description = "用户信息")
public class CustomerResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private Boolean gender;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;
}