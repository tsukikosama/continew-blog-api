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

package top.continew.admin.blog.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.continew.admin.blog.model.entity.TagDO;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Api博客信息")
public class ApiBlogResp {
    private Long id;
    private String title;
    private String picture;
    private String content;
    private Integer visit;
    private String simpleTitle;
    private String userId;
    private String userName;
    private List<TagDO> tagList;
    private Integer reviewNum;
    private Integer likeNumber;
    private LocalDateTime createTime;
}
