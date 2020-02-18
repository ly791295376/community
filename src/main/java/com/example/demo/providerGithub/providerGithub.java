package com.example.demo.providerGithub;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.GitHubUser;
import com.example.demo.dto.accessTokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;



@Component
public class providerGithub {
    public String getAccessToken(accessTokenDTO accessTokenDTO) {
        MediaType mediaTYpe = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaTYpe,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
        try (
                Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            //str = access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
            System.out.println(str);
            return str.split("&")[0].split("=")[1];
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
             String str = response.body().string();
             System.out.println(str);
             return JSON.parseObject(str, GitHubUser.class);
        }catch (Exception e){

            e.printStackTrace();
        }
        return null;
    }
}
