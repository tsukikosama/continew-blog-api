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

package top.continew.admin.blog.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.core.exception.BusinessException;
import top.continew.starter.core.validation.CheckUtils;
import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.FriendlinkMapper;
import top.continew.admin.blog.model.entity.FriendlinkDO;
import top.continew.admin.blog.model.query.FriendlinkQuery;
import top.continew.admin.blog.model.req.FriendlinkReq;
import top.continew.admin.blog.model.resp.FriendlinkDetailResp;
import top.continew.admin.blog.model.resp.FriendlinkResp;
import top.continew.admin.blog.service.FriendlinkService;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 友链业务实现
 *
 * @author weilai
 * @since 2025/07/08 14:00
 */
@Service
@RequiredArgsConstructor
public class FriendlinkServiceImpl extends BaseServiceImpl<FriendlinkMapper, FriendlinkDO, FriendlinkResp, FriendlinkDetailResp, FriendlinkQuery, FriendlinkReq> implements FriendlinkService {
    @Override
    public Long create(FriendlinkReq req) {
        //校验网址是否可以访问
        checkUrlAccessible(req.getUrl());
        return super.create(req);
    }

    public void checkUrlAccessible(String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url += "http://" + url;
            }
            URL urls = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urls.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(true);
            int responseCode = conn.getResponseCode();
            CheckUtils.throwIfNotEqual(responseCode, 200, "网址{}不可访问", url);
        } catch (Exception e) {
            throw new BusinessException(String.format("网址 %s 不可访问", url));
        }
    }

}