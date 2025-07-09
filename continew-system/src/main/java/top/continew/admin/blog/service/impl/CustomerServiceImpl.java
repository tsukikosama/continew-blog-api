package top.continew.admin.blog.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.admin.auth.enums.AuthTypeEnum;
import top.continew.admin.blog.model.req.CustomerLoginReq;
import top.continew.admin.common.context.RoleContext;
import top.continew.admin.common.context.UserContext;
import top.continew.admin.common.context.UserContextHolder;
import top.continew.admin.common.context.UserExtraContext;
import top.continew.admin.common.enums.DataScopeEnum;
import top.continew.admin.common.enums.DisEnableStatusEnum;
import top.continew.admin.common.util.SecureUtils;
import top.continew.admin.system.model.entity.ClientDO;
import top.continew.admin.system.model.resp.ClientResp;
import top.continew.admin.system.service.ClientService;
import top.continew.starter.core.util.ServletUtils;
import top.continew.starter.core.validation.CheckUtils;
import top.continew.starter.core.validation.ValidationUtils;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.CustomerMapper;
import top.continew.admin.blog.model.entity.CustomerDO;
import top.continew.admin.blog.model.query.CustomerQuery;
import top.continew.admin.blog.model.req.CustomerReq;
import top.continew.admin.blog.model.resp.CustomerDetailResp;
import top.continew.admin.blog.model.resp.CustomerResp;
import top.continew.admin.blog.service.CustomerService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.apache.http.impl.auth.BasicScheme.authenticate;
import static top.continew.admin.common.enums.DataScopeEnum.ALL;
import static top.continew.admin.common.enums.DataScopeEnum.SELF;
import static top.continew.admin.system.enums.PasswordPolicyEnum.PASSWORD_EXPIRATION_DAYS;


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
        CustomerDO customerDO = this.baseMapper.selectOne(Wrappers.<CustomerDO>lambdaQuery().eq(CustomerDO::getUsername, req.getUsername()));
        CheckUtils.throwIfNotNull(customerDO,"用户名{}已存在",req.getUsername());
        //存在 对密码进行加密 存入数据库中

        req.setPassword(SecureUtil.md5(req.getPassword()));
        BeanUtil.copyProperties(req,CustomerDO.class);
        //对用户随机生成昵称
        req.setNickname(RandomUtil.randomString(6));
        this.baseMapper.insert(BeanUtil.copyProperties(req,CustomerDO.class));
        return null;
    }



    @Override
    public String loginCustomer(CustomerLoginReq req) {

        // 校验客户端
        CustomerDO customerDO = this.baseMapper.selectOne(Wrappers.<CustomerDO>lambdaQuery().eq(CustomerDO::getUsername, req.getUsername()));
        CheckUtils.throwIfNull(customerDO,"账号{}不存在",req.getUsername());
        CheckUtils.throwIfNotEqual(customerDO.getPassword(),SecureUtil.md5(req.getPassword()),"账号{}密码错误",req.getUsername());
        ClientResp client = clientService.getByClientId("ef51c9a3e9046c4f2ea45142c8a8344a");

        // 获取权限、角色、密码过期天数
        Long userId = customerDO.getId();


        UserContext userContext = new UserContext();
        Set<RoleContext> roles = new HashSet<>();
        Set<String> codes = new HashSet<>();

        codes.add("admin");
        RoleContext role = new RoleContext();
        role.setId(1l);
        role.setCode("admin");
        role.setDataScope(ALL);
        roles.add(role);
        userContext.setRoles(roles);
        userContext.setRoleCodes(codes);
        userContext.setId(userId);
        BeanUtil.copyProperties(customerDO, userContext);
        // 设置登录配置参数
        SaLoginParameter loginParameter = new SaLoginParameter();
        loginParameter.setActiveTimeout(client.getActiveTimeout());
        loginParameter.setTimeout(client.getTimeout());
        loginParameter.setDeviceType(client.getClientType());
        userContext.setClientType(client.getClientType());
        loginParameter.setExtra("clientId", client.getClientId());
        userContext.setClientId(client.getClientId());
        // 登录并缓存用户信息
        StpUtil.login(userContext.getId(), loginParameter.setExtraData(BeanUtil
                .beanToMap(new UserExtraContext(ServletUtils.getRequest()))));
        UserContextHolder.setContext(userContext);

        return "Bearer " + StpUtil.getTokenValue();
    }




}