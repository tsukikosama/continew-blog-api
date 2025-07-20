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

package top.continew.admin.api;

import okhttp3.*;

import java.io.IOException;

public class GeMiniApiController {
    public static void main(String[] args) throws IOException {
        //        Client client = Client.builder().apiKey("AIzaSyDSyFQb22M_aZhJLdfLS3bAe7wzfzCcbvE").build();
        //
        //        GenerateContentResponse response =
        //                client.models.generateContent(
        //                        "gemini-2.5-flash",
        //                        "how do you do ",
        //                        null);
        //        System.out.println(response.text());
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create("{\"message\":\"你好\"}", MediaType.get("application/json"));
        Request request = new Request.Builder().url("https://mirai-gemini-47-d6hyt6sw2smm.deno.dev/generate")
            .post(body)
            .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
