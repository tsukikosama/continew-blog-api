package top.continew.admin.blog.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "页面标签")
public class ApiTagResp {
    private Long id;
    private String name;
}
