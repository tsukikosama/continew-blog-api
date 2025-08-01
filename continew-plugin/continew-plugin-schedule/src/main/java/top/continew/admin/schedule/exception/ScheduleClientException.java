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

package top.continew.admin.schedule.exception;

import top.continew.starter.core.exception.BaseException;

/**
 * 调度客户端异常
 *
 * @author Charles7c
 * @since 2025/5/21 22:05
 */
public class ScheduleClientException extends BaseException {

    public ScheduleClientException(String message) {
        super(message);
    }

    public ScheduleClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
