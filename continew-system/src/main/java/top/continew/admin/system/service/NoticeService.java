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

package top.continew.admin.system.service;

import top.continew.admin.system.enums.NoticeMethodEnum;
import top.continew.admin.system.model.entity.NoticeDO;
import top.continew.admin.system.model.query.NoticeQuery;
import top.continew.admin.system.model.req.NoticeReq;
import top.continew.admin.system.model.resp.dashboard.DashboardNoticeResp;
import top.continew.admin.system.model.resp.notice.NoticeDetailResp;
import top.continew.admin.system.model.resp.notice.NoticeResp;
import top.continew.starter.data.mp.service.IService;
import top.continew.starter.extension.crud.service.BaseService;

import java.util.List;

/**
 * 公告业务接口
 *
 * @author Charles7c
 * @since 2023/8/20 10:55
 */
public interface NoticeService extends BaseService<NoticeResp, NoticeDetailResp, NoticeQuery, NoticeReq>, IService<NoticeDO> {

    /**
     * 发布公告
     *
     * @param notice 公告信息
     */
    void publish(NoticeDO notice);

    /**
     * 查询未读公告 ID 列表
     *
     * @param method 通知方式
     * @param userId 用户 ID
     * @return 未读公告 ID 响应参数
     */
    List<Long> listUnreadIdsByUserId(NoticeMethodEnum method, Long userId);

    /**
     * 阅读公告
     *
     * @param id     公告 ID
     * @param userId 用户 ID
     */
    void readNotice(Long id, Long userId);

    /**
     * 查询仪表盘公告列表
     *
     * @return 仪表盘公告列表
     */
    List<DashboardNoticeResp> listDashboard();
}