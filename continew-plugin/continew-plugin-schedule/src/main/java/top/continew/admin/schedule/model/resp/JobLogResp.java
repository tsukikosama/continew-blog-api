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

package top.continew.admin.schedule.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.continew.admin.schedule.enums.JobExecuteReasonEnum;
import top.continew.admin.schedule.enums.JobExecuteStatusEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务日志响应参数
 *
 * @author KAI
 * @author Charles7c
 * @since 2024/6/27 22:50
 */
@Data
@Schema(description = "任务日志响应参数")
public class JobLogResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    private Long id;

    /**
     * 任务组
     */
    @Schema(description = "任务组", example = "continew-admin")
    private String groupName;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称", example = "定时任务1")
    private String jobName;

    /**
     * 任务 ID
     */
    @Schema(description = "任务ID", example = "1")
    private Long jobId;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态", example = "3")
    private JobExecuteStatusEnum taskBatchStatus;

    /**
     * 操作原因
     */
    @Schema(description = "操作原因", example = "0")
    private JobExecuteReasonEnum operationReason;

    /**
     * 执行器类型
     */
    @Schema(description = "执行器类型", example = "1")
    private Integer executorType;

    /**
     * 执行器名称
     */
    @Schema(description = "执行器名称", example = "test")
    private String executorInfo;

    /**
     * 执行时间
     */
    @Schema(description = "执行时间", example = "2023-08-08 08:08:08", type = "string")
    private LocalDateTime executionAt;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2023-08-08 08:08:08", type = "string")
    private LocalDateTime createDt;
}
