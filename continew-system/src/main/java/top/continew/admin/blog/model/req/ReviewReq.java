package top.continew.admin.blog.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 评论创建或修改参数
 *
 * @author weilai
 * @since 2025/07/10 14:23
 */
@Data
@Schema(description = "评论创建或修改参数")
public class ReviewReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论的类型 0是主评论1是子评论
     */
    @Schema(description = "评论的类型 0是主评论1是子评论")
    @NotNull(message = "评论的类型 0是主评论1是子评论不能为空")
    private Integer reviewType;
}