package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.admin.common.model.resp.BaseResp;
import java.io.Serial;
import java.time.*;

/**
 * 点赞信息
 *
 * @author weilai
 * @since 2025/07/08 15:03
 */
@Data
@Schema(description = "点赞信息")
public class BlogLikeResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;
}