package top.continew.admin.biz.model.req;


import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 标签创建或修改参数
 *
 * @author weilai
 * @since 2025/07/08 13:44
 */
@Data
@Schema(description = "标签创建或修改参数")
public class TagReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    @Length(max = 20, message = "标签名称长度不能超过 {max} 个字符")
    private String name;
}