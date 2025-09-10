-- liquibase formatted sql

-- changeset weilai:1
-- comment 博客标签字段调整
ALTER TABLE `ccblog`.`cc_friendlink`
    DROP COLUMN `web_time`,
    CHANGE COLUMN `web_url` `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '网站链接' AFTER `id`,
    CHANGE COLUMN `web_name` `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '网站名字' AFTER `url`,
    CHANGE COLUMN `web_descript` `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '网站描述' AFTER `name`,
    CHANGE COLUMN `web_img` `img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '网站图片' AFTER `description`,
    CHANGE COLUMN `web_access` `access` int NOT NULL COMMENT '0为为审核通过 1为审核通过 2为审核失败' AFTER `img`,
    CHANGE COLUMN `web_email` `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户邮箱' AFTER `access`,
    ADD COLUMN `avatar` varchar(255) NULL COMMENT '网站作者头像' AFTER `update_user`;
