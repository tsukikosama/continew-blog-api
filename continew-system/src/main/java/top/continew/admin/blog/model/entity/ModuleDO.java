package top.continew.admin.blog.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;


import java.io.Serial;

/**
 * 模块实体
 *
 * @author weilai
 * @since 2025/07/20 10:56
 */
@Data
@TableName("cc_module")
public class ModuleDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模块名字
     */
    private String moduleName;

    /**
     * 0表示没有开发的 ，  1 表示开发完成的
     */
    private Integer moduleStatus;
}
