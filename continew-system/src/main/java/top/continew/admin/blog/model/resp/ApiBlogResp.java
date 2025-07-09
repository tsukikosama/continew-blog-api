package top.continew.admin.blog.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.continew.admin.blog.model.entity.TagDO;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Api博客信息")
public class ApiBlogResp {
    private Long id;
    private String title;
    private String picture;
    private String content;
    private Integer visit;
    private String simpleTitle;
    private String userId;
    private String userName;
    private List<TagDO> list;
    private Integer reviewNum;
    private Integer likeNumber;
    private LocalDateTime createTime;
}
