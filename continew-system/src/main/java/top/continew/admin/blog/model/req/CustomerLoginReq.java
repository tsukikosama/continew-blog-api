package top.continew.admin.blog.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import top.continew.admin.auth.enums.AuthTypeEnum;
import top.continew.admin.auth.model.req.LoginReq;

@Data
@Schema(description = "用户登录req")
public class CustomerLoginReq  {
    private String username;
    private String password;
    /**
     * 客户端 ID
     */
    @Schema(description = "客户端 ID", example = "ef51c9a3e9046c4f2ea45142c8a8344a")
    @NotBlank(message = "客户端ID不能为空")
    private String clientId;

    /**
     * 认证类型
     */
    @Schema(description = "认证类型", example = "ACCOUNT")
    @NotNull(message = "认证类型无效")
    private AuthTypeEnum authType;
}
