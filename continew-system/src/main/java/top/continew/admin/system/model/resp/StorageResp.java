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

package top.continew.admin.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.continew.admin.common.model.resp.BaseDetailResp;
import top.continew.admin.common.enums.DisEnableStatusEnum;
import top.continew.admin.system.enums.StorageTypeEnum;
import top.continew.starter.security.mask.annotation.JsonMask;

import java.io.Serial;

/**
 * 存储响应参数
 *
 * @author Charles7c
 * @since 2023/12/26 22:09
 */
@Data
@Schema(description = "存储响应参数")
public class StorageResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Schema(description = "名称", example = "存储1")
    private String name;

    /**
     * 编码
     */
    @Schema(description = "编码", example = "local")
    private String code;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    private DisEnableStatusEnum status;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "2")
    private StorageTypeEnum type;

    /**
     * Access Key
     */
    @Schema(description = "Access Key", example = "")
    private String accessKey;

    /**
     * Secret Key
     */
    @Schema(description = "Secret Key", example = "")
    @JsonMask(left = 4, right = 3)
    private String secretKey;

    /**
     * Endpoint
     */
    @Schema(description = "Endpoint", example = "")
    private String endpoint;

    /**
     * Bucket/存储路径
     */
    @Schema(description = "Bucket/存储路径", example = "C:/continew-admin/data/file/")
    private String bucketName;

    /**
     * 域名/访问路径
     */
    @Schema(description = "域名", example = "http://localhost:8000/file")
    private String domain;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "存储描述")
    private String description;

    /**
     * 是否为默认存储
     */
    @Schema(description = "是否为默认存储", example = "true")
    private Boolean isDefault;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    private Integer sort;

    @Override
    public Boolean getDisabled() {
        return this.getIsDefault();
    }

}