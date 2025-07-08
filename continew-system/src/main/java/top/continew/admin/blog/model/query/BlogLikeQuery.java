package top.continew.admin.blog.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 点赞查询条件
 *
 * @author weilai
 * @since 2025/07/08 15:03
 */
@Data
@Schema(description = "点赞查询条件")
public class BlogLikeQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}