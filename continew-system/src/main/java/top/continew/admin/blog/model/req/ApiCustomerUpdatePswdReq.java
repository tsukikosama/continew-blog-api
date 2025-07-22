package top.continew.admin.blog.model.req;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "API-用户-修改密码请求")
public class ApiCustomerUpdatePswdReq {
    private String oldPswd;
    private String newPswd;
}
