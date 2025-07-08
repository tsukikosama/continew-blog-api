SET @parentId = 1942459838383915008;
-- 标签管理菜单
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (@parentId, '标签管理', 1000, 2, '/biz/tag', 'Tag', 'biz/tag/index', NULL, NULL, b'0', b'0', b'0', NULL, 1, 1, 1, NOW());

-- 标签管理按钮
INSERT INTO `sys_menu`
    (`id`, `title`, `parent_id`, `type`, `permission`, `sort`, `status`, `create_user`, `create_time`)
VALUES
    (1942459838383915009, '列表', @parentId, 3, 'biz:tag:list', 1, 1, 1, NOW()),
    (1942459838383915010, '详情', @parentId, 3, 'biz:tag:get', 2, 1, 1, NOW()),
    (1942459838383915011, '新增', @parentId, 3, 'biz:tag:create', 3, 1, 1, NOW()),
    (1942459838383915012, '修改', @parentId, 3, 'biz:tag:update', 4, 1, 1, NOW()),
    (1942459838383915013, '删除', @parentId, 3, 'biz:tag:delete', 5, 1, 1, NOW()),
    (1942459838383915014, '导出', @parentId, 3, 'biz:tag:export', 6, 1, 1, NOW());

