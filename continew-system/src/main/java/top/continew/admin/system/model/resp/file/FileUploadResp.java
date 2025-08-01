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

package top.continew.admin.system.model.resp.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 文件上传响应参数
 *
 * @author Charles7c
 * @since 2024/3/6 22:26
 */
@Data
@Builder
@Schema(description = "文件上传响应参数")
public class FileUploadResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件 id
     */
    @Schema(description = "文件 id", example = "1897293810343682049")
    private String id;

    /**
     * 文件 URL
     */
    @Schema(description = "文件 URL", example = "http://localhost:8000/file/65e87dc3fb377a6fb58bdece.jpg")
    private String url;

    /**
     * 缩略图文件 URL
     */
    @Schema(description = "缩略图文件 URL", example = "http://localhost:8000/file/65e87dc3fb377a6fb58bdece.jpg")
    private String thUrl;

    /**
     * 元数据
     */
    @Schema(description = "元数据")
    private Map<String, String> metadata;
}
