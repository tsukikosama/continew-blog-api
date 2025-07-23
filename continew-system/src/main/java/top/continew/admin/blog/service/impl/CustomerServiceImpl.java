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

package top.continew.admin.blog.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.admin.blog.model.req.ApiCustomerUpdatePswdReq;
import top.continew.admin.blog.model.req.ApiCustomerUpdateReq;
import top.continew.admin.blog.model.req.CustomerLoginReq;
import top.continew.admin.common.context.RoleContext;
import top.continew.admin.common.context.UserContext;
import top.continew.admin.system.model.resp.ClientResp;
import top.continew.admin.system.service.ClientService;
import top.continew.starter.core.validation.CheckUtils;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.CustomerMapper;
import top.continew.admin.blog.model.entity.CustomerDO;
import top.continew.admin.blog.model.query.CustomerQuery;
import top.continew.admin.blog.model.req.CustomerReq;
import top.continew.admin.blog.model.resp.CustomerDetailResp;
import top.continew.admin.blog.model.resp.CustomerResp;
import top.continew.admin.blog.service.CustomerService;

import java.util.*;

import static top.continew.admin.common.enums.DataScopeEnum.ALL;

/**
 * 用户业务实现
 *
 * @author weilai
 * @since 2025/07/09 15:15
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends BaseServiceImpl<CustomerMapper, CustomerDO, CustomerResp, CustomerDetailResp, CustomerQuery, CustomerReq> implements CustomerService {

    private final ClientService clientService;

    @Override
    public Void register(CustomerReq req) {
        //注册用户 检查用户账号是否存在
        CustomerDO customerDO = this.baseMapper.selectOne(Wrappers.<CustomerDO>lambdaQuery()
            .eq(CustomerDO::getUsername, req.getUsername()));
        CheckUtils.throwIfNotNull(customerDO, "用户名{}已存在", req.getUsername());
        //存在 对密码进行加密 存入数据库中

        req.setPassword(SecureUtil.md5(req.getPassword()));
        BeanUtil.copyProperties(req, CustomerDO.class);
        //对用户随机生成昵称
        req.setNickname(RandomUtil.randomString(6));
        this.baseMapper.insert(BeanUtil.copyProperties(req, CustomerDO.class));
        return null;
    }

    @Override
    public String loginCustomer(CustomerLoginReq req) {

        // 校验客户端
        CustomerDO customerDO = this.baseMapper.selectOne(Wrappers.<CustomerDO>lambdaQuery()
            .eq(CustomerDO::getUsername, req.getUsername()));
        CheckUtils.throwIfNull(customerDO, "账号{}不存在", req.getUsername());
        CheckUtils.throwIfNotEqual(customerDO.getPassword(), SecureUtil.md5(req.getPassword()), "账号{}密码错误", req
            .getUsername());
        ClientResp client = clientService.getByClientId("ef51c9a3e9046c4f2ea45142c8a8344a");

        // 获取权限、角色、密码过期天数
        Long userId = customerDO.getId();

        // 构建用户上下文
        UserContext userContext = new UserContext();
        Set<RoleContext> roles = new HashSet<>();
        Set<String> codes = new HashSet<>();
        codes.add("admin");

        RoleContext role = new RoleContext();
        role.setId(1L);
        role.setCode("admin");
        role.setDataScope(ALL);
        roles.add(role);

        userContext.setRoles(roles);
        userContext.setRoleCodes(codes);
        userContext.setId(customerDO.getId());
        BeanUtil.copyProperties(customerDO, userContext);
        userContext.setClientType(client.getClientType());
        userContext.setClientId(client.getClientId());

        // 构建 SaLoginModel
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getClientType());
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra("USER", userContext); // ✅ 放进会话

        // 登录
        StpUtil.login(customerDO.getId(), model);

        return "Bearer " + StpUtil.getTokenValue();
    }

    @Override
    public void updateCustomer(ApiCustomerUpdateReq req) {
        //获取当前的用户信息
        CustomerDO customerDO = this.baseMapper.selectById(StpUtil.getLoginIdAsLong());
        CheckUtils.throwIfNull(customerDO, "用户不存在");

        LambdaUpdateWrapper<CustomerDO> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(StringUtils.isNotBlank(req.getNickname()), CustomerDO::getNickname, req.getNickname())
            .set(StringUtils.isNotBlank(req.getEmail()), CustomerDO::getEmail, req.getEmail())
            .set(StringUtils.isNotBlank(req.getAvatar()), CustomerDO::getAvatar, req.getAvatar())
            .set(StringUtils.isNotBlank(req.getGender()), CustomerDO::getGender, req.getGender())
            .set(StringUtils.isNotBlank(req.getDescription()), CustomerDO::getDescription, req.getDescription())
            .eq(CustomerDO::getId, customerDO.getId());
        this.baseMapper.update(null, wrapper);
    }

    @Override
    public void updateCustomerPswd(ApiCustomerUpdatePswdReq req) {
        //获取当前的用户信息
        CustomerDO customerDO = this.baseMapper.selectById(StpUtil.getLoginIdAsLong());
        CheckUtils.throwIfNull(customerDO, "用户不存在");

        CheckUtils.throwIfNotEqual(customerDO.getPassword(), SecureUtil.md5(req.getOldPswd()), "账号{}密码错误", customerDO
            .getUsername());
        LambdaUpdateWrapper<CustomerDO> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(CustomerDO::getPassword, SecureUtil.md5(req.getNewPswd()));
        wrapper.eq(CustomerDO::getId, customerDO.getId());
        this.baseMapper.update(null, wrapper);
    }

}