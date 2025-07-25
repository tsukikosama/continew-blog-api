/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.system.service.impl;

import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.admin.common.constant.CacheConstants;
import top.continew.admin.common.constant.ContainerConstants;
import top.continew.admin.common.constant.SysConstants;
import top.continew.admin.common.context.RoleContext;
import top.continew.admin.common.context.UserContext;
import top.continew.admin.common.context.UserContextHolder;
import top.continew.admin.common.enums.DataScopeEnum;
import top.continew.admin.system.mapper.RoleMapper;
import top.continew.admin.system.model.entity.RoleDO;
import top.continew.admin.system.model.query.RoleQuery;
import top.continew.admin.system.model.req.RoleReq;
import top.continew.admin.system.model.req.RoleUpdatePermissionReq;
import top.continew.admin.system.model.resp.MenuResp;
import top.continew.admin.system.model.resp.role.RoleDetailResp;
import top.continew.admin.system.model.resp.role.RoleResp;
import top.continew.admin.system.service.*;
import top.continew.starter.core.validation.CheckUtils;
import top.continew.starter.extension.crud.service.BaseServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色业务实现
 *
 * @author Charles7c
 * @since 2023/2/8 23:17
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, RoleDO, RoleResp, RoleDetailResp, RoleQuery, RoleReq> implements RoleService {

    private final MenuService menuService;
    private final RoleMenuService roleMenuService;
    private final RoleDeptService roleDeptService;
    private final UserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(RoleReq req) {
        String name = req.getName();
        CheckUtils.throwIf(this.isNameExists(name, null), "新增失败，[{}] 已存在", name);
        String code = req.getCode();
        CheckUtils.throwIf(this.isCodeExists(code, null), "新增失败，[{}] 已存在", code);
        // 新增信息
        Long roleId = super.create(req);
        // 保存角色和部门关联
        roleDeptService.add(req.getDeptIds(), roleId);
        return roleId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleReq req, Long id) {
        String name = req.getName();
        CheckUtils.throwIf(this.isNameExists(name, id), "修改失败，[{}] 已存在", name);
        RoleDO oldRole = super.getById(id);
        CheckUtils.throwIfNotEqual(req.getCode(), oldRole.getCode(), "角色编码不允许修改", oldRole.getName());
        DataScopeEnum oldDataScope = oldRole.getDataScope();
        if (Boolean.TRUE.equals(oldRole.getIsSystem())) {
            CheckUtils.throwIfNotEqual(req.getDataScope(), oldDataScope, "[{}] 是系统内置角色，不允许修改角色数据权限", oldRole.getName());
        }
        // 更新信息
        super.update(req, id);
        if (SysConstants.SUPER_ROLE_CODE.equals(req.getCode())) {
            return;
        }
        // 保存角色和部门关联
        boolean isSaveDeptSuccess = roleDeptService.add(req.getDeptIds(), id);
        // 如果数据权限有变更，则更新在线用户权限信息
        if (isSaveDeptSuccess || ObjectUtil.notEqual(req.getDataScope(), oldDataScope)) {
            this.updateUserContext(id);
        }
    }

    @Override
    public void beforeDelete(List<Long> ids) {
        List<RoleDO> list = baseMapper.lambdaQuery()
            .select(RoleDO::getName, RoleDO::getIsSystem)
            .in(RoleDO::getId, ids)
            .list();
        Optional<RoleDO> isSystemData = list.stream().filter(RoleDO::getIsSystem).findFirst();
        CheckUtils.throwIf(isSystemData::isPresent, "所选角色 [{}] 是系统内置角色，不允许删除", isSystemData.orElseGet(RoleDO::new)
            .getName());
        CheckUtils.throwIf(userRoleService.isRoleIdExists(ids), "所选角色存在用户关联，请解除关联后重试");
        // 删除角色和菜单关联
        roleMenuService.deleteByRoleIds(ids);
        // 删除角色和部门关联
        roleDeptService.deleteByRoleIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(key = "#id", name = CacheConstants.ROLE_MENU_KEY_PREFIX)
    public void updatePermission(Long id, RoleUpdatePermissionReq req) {
        super.getById(id);
        // 保存角色和菜单关联
        boolean isSaveMenuSuccess = roleMenuService.add(req.getMenuIds(), id);
        // 如果功能权限有变更，则更新在线用户权限信息
        if (isSaveMenuSuccess) {
            this.updateUserContext(id);
        }
        baseMapper.lambdaUpdate()
            .set(RoleDO::getMenuCheckStrictly, req.getMenuCheckStrictly())
            .eq(RoleDO::getId, id)
            .update();
    }

    @Override
    public void assignToUsers(Long id, List<Long> userIds) {
        super.getById(id);
        // 保存用户和角色关联
        userRoleService.assignRoleToUsers(id, userIds);
        // 更新用户上下文
        this.updateUserContext(id);
    }

    @Override
    public void fill(Object obj) {
        super.fill(obj);
        if (obj instanceof RoleDetailResp detail) {
            Long roleId = detail.getId();
            List<MenuResp> list = menuService.listByRoleId(roleId);
            List<Long> menuIds = list.stream().map(MenuResp::getId).toList();
            detail.setMenuIds(menuIds);
        }
    }

    @Override
    public Set<String> listPermissionByUserId(Long userId) {
        Set<String> roleCodeSet = this.listCodeByUserId(userId);
        // 超级管理员赋予全部权限
        if (roleCodeSet.contains(SysConstants.SUPER_ROLE_CODE)) {
            return CollUtil.newHashSet(SysConstants.ALL_PERMISSION);
        }
        return menuService.listPermissionByUserId(userId);
    }

    @Override
    @ContainerMethod(namespace = ContainerConstants.USER_ROLE_NAME_LIST, type = MappingType.ORDER_OF_KEYS)
    public List<String> listNameByIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<RoleDO> roleList = baseMapper.lambdaQuery().select(RoleDO::getName).in(RoleDO::getId, ids).list();
        return roleList.stream().map(RoleDO::getName).toList();
    }

    @Override
    public Set<String> listCodeByUserId(Long userId) {
        List<Long> roleIdList = userRoleService.listRoleIdByUserId(userId);
        if (CollUtil.isEmpty(roleIdList)) {
            return Collections.emptySet();
        }
        List<RoleDO> roleList = baseMapper.lambdaQuery().select(RoleDO::getCode).in(RoleDO::getId, roleIdList).list();
        return roleList.stream().map(RoleDO::getCode).collect(Collectors.toSet());
    }

    @Override
    public Set<RoleContext> listByUserId(Long userId) {
        List<Long> roleIdList = userRoleService.listRoleIdByUserId(userId);
        if (CollUtil.isEmpty(roleIdList)) {
            return Collections.emptySet();
        }
        List<RoleDO> roleList = baseMapper.lambdaQuery()
            .select(RoleDO::getId, RoleDO::getCode, RoleDO::getDataScope)
            .in(RoleDO::getId, roleIdList)
            .list();
        return roleList.stream()
            .map(r -> new RoleContext(r.getId(), r.getCode(), r.getDataScope()))
            .collect(Collectors.toSet());
    }

    @Override
    public RoleDO getByCode(String code) {
        return baseMapper.lambdaQuery().eq(RoleDO::getCode, code).one();
    }

    @Override
    public List<RoleDO> listByNames(List<String> list) {
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return this.list(Wrappers.<RoleDO>lambdaQuery().in(RoleDO::getName, list));
    }

    @Override
    public int countByNames(List<String> roleNames) {
        if (CollUtil.isEmpty(roleNames)) {
            return 0;
        }
        return (int)this.count(Wrappers.<RoleDO>lambdaQuery().in(RoleDO::getName, roleNames));
    }

    /**
     * 名称是否存在
     *
     * @param name 名称
     * @param id   ID
     * @return 是否存在
     */
    private boolean isNameExists(String name, Long id) {
        return baseMapper.lambdaQuery().eq(RoleDO::getName, name).ne(id != null, RoleDO::getId, id).exists();
    }

    /**
     * 编码是否存在
     *
     * @param code 编码
     * @param id   ID
     * @return 是否存在
     */
    private boolean isCodeExists(String code, Long id) {
        return baseMapper.lambdaQuery().eq(RoleDO::getCode, code).ne(id != null, RoleDO::getId, id).exists();
    }

    /**
     * 更新用户上下文
     *
     * @param roleId 角色 ID
     */
    private void updateUserContext(Long roleId) {
        List<Long> userIdList = userRoleService.listUserIdByRoleId(roleId);
        userIdList.parallelStream().forEach(userId -> {
            UserContext userContext = UserContextHolder.getContext(userId);
            if (userContext != null) {
                userContext.setRoles(this.listByUserId(userId));
                userContext.setPermissions(this.listPermissionByUserId(userId));
                UserContextHolder.setContext(userContext);
            }
        });
    }
}
