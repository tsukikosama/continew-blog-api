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

package top.continew.admin.blog.service;

import top.continew.admin.blog.model.entity.BlogTypeDO;
import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.BlogTypeQuery;
import top.continew.admin.blog.model.req.BlogTypeReq;
import top.continew.admin.blog.model.resp.BlogTypeDetailResp;
import top.continew.admin.blog.model.resp.BlogTypeResp;

import java.util.List;

/**
 * 分类业务接口
 *
 * @author weilai
 * @since 2025/07/07 18:25
 */
public interface BlogTypeService extends BaseService<BlogTypeResp, BlogTypeDetailResp, BlogTypeQuery, BlogTypeReq> {
    void batchSaveBlogType(List<Long> tagId, Long blogId);

    List<BlogTypeDO> getBlogTagByBlogId(Long id);
}