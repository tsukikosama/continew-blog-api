package top.continew.admin.blog.model.req;


import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 点赞创建或修改参数
 *
 * @author weilai
 * @since 2025/07/08 15:03
 */
@Data
@Schema(description = "点赞创建或修改参数")
public class BlogLikeReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}