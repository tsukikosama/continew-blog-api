package top.continew.admin.biz.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;


import java.io.Serial;

/**
 * 标签实体
 *
 * @author weilai
 * @since 2025/07/08 13:44
 */
@Data
@TableName("cc_tag")
public class TagDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    private String name;
}
