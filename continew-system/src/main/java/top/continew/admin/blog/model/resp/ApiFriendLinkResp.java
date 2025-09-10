package top.continew.admin.blog.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "友链")
public class ApiFriendLinkResp {
    private String name;
    private String url;
    private String description;
    private String avatar;
    private String img;

}
