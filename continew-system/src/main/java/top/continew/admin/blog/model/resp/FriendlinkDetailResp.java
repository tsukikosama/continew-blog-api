package top.continew.admin.blog.model.resp;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.admin.common.model.resp.BaseDetailResp;
import java.io.Serial;
import java.time.*;

/**
 * 友链详情信息
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "友链详情信息")
public class FriendlinkDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网站链接
     */
    @Schema(description = "网站链接")
    @ExcelProperty(value = "网站链接")
    private String webUrl;

    /**
     * 网站名字
     */
    @Schema(description = "网站名字")
    @ExcelProperty(value = "网站名字")
    private String webName;

    /**
     * 网站描述
     */
    @Schema(description = "网站描述")
    @ExcelProperty(value = "网站描述")
    private String webDescript;

    /**
     * 添加时间
     */
    @Schema(description = "添加时间")
    @ExcelProperty(value = "添加时间")
    private LocalDate webTime;

    /**
     * 网站图片
     */
    @Schema(description = "网站图片")
    @ExcelProperty(value = "网站图片")
    private String webImg;

    /**
     * 0为为审核通过 1为审核通过
     */
    @Schema(description = "0为为审核通过 1为审核通过")
    @ExcelProperty(value = "0为为审核通过 1为审核通过")
    private Integer webAccess;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    @ExcelProperty(value = "用户邮箱")
    private String webEmail;
}