package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;
import java.io.Serial;
import java.time.*;

/**
 * 标签详情信息
 *
 * @author weilai
 * @since 2025/07/08 13:45
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "标签详情信息")
public class TagDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    @ExcelProperty(value = "标签名称")
    private String name;
}