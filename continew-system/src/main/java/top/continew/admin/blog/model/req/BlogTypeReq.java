package top.continew.admin.blog.model.req;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 分类创建或修改参数
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
@Data
@Schema(description = "分类创建或修改参数")
public class BlogTypeReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @Schema(description = "博客id")
    @NotNull(message = "博客id不能为空")
    private Long blogId;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    @NotNull(message = "标签id不能为空")
    private Long tagId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @NotNull(message = "创建人不能为空")
    private Long createUser;
}