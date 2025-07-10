package top.continew.admin.blog.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "评论请求体")
public class ApiReviewReq {
    private Long blogId;
    private String content;
    private Long replyId;
    private Integer reviewType;
    private Long replyUserId;

}
