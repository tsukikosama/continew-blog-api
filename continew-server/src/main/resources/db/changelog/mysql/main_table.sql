-- liquibase formatted sql

-- changeset charles7c:1
-- comment 初始化表结构
CREATE TABLE IF NOT EXISTS `sys_menu`
(
    `id`          bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title`       varchar(30)         NOT NULL COMMENT '标题',
    `parent_id`   bigint(20)          NOT NULL DEFAULT 0 COMMENT '上级菜单ID',
    `type`        tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '类型（1：目录；2：菜单；3：按钮）',
    `path`        varchar(255)                 DEFAULT NULL COMMENT '路由地址',
    `name`        varchar(50)                  DEFAULT NULL COMMENT '组件名称',
    `component`   varchar(255)                 DEFAULT NULL COMMENT '组件路径',
    `redirect`    varchar(255)                 DEFAULT NULL COMMENT '重定向地址',
    `icon`        varchar(50)                  DEFAULT NULL COMMENT '图标',
    `is_external` bit(1)                       DEFAULT b'0' COMMENT '是否外链',
    `is_cache`    bit(1)                       DEFAULT b'0' COMMENT '是否缓存',
    `is_hidden`   bit(1)                       DEFAULT b'0' COMMENT '是否隐藏',
    `permission`  varchar(100)                 DEFAULT NULL COMMENT '权限标识',
    `sort`        int                 NOT NULL DEFAULT 999 COMMENT '排序',
    `status`      tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：启用；2：禁用）',
    `create_user` bigint(20)          NOT NULL COMMENT '创建人',
    `create_time` datetime            NOT NULL COMMENT '创建时间',
    `update_user` bigint(20)                   DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_title_parent_id` (`title`, `parent_id`),
    INDEX `idx_parent_id` (`parent_id`),
    INDEX `idx_create_user` (`create_user`),
    INDEX `idx_update_user` (`update_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单表';

CREATE TABLE IF NOT EXISTS `sys_dept`
(
    `id`          bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(30)         NOT NULL COMMENT '名称',
    `parent_id`   bigint(20)          NOT NULL DEFAULT 0 COMMENT '上级部门ID',
    `ancestors`   varchar(512)        NOT NULL DEFAULT '' COMMENT '祖级列表',
    `description` varchar(200)                 DEFAULT NULL COMMENT '描述',
    `sort`        int                 NOT NULL DEFAULT 999 COMMENT '排序',
    `status`      tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：启用；2：禁用）',
    `is_system`   bit(1)              NOT NULL DEFAULT b'0' COMMENT '是否为系统内置数据',
    `create_user` bigint(20)          NOT NULL COMMENT '创建人',
    `create_time` datetime            NOT NULL COMMENT '创建时间',
    `update_user` bigint(20)                   DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_name_parent_id` (`name`, `parent_id`),
    INDEX `idx_parent_id` (`parent_id`),
    INDEX `idx_create_user` (`create_user`),
    INDEX `idx_update_user` (`update_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门表';

CREATE TABLE IF NOT EXISTS `sys_role`
(
    `id`                  bigint(20)  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`                varchar(30) NOT NULL COMMENT '名称',
    `code`                varchar(30) NOT NULL COMMENT '编码',
    `data_scope`          tinyint(1)  NOT NULL DEFAULT 4 COMMENT '数据权限（1：全部数据权限；2：本部门及以下数据权限；3：本部门数据权限；4：仅本人数据权限；5：自定义数据权限）',
    `description`         varchar(200)         DEFAULT NULL COMMENT '描述',
    `sort`                int         NOT NULL DEFAULT 999 COMMENT '排序',
    `is_system`           bit(1)      NOT NULL DEFAULT b'0' COMMENT '是否为系统内置数据',
    `menu_check_strictly` bit(1)               DEFAULT b'1' COMMENT '菜单选择是否父子节点关联',
    `dept_check_strictly` bit(1)               DEFAULT b'1' COMMENT '部门选择是否父子节点关联',
    `create_user`         bigint(20)  NOT NULL COMMENT '创建人',
    `create_time`         datetime    NOT NULL COMMENT '创建时间',
    `update_user`         bigint(20)           DEFAULT NULL COMMENT '修改人',
    `update_time`         datetime             DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_name` (`name`),
    UNIQUE INDEX `uk_code` (`code`),
    INDEX `idx_create_user` (`create_user`),
    INDEX `idx_update_user` (`update_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

CREATE TABLE IF NOT EXISTS `sys_user`
(
    `id`             bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`       varchar(64)         NOT NULL COMMENT '用户名',
    `nickname`       varchar(30)         NOT NULL COMMENT '昵称',
    `password`       varchar(255)                 DEFAULT NULL COMMENT '密码',
    `gender`         tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别（0：未知；1：男；2：女）',
    `email`          varchar(255)                 DEFAULT NULL COMMENT '邮箱',
    `phone`          varchar(255)                 DEFAULT NULL COMMENT '手机号码',
    `avatar`         longtext                     DEFAULT NULL COMMENT '头像',
    `description`    varchar(200)                 DEFAULT NULL COMMENT '描述',
    `status`         tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：启用；2：禁用）',
    `is_system`      bit(1)              NOT NULL DEFAULT b'0' COMMENT '是否为系统内置数据',
    `pwd_reset_time` datetime                     DEFAULT NULL COMMENT '最后一次修改密码时间',
    `dept_id`        bigint(20)          NOT NULL COMMENT '部门ID',
    `create_user`    bigint(20)                   DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime            NOT NULL COMMENT '创建时间',
    `update_user`    bigint(20)                   DEFAULT NULL COMMENT '修改人',
    `update_time`    datetime                     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_username` (`username`),
    UNIQUE INDEX `uk_email` (`email`),
    UNIQUE INDEX `uk_phone` (`phone`),
    INDEX `idx_dept_id` (`dept_id`),
    INDEX `idx_create_user` (`create_user`),
    INDEX `idx_update_user` (`update_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

CREATE TABLE IF NOT EXISTS `sys_user_password_history`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`     bigint(20)   NOT NULL COMMENT '用户ID',
    `password`    varchar(255) NOT NULL COMMENT '密码',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户历史密码表';

CREATE TABLE IF NOT EXISTS `sys_user_social`
(
    `id`              bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `source`          varchar(255) NOT NULL COMMENT '来源',
    `open_id`         varchar(255) NOT NULL COMMENT '开放ID',
    `user_id`         bigint(20)   NOT NULL COMMENT '用户ID',
    `meta_json`       text     DEFAULT NULL COMMENT '附加信息',
    `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `create_time`     datetime     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_source_open_id` (`source`, `open_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户社会化关联表';

CREATE TABLE IF NOT EXISTS `sys_user_role`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_user_id_role_id` (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户和角色关联表';

CREATE TABLE IF NOT EXISTS `sys_role_menu`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色和菜单关联表';

CREATE TABLE IF NOT EXISTS `sys_role_dept`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`role_id`, `dept_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色和部门关联表';

CREATE TABLE IF NOT EXISTS `sys_option`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `category`      varchar(50)  NOT NULL COMMENT '类别',
    `name`          varchar(50)  NOT NULL COMMENT '名称',
    `code`          varchar(100) NOT NULL COMMENT '键',
    `value`         longtext     DEFAULT NULL COMMENT '值',
    `default_value` longtext     DEFAULT NULL COMMENT '默认值',
    `description`   varchar(200) DEFAULT NULL COMMENT '描述',
    `update_user`   bigint(20)   DEFAULT NULL COMMENT '修改人',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_category_code` (`category`, `code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='参数表';

CREATE TABLE IF NOT EXISTS `sys_dict`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(30) NOT NULL COMMENT '名称',
    `code`        varchar(30) NOT NULL COMMENT '编码',
    `description` varchar(200)         DEFAULT NULL COMMENT '描述',
    `is_system`   bit(1)      NOT NULL DEFAULT b'0' COMMENT '是否为系统内置数据',
    `create_user` bigint(20)  NOT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_user` bigint(20)           DEFAULT NULL COMMENT '修改人',
    `update_time` datetime             DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_name` (`name`),
    UNIQUE INDEX `uk_code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典表';

CREATE TABLE IF NOT EXISTS `sys_dict_item`
(
    `id`          bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `label`       varchar(30)         NOT NULL COMMENT '标签',
    `value`       varchar(30)         NOT NULL COMMENT '值',
    `color`       varchar(30)                  DEFAULT NULL COMMENT '标签颜色',
    `sort`        int                 NOT NULL DEFAULT 999 COMMENT '排序',
    `description` varchar(200)                 DEFAULT NULL COMMENT '描述',
    `status`      tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：启用；2：禁用）',
    `dict_id`     bigint(20)          NOT NULL COMMENT '字典ID',
    `create_user` bigint(20)          NOT NULL COMMENT '创建人',
    `create_time` datetime            NOT NULL COMMENT '创建时间',
    `update_user` bigint(20)                   DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_value_dict_id` (`value`, `dict_id`),
    INDEX `idx_dict_id` (`dict_id`),
    INDEX `idx_create_user` (`create_user`),
    INDEX `idx_update_user` (`update_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典项表';

CREATE TABLE IF NOT EXISTS `sys_log`
(
    `id`               bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `trace_id`         varchar(255)                 DEFAULT NULL COMMENT '链路ID',
    `description`      varchar(255)        NOT NULL COMMENT '日志描述',
    `module`           varchar(100)        NOT NULL COMMENT '所属模块',
    `request_url`      varchar(512)        NOT NULL COMMENT '请求URL',
    `request_method`   varchar(10)         NOT NULL COMMENT '请求方式',
    `request_headers`  text                         DEFAULT NULL COMMENT '请求头',
    `request_body`     text                         DEFAULT NULL COMMENT '请求体',
    `status_code`      int                 NOT NULL COMMENT '状态码',
    `response_headers` text                         DEFAULT NULL COMMENT '响应头',
    `response_body`    mediumtext                   DEFAULT NULL COMMENT '响应体',
    `time_taken`       bigint(20)          NOT NULL COMMENT '耗时（ms）',
    `ip`               varchar(100)                 DEFAULT NULL COMMENT 'IP',
    `address`          varchar(255)                 DEFAULT NULL COMMENT 'IP归属地',
    `browser`          varchar(100)                 DEFAULT NULL COMMENT '浏览器',
    `os`               varchar(100)                 DEFAULT NULL COMMENT '操作系统',
    `status`           tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：成功；2：失败）',
    `error_msg`        text                         DEFAULT NULL COMMENT '错误信息',
    `create_user`      bigint(20)                   DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime            NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `idx_module` (`module`),
    INDEX `idx_ip` (`ip`),
    INDEX `idx_address` (`address`),
    INDEX `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统日志表';

CREATE TABLE IF NOT EXISTS `sys_message`
(
    `id`          bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title`       varchar(50)         NOT NULL COMMENT '标题',
    `content`     text                         DEFAULT NULL COMMENT '内容',
    `type`        tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '类型（1：系统消息；2：安全消息）',
    `path`        varchar(255)                 DEFAULT NULL COMMENT '跳转路径',
    `scope`       tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '通知范围（1：所有人；2：指定用户）',
    `users`       json                         DEFAULT NULL COMMENT '通知用户',
    `create_time` datetime            NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='消息表';

CREATE TABLE IF NOT EXISTS `sys_message_log`
(
    `message_id` bigint(20) NOT NULL COMMENT '消息ID',
    `user_id`    bigint(20) NOT NULL COMMENT '用户ID',
    `read_time`  datetime DEFAULT NULL COMMENT '读取时间',
    PRIMARY KEY (`message_id`, `user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='消息日志表';

CREATE TABLE IF NOT EXISTS `sys_notice`
(
    `id`             bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title`          varchar(150)        NOT NULL COMMENT '标题',
    `content`        mediumtext          NOT NULL COMMENT '内容',
    `type`           varchar(30)         NOT NULL COMMENT '分类',
    `notice_scope`   tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '通知范围（1：所有人；2：指定用户）',
    `notice_users`   json                         DEFAULT NULL COMMENT '通知用户',
    `notice_methods` json                         DEFAULT NULL COMMENT '通知方式（1：系统消息；2：登录弹窗）',
    `is_timing`      bit(1)              NOT NULL DEFAULT b'0' COMMENT '是否定时',
    `publish_time`   datetime                     DEFAULT NULL COMMENT '发布时间',
    `is_top`         bit(1)              NOT NULL DEFAULT b'0' COMMENT '是否置顶',
    `status`         tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：草稿；2：待发布；3：已发布）',
    `create_user`    bigint(20)          NOT NULL COMMENT '创建人',
    `create_time`    datetime            NOT NULL COMMENT '创建时间',
    `update_user`    bigint(20)                   DEFAULT NULL COMMENT '修改人',
    `update_time`    datetime                     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX `idx_create_user` (`create_user`),
    INDEX `idx_update_user` (`update_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='公告表';

CREATE TABLE IF NOT EXISTS `sys_notice_log`
(
    `notice_id` bigint(20) NOT NULL COMMENT '公告ID',
    `user_id`   bigint(20) NOT NULL COMMENT '用户ID',
    `read_time` datetime DEFAULT NULL COMMENT '读取时间',
    PRIMARY KEY (`notice_id`, `user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='公告日志表';

CREATE TABLE IF NOT EXISTS `sys_storage`
(
    `id`          bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(100)        NOT NULL COMMENT '名称',
    `code`        varchar(30)         NOT NULL COMMENT '编码',
    `type`        tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '类型（1：本地存储；2：对象存储）',
    `access_key`  varchar(255)                 DEFAULT NULL COMMENT 'Access Key',
    `secret_key`  varchar(255)                 DEFAULT NULL COMMENT 'Secret Key',
    `endpoint`    varchar(255)                 DEFAULT NULL COMMENT 'Endpoint',
    `bucket_name` varchar(255)        NOT NULL COMMENT 'Bucket',
    `domain`      varchar(255)                 DEFAULT NULL COMMENT '域名',
    `description` varchar(200)                 DEFAULT NULL COMMENT '描述',
    `is_default`  bit(1)              NOT NULL DEFAULT b'0' COMMENT '是否为默认存储',
    `sort`        int                 NOT NULL DEFAULT 999 COMMENT '排序',
    `status`      tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：启用；2：禁用）',
    `create_user` bigint(20)          NOT NULL COMMENT '创建人',
    `create_time` datetime            NOT NULL COMMENT '创建时间',
    `update_user` bigint(20)                   DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_code` (`code`),
    INDEX `idx_create_user` (`create_user`),
    INDEX `idx_update_user` (`update_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='存储表';

CREATE TABLE IF NOT EXISTS `sys_file`
(
    `id`                 bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`               varchar(255)        NOT NULL COMMENT '名称',
    `original_name`      varchar(255)        NOT NULL COMMENT '原始名称',
    `size`               bigint(20)                   DEFAULT NULL COMMENT '大小（字节）',
    `parent_path`        varchar(512)        NOT NULL DEFAULT '/' COMMENT '上级目录',
    `path`               varchar(512)        NOT NULL COMMENT '路径',
    `extension`          varchar(32)                  DEFAULT NULL COMMENT '扩展名',
    `content_type`       varchar(255)                 DEFAULT NULL COMMENT '内容类型',
    `type`               tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '类型（0: 目录；1：其他；2：图片；3：文档；4：视频；5：音频）',
    `sha256`             varchar(256)                 DEFAULT NULL COMMENT 'SHA256值',
    `metadata`           text                         DEFAULT NULL COMMENT '元数据',
    `thumbnail_name`     varchar(255)                 DEFAULT NULL COMMENT '缩略图名称',
    `thumbnail_size`     bigint(20)                   DEFAULT NULL COMMENT '缩略图大小（字节)',
    `thumbnail_metadata` text                         DEFAULT NULL COMMENT '缩略图元数据',
    `storage_id`         bigint(20)          NOT NULL COMMENT '存储ID',
    `create_user`        bigint(20)          NOT NULL COMMENT '创建人',
    `create_time`        datetime            NOT NULL COMMENT '创建时间',
    `update_user`        bigint(20)                   DEFAULT NULL COMMENT '修改人',
    `update_time`        datetime                     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX `idx_type` (`type`),
    INDEX `idx_sha256` (`sha256`),
    INDEX `idx_storage_id` (`storage_id`),
    INDEX `idx_create_user` (`create_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='文件表';

CREATE TABLE IF NOT EXISTS `sys_client`
(
    `id`             bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `client_id`      varchar(50)         NOT NULL COMMENT '客户端ID',
    `client_type`    varchar(50)         NOT NULL COMMENT '客户端类型',
    `auth_type`      json                NOT NULL COMMENT '认证类型',
    `active_timeout` bigint(20)                   DEFAULT -1 COMMENT 'Token最低活跃频率（单位：秒，-1：不限制，永不冻结）',
    `timeout`        bigint(20)                   DEFAULT 2592000 COMMENT 'Token有效期（单位：秒，-1：永不过期）',
    `status`         tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：启用；2：禁用）',
    `create_user`    bigint(20)          NOT NULL COMMENT '创建人',
    `create_time`    datetime            NOT NULL COMMENT '创建时间',
    `update_user`    bigint(20)                   DEFAULT NULL COMMENT '修改人',
    `update_time`    datetime                     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_client_id` (`client_id`),
    INDEX `idx_create_user` (`create_user`),
    INDEX `idx_update_user` (`update_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='客户端表';

CREATE TABLE IF NOT EXISTS `sys_sms_config`
(
    `id`              bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`            varchar(100)        NOT NULL COMMENT '名称',
    `supplier`        varchar(50)         NOT NULL COMMENT '厂商',
    `access_key`      varchar(255)        NOT NULL COMMENT 'Access Key',
    `secret_key`      varchar(255)        NOT NULL COMMENT 'Secret Key',
    `signature`       varchar(100)                 DEFAULT NULL COMMENT '短信签名',
    `template_id`     varchar(50)                  DEFAULT NULL COMMENT '模板ID',
    `weight`          int                          DEFAULT NULL COMMENT '负载均衡权重',
    `retry_interval`  int                          DEFAULT NULL COMMENT '重试间隔（单位：秒）',
    `max_retries`     int                          DEFAULT NULL COMMENT '重试次数',
    `maximum`         int                          DEFAULT NULL COMMENT '发送上限',
    `supplier_config` text                         DEFAULT NULL COMMENT '各个厂商独立配置',
    `is_default`      bit(1)              NOT NULL DEFAULT b'0' COMMENT '是否为默认配置',
    `status`          tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态（1：启用；2：禁用）',
    `create_user`     bigint(20)          NOT NULL COMMENT '创建人',
    `create_time`     datetime            NOT NULL COMMENT '创建时间',
    `update_user`     bigint(20)                   DEFAULT NULL COMMENT '修改人',
    `update_time`     datetime                     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX `idx_create_user` (`create_user`),
    INDEX `idx_update_user` (`update_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='短信配置表';

CREATE TABLE IF NOT EXISTS `sys_sms_log`
(
    `id`          bigint(20)          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `config_id`   bigint(20)          NOT NULL COMMENT '配置ID',
    `phone`       varchar(25)         NOT NULL COMMENT '手机号',
    `params`      text                         DEFAULT NULL COMMENT '参数配置',
    `status`      tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '发送状态（1：成功；2：失败）',
    `res_msg`     text                         DEFAULT NULL COMMENT '返回数据',
    `create_user` bigint(20)          NOT NULL COMMENT '创建人',
    `create_time` datetime            NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `idx_config_id` (`config_id`),
    INDEX `idx_create_user` (`create_user`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='短信日志表';

-- changeset weilai:1
-- comment 初始项目表结构

DROP TABLE IF EXISTS `cc_angry_pig`;
CREATE TABLE `cc_angry_pig`
(
    `id`          bigint                                                NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     bigint                                                NOT NULL COMMENT '用户id',
    `cause`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '原因',
    `create_time` datetime                                              NOT NULL COMMENT '创建时间',
    `update_user` datetime                                              NULL DEFAULT NULL COMMENT '更新人',
    `create_user` bigint                                                NOT NULL COMMENT '创建人',
    `type`        tinyint                                               NOT NULL COMMENT '事件类型',
    `update_time` datetime                                              NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cc_blog`;
CREATE TABLE `cc_blog`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`        varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '标题',
    `picture`      varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT NULL COMMENT '图片',
    `content`      longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci     NOT NULL COMMENT '内容',
    `is_valid`     int                                                           NOT NULL COMMENT '是否有效',
    `version`      int                                                           NOT NULL DEFAULT 0 COMMENT '版本',
    `visit`        int                                                           NOT NULL DEFAULT 0 COMMENT '流量数量',
    `simple_title` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '简化标题',
    `user_id`      bigint                                                        NOT NULL COMMENT '用户id',
    `state`        tinyint                                                       NOT NULL COMMENT '0保存 1发布',
    `create_time`  datetime                                                      NOT NULL COMMENT '创建时间',
    `create_user`  bigint                                                        NOT NULL COMMENT '创建人',
    `update_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`  bigint                                                        NULL     DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `cc_blog_like`;
CREATE TABLE `cc_blog_like`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '组件',
    `user_id`     bigint   NOT NULL COMMENT '用户id',
    `blog_id`     bigint   NOT NULL COMMENT '点赞博客id',
    `create_time` datetime NOT NULL COMMENT '点赞时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cc_blog_type`;
CREATE TABLE `cc_blog_type`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `blog_id`     bigint   NOT NULL COMMENT '博客id',
    `tag_id`      bigint   NOT NULL COMMENT '标签id',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `create_user` bigint   NOT NULL COMMENT '创建人',
    `update_user` bigint   NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cc_feedback`;
CREATE TABLE `cc_feedback`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT COMMENT 'id',
    `nickname`    varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `avator`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
    `message`     text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         NULL COMMENT '消息',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `cc_friendlink`;
CREATE TABLE `cc_friendlink`
(
    `id`           int                                                           NOT NULL AUTO_INCREMENT COMMENT '主键',
    `web_url`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '网站链接',
    `web_name`     varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '网站名字',
    `web_descript` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         NULL COMMENT '网站描述',
    `web_time`     date                                                          NULL DEFAULT NULL COMMENT '添加时间',
    `web_img`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '网站图片',
    `web_access`   int                                                           NOT NULL COMMENT '0为为审核通过 1为审核通过',
    `web_email`    varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户邮箱',
    `create_time`  datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time`  datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `create_user`  bigint                                                        NOT NULL COMMENT '创建人',
    `update_user`  bigint                                                        NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `cc_life_note`;
CREATE TABLE `cc_life_note`
(
    `id`          bigint                                                        NOT NULL COMMENT '主键',
    `origin_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '原图地址',
    `web_url`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网页压缩图地址',
    `user_id`     bigint                                                        NOT NULL COMMENT '发布者id',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
    `title`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `create_user` bigint                                                        NOT NULL COMMENT '创建人',
    `update_user` bigint                                                        NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cc_module`;
CREATE TABLE `cc_module`
(
    `id`            int                                                           NOT NULL AUTO_INCREMENT COMMENT 'id',
    `module_name`   varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '模块名字',
    `module_status` tinyint                                                       NULL DEFAULT NULL COMMENT '0表示没有开发的 ，  1 表示开发完成的',
    `create_time`   datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `create_user`   bigint                                                        NOT NULL COMMENT '创建人',
    `update_user`   bigint                                                        NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `cc_review`;
CREATE TABLE `cc_review`
(
    `id`            bigint                                                NOT NULL AUTO_INCREMENT COMMENT '主键',
    `content`       text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '评论内容',
    `likes`         int                                                   NULL DEFAULT NULL COMMENT '点赞数',
    `blog_id`       bigint                                                NULL DEFAULT NULL COMMENT '博客id',
    `reply_id`      bigint                                                NULL DEFAULT NULL COMMENT '回复的是哪个评论的id',
    `user_id`       bigint                                                NULL DEFAULT NULL COMMENT '评论用户id',
    `review_type`   tinyint                                               NOT NULL COMMENT '评论的类型 0是主评论1是子评论',
    `reply_user_id` bigint                                                NULL DEFAULT NULL COMMENT '回复的用户id',
    `create_time`   datetime                                              NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `cc_tag`;
CREATE TABLE `cc_tag`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标签名称',
    `create_time` datetime                                                     NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci
  ROW_FORMAT = DYNAMIC;


-- changeset weilai:2
-- comment 标签表新增创建人和更新人
ALTER TABLE `cc_tag`
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `name`,
    ADD COLUMN `create_user` bigint NOT NULL COMMENT '创建人' AFTER `update_time`,
    ADD COLUMN `update_user` bigint NULL COMMENT '更新人' AFTER `create_user`;

-- changeset weilai:3
-- comment 修改博客表字段名
ALTER TABLE `cc_blog`
    CHANGE COLUMN `state` `status` tinyint NOT NULL COMMENT '0保存 1发布' AFTER `user_id`;
ALTER TABLE `cc_blog`
    MODIFY COLUMN `is_valid` int NULL COMMENT '是否有效' AFTER `content`;

-- changeset weilai:4
-- comment 移除无用字段
ALTER TABLE `cc_blog`
    DROP COLUMN `is_valid`,
    DROP COLUMN `version`;

-- changeset weilai:5
-- comment 新增用户表字段
CREATE TABLE `cc_customer`
(
    `id`          bigint       NOT NULL COMMENT 'id',
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `password`    varchar(128) NOT NULL COMMENT '密码',
    `email`       varchar(30)  NOT NULL COMMENT '邮箱',
    `gender`      tinyint(1)   NULL COMMENT '性别',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `description` varchar(255) NULL COMMENT '描述',
    `avatar`      varchar(255) NULL COMMENT '头像',
    PRIMARY KEY (`id`)
);

-- changeset weilai:6
-- comment 新增用户昵称
ALTER TABLE `cc_customer`
    ADD COLUMN `nickname` varchar(50) NOT NULL COMMENT '昵称' AFTER `avatar`;
-- changeset weilai:7
-- comment 新增邮箱非必输
ALTER TABLE `cc_customer`
    MODIFY COLUMN `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '邮箱' AFTER `password`;

