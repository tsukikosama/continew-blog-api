package top.continew.admin.blog.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.config.excel.DictExcelProperty;
import top.continew.admin.common.model.entity.BaseDO;
import top.continew.starter.extension.crud.annotation.DictModel;


import java.io.Serial;

/**
 * 标签实体
 *
 * @author weilai
 * @since 2025/07/08 13:45
 */
@Data
@TableName("cc_tag")
@DictModel(labelKey = "name")
public class TagDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    private String name;
}
