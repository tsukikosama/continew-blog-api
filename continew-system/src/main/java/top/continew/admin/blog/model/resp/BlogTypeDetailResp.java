package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;
import java.io.Serial;
import java.time.*;

/**
 * 分类详情信息
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "分类详情信息")
public class BlogTypeDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @Schema(description = "博客id")
    @ExcelProperty(value = "博客id")
    private Long blogId;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    @ExcelProperty(value = "标签id")
    private Long tagId;
}