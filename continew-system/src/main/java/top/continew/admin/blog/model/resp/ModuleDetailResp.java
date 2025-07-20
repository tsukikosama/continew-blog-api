package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;
import java.io.Serial;
import java.time.*;

/**
 * 模块详情信息
 *
 * @author weilai
 * @since 2025/07/20 10:56
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "模块详情信息")
public class ModuleDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模块名字
     */
    @Schema(description = "模块名字")
    @ExcelProperty(value = "模块名字")
    private String moduleName;

    /**
     * 0表示没有开发的 ，  1 表示开发完成的
     */
    @Schema(description = "0表示没有开发的 ，  1 表示开发完成的")
    @ExcelProperty(value = "0表示没有开发的 ，  1 表示开发完成的")
    private Integer moduleStatus;
}