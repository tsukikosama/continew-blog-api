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

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.TagQuery;
import top.continew.admin.blog.model.req.TagReq;
import top.continew.admin.blog.model.resp.TagDetailResp;
import top.continew.admin.blog.model.resp.TagResp;

/**
 * 标签业务接口
 *
 * @author weilai
 * @since 2025/07/08 13:45
 */
public interface TagService extends BaseService<TagResp, TagDetailResp, TagQuery, TagReq> {}