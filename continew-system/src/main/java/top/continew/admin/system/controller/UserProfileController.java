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

package top.continew.admin.system.controller;

import com.xkcoding.justauth.autoconfigure.JustAuthProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.AuthRequestBuilder;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.continew.admin.common.constant.CacheConstants;
import top.continew.admin.common.context.UserContextHolder;
import top.continew.admin.common.util.SecureUtils;
import top.continew.admin.system.enums.SocialSourceEnum;
import top.continew.admin.system.model.entity.user.UserSocialDO;
import top.continew.admin.system.model.req.user.UserBasicInfoUpdateReq;
import top.continew.admin.system.model.req.user.UserEmailUpdateReq;
import top.continew.admin.system.model.req.user.UserPasswordUpdateReq;
import top.continew.admin.system.model.req.user.UserPhoneUpdateReq;
import top.continew.admin.system.model.resp.AvatarResp;
import top.continew.admin.system.model.resp.user.UserSocialBindResp;
import top.continew.admin.system.service.UserService;
import top.continew.admin.system.service.UserSocialService;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.exception.BadRequestException;
import top.continew.starter.core.util.ExceptionUtils;
import top.continew.starter.core.validation.ValidationUtils;

import java.io.IOException;
import java.util.List;

/**
 * 个人信息 API
 *
 * @author Charles7c
 * @since 2023/1/2 11:41
 */
@Tag(name = "个人信息 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileController {

    private static final String DECRYPT_FAILED = "当前密码解密失败";
    private static final String CAPTCHA_EXPIRED = "验证码已失效";
    private final UserService userService;
    private final UserSocialService userSocialService;
    private final JustAuthProperties authProperties;

    @Operation(summary = "修改头像", description = "用户修改个人头像")
    @PatchMapping("/avatar")
    public AvatarResp updateAvatar(@NotNull(message = "头像不能为空") MultipartFile avatarFile) throws IOException {
        ValidationUtils.throwIf(avatarFile::isEmpty, "头像不能为空");
        String newAvatar = userService.updateAvatar(avatarFile, UserContextHolder.getUserId());
        return AvatarResp.builder().avatar(newAvatar).build();
    }

    @Operation(summary = "修改基础信息", description = "修改用户基础信息")
    @PatchMapping("/basic/info")
    public void updateBasicInfo(@Validated @RequestBody UserBasicInfoUpdateReq req) {
        userService.updateBasicInfo(req, UserContextHolder.getUserId());
    }

    @Operation(summary = "修改密码", description = "修改用户登录密码")
    @PatchMapping("/password")
    public void updatePassword(@Validated @RequestBody UserPasswordUpdateReq updateReq) {
        String rawOldPassword = ExceptionUtils.exToNull(() -> SecureUtils.decryptByRsaPrivateKey(updateReq
            .getOldPassword()));
        ValidationUtils.throwIfNull(rawOldPassword, DECRYPT_FAILED);
        String rawNewPassword = ExceptionUtils.exToNull(() -> SecureUtils.decryptByRsaPrivateKey(updateReq
            .getNewPassword()));
        ValidationUtils.throwIfNull(rawNewPassword, "新密码解密失败");
        userService.updatePassword(rawOldPassword, rawNewPassword, UserContextHolder.getUserId());
    }

    @Operation(summary = "修改手机号", description = "修改手机号")
    @PatchMapping("/phone")
    public void updatePhone(@Validated @RequestBody UserPhoneUpdateReq updateReq) {
        String rawOldPassword = ExceptionUtils.exToNull(() -> SecureUtils.decryptByRsaPrivateKey(updateReq
            .getOldPassword()));
        ValidationUtils.throwIfBlank(rawOldPassword, DECRYPT_FAILED);
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + updateReq.getPhone();
        String captcha = RedisUtils.get(captchaKey);
        ValidationUtils.throwIfBlank(captcha, CAPTCHA_EXPIRED);
        ValidationUtils.throwIfNotEqualIgnoreCase(updateReq.getCaptcha(), captcha, "验证码不正确");
        RedisUtils.delete(captchaKey);
        userService.updatePhone(updateReq.getPhone(), rawOldPassword, UserContextHolder.getUserId());
    }

    @Operation(summary = "修改邮箱", description = "修改用户邮箱")
    @PatchMapping("/email")
    public void updateEmail(@Valid @RequestBody UserEmailUpdateReq updateReq) {
        String rawOldPassword = ExceptionUtils.exToNull(() -> SecureUtils.decryptByRsaPrivateKey(updateReq
            .getOldPassword()));
        ValidationUtils.throwIfBlank(rawOldPassword, DECRYPT_FAILED);
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + updateReq.getEmail();
        String captcha = RedisUtils.get(captchaKey);
        ValidationUtils.throwIfBlank(captcha, CAPTCHA_EXPIRED);
        ValidationUtils.throwIfNotEqualIgnoreCase(updateReq.getCaptcha(), captcha, "验证码不正确");
        RedisUtils.delete(captchaKey);
        userService.updateEmail(updateReq.getEmail(), rawOldPassword, UserContextHolder.getUserId());
    }

    @Operation(summary = "查询绑定的三方账号", description = "查询绑定的三方账号")
    @GetMapping("/social")
    public List<UserSocialBindResp> listSocialBind() {
        List<UserSocialDO> userSocialList = userSocialService.listByUserId(UserContextHolder.getUserId());
        return userSocialList.stream().map(userSocial -> {
            String source = userSocial.getSource();
            UserSocialBindResp userSocialBind = new UserSocialBindResp();
            userSocialBind.setSource(source);
            userSocialBind.setDescription(SocialSourceEnum.valueOf(source).getDescription());
            return userSocialBind;
        }).toList();
    }

    @Operation(summary = "绑定三方账号", description = "绑定三方账号")
    @Parameter(name = "source", description = "来源", example = "gitee", in = ParameterIn.PATH)
    @PostMapping("/social/{source}")
    public void bindSocial(@PathVariable String source, @RequestBody AuthCallback callback) {
        AuthRequest authRequest = this.getAuthRequest(source);
        AuthResponse<AuthUser> response = authRequest.login(callback);
        ValidationUtils.throwIf(!response.ok(), response.getMsg());
        AuthUser authUser = response.getData();
        userSocialService.bind(authUser, UserContextHolder.getUserId());
    }

    @Operation(summary = "解绑三方账号", description = "解绑三方账号")
    @Parameter(name = "source", description = "来源", example = "gitee", in = ParameterIn.PATH)
    @DeleteMapping("/social/{source}")
    public void unbindSocial(@PathVariable String source) {
        userSocialService.deleteBySourceAndUserId(source, UserContextHolder.getUserId());
    }

    private AuthRequest getAuthRequest(String source) {
        try {
            AuthConfig authConfig = authProperties.getType().get(source.toUpperCase());
            return AuthRequestBuilder.builder().source(source).authConfig(authConfig).build();
        } catch (Exception e) {
            throw new BadRequestException("暂不支持 [%s] 平台账号登录".formatted(source));
        }
    }
}
