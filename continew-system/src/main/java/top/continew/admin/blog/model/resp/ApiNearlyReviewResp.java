package top.continew.admin.blog.model.resp;

import lombok.Data;

/**
 * 最近评论api
 */
@Data
public class ApiNearlyReviewResp {
    private String content;
    private String nickname;
    private String avatar;
    private String createTime;
}
