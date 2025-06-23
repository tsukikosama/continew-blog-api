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

package top.continew.admin.system.config.file;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import top.continew.admin.common.enums.DisEnableStatusEnum;
import top.continew.admin.system.model.entity.StorageDO;
import top.continew.admin.system.service.StorageService;

import java.util.List;

/**
 * 文件存储配置加载器
 *
 * @author Charles7c
 * @since 2023/12/24 22:31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileStorageConfigLoader implements ApplicationRunner {

    private final StorageService storageService;

    @Override
    public void run(ApplicationArguments args) {
        List<StorageDO> list = storageService.lambdaQuery().eq(StorageDO::getStatus, DisEnableStatusEnum.ENABLE).list();
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(storageService::load);
    }
}
