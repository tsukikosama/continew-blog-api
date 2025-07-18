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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件夹计算大小响应参数
 *
 * @author Charles7c
 * @since 2025/5/16 21:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件夹计算大小响应参数")
public class FileDirCalcSizeResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 大小（字节）
     */
    @Schema(description = "大小（字节）", example = "4096")
    private Long size;
}