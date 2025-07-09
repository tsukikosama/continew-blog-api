package top.continew.admin.blog.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.admin.common.model.entity.BaseDO;


import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author weilai
 * @since 2025/07/09 15:15
 */
@Data
@TableName("cc_customer")
public class CustomerDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Boolean gender;

    /**
     * 描述
     */
    private String description;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 不需要 createUser 字段
     */
    @TableField(exist = false)
    private Long createUser;

    @TableField(exist = false)
    private Long updateUser;

    @TableField(exist = false)
    private LocalDateTime updateTime;
}
