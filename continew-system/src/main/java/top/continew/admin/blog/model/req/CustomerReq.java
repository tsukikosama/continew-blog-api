package top.continew.admin.blog.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 用户创建或修改参数
 *
 * @author weilai
 * @since 2025/07/09 15:15
 */
@Data
@Schema(description = "用户创建或修改参数")
public class CustomerReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(max = 50, message = "用户名长度不能超过 {max} 个字符")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Length(max = 128, message = "密码长度不能超过 {max} 个字符")
    private String password;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
//    @NotBlank(message = "邮箱不能为空")
    @Length(max = 30, message = "邮箱长度不能超过 {max} 个字符")
    private String email;

    /**
     * 创建时间
     */
//    @Schema(description = "创建时间")
////    @NotNull(message = "创建时间不能为空")
//    private LocalDateTime createTime;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
//    @NotBlank(message = "昵称不能为空")
    @Length(max = 50, message = "昵称长度不能超过 {max} 个字符")
    private String nickname;
}