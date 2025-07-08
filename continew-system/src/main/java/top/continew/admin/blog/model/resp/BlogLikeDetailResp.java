package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;
import java.io.Serial;
import java.time.*;

/**
 * 点赞详情信息
 *
 * @author weilai
 * @since 2025/07/08 15:03
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "点赞详情信息")
public class BlogLikeDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 点赞博客id
     */
    @Schema(description = "点赞博客id")
    @ExcelProperty(value = "点赞博客id")
    private Long blogId;
}