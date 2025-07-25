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

import top.continew.admin.system.model.query.MessageQuery;
import top.continew.admin.system.model.req.MessageReq;
import top.continew.admin.system.model.resp.message.MessageDetailResp;
import top.continew.admin.system.model.resp.message.MessageResp;
import top.continew.admin.system.model.resp.message.MessageUnreadResp;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

import java.util.List;

/**
 * 消息业务接口
 *
 * @author Bull-BCLS
 * @author Charles7c
 * @since 2023/10/15 19:05
 */
public interface MessageService {

    /**
     * 分页查询列表
     *
     * @param query     查询条件
     * @param pageQuery 分页查询条件
     * @return 分页列表信息
     */
    PageResp<MessageResp> page(MessageQuery query, PageQuery pageQuery);

    /**
     * 查询详情
     *
     * @param id ID
     * @return 详情信息
     */
    MessageDetailResp get(Long id);

    /**
     * 将消息标记已读
     *
     * @param ids    消息ID（为空则将所有消息标记已读）
     * @param userId 用户ID
     */
    void readMessage(List<Long> ids, Long userId);

    /**
     * 查询未读消息数量
     *
     * @param userId   用户 ID
     * @param isDetail 是否查询详情
     * @return 未读消息数量
     */
    MessageUnreadResp countUnreadByUserId(Long userId, Boolean isDetail);

    /**
     * 新增
     *
     * @param req        请求参数
     * @param userIdList 接收人列表
     */
    void add(MessageReq req, List<String> userIdList);

    /**
     * 删除
     *
     * @param ids ID 列表
     */
    void delete(List<Long> ids);
}