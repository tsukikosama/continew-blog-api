package top.continew.admin.blog.model.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;


import java.io.Serial;
import java.time.*;

/**
 * 友链实体
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
@Data
@TableName("cc_friendlink")
public class FriendlinkDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 网站链接
     */
    private String webUrl;

    /**
     * 网站名字
     */
    private String webName;

    /**
     * 网站描述
     */
    private String webDescript;

    /**
     * 添加时间
     */
    private LocalDate webTime;

    /**
     * 网站图片
     */
    private String webImg;

    /**
     * 0为为审核通过 1为审核通过
     */
    private Integer webAccess;

    /**
     * 用户邮箱
     */
    private String webEmail;
}
