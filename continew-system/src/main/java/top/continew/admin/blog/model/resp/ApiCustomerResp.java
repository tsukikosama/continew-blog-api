package top.continew.admin.blog.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录用户信息")
public class ApiCustomerResp {
    private String username;
    private String nickname;
    private String avatar;
    private String description;
    private String email;
    private String gender;
}
