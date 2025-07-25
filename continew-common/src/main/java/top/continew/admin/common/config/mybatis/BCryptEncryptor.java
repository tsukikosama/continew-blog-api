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

package top.continew.admin.common.config.mybatis;

import org.springframework.security.crypto.password.PasswordEncoder;
import top.continew.starter.security.crypto.encryptor.IEncryptor;
import top.continew.starter.security.password.constant.PasswordEncoderConstants;

/**
 * BCrypt 加/解密处理器（不可逆）
 *
 * @author Charles7c
 * @since 2024/2/8 22:29
 */
public class BCryptEncryptor implements IEncryptor {

    private final PasswordEncoder passwordEncoder;

    public BCryptEncryptor(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String plaintext, String password, String publicKey) {
        // 如果已经是 BCrypt 加密格式，直接返回
        if (PasswordEncoderConstants.BCRYPT_PATTERN.matcher(plaintext).matches()) {
            return plaintext;
        }
        return passwordEncoder.encode(plaintext);
    }

    @Override
    public String decrypt(String ciphertext, String password, String privateKey) {
        return ciphertext;
    }
}
