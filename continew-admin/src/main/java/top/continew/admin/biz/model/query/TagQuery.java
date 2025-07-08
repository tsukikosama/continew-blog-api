package top.continew.admin.biz.model.query;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;
import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 标签查询条件
 *
 * @author weilai
 * @since 2025/07/08 13:44
 */
@Data
@Schema(description = "标签查询条件")
public class TagQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    @Query(type = QueryType.LIKE)
    private String name;
}