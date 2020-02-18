package com.example.demo.authorizeController;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.Config.mapper.userMapper;
import com.example.demo.daoPOJO.User;
import com.example.demo.dto.GitHubUser;
import com.example.demo.dto.accessTokenDTO;
import com.example.demo.providerGithub.providerGithub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/*
    返回到callback进行两步验证获取user信息
        ①：（GitHub给用户code）通过callback的get请求中获取code，
            （用户携带code访问GitHub）用户得到code后再将id  秘钥 返回地址 封装到accessTokenDTO对象当中
            再通过okhttp的post协议（accessTokenDTO对象 -> JSON对象）  https://github.com/login/oauth/access_token 访问
        ②：GitHub得到code后确认，然后给用户一个access_token字符串进行第二步验证
            同样，用户访问GitHub的制定 get请求url+access_token GitHub得到验证后返回user
     session相当于自己的银行账户
     cookie是银行卡
     浏览器是自己，银行是服务器
     Cookie通过在客户端记录信息确定用户身份，Session通过在服务器端记录信息确定用户身份。
     Cookie通过在客户端记录信息确定用户身份，Session通过在服务器端记录信息确定用户身份。
 */
@Controller
public class authorizeControl {
    @Autowired
    private providerGithub providerGithub;
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.uri}")
    private String clientUri;
    @Autowired
    userMapper userMapper;
    @GetMapping("/callback")
    public  String callback(@RequestParam(name = "code")String code,
                            @RequestParam(name = "state")String state,
                            HttpServletRequest request,
                            HttpServletResponse response
    ){
        accessTokenDTO accessTokenDTO = new accessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setState(state);
        String accessToken = providerGithub.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GitHubUser gitHubUser = providerGithub.getUser(accessToken);
        if (gitHubUser == null) {
            //失败
            return "redirect:/";
        }else {
            //成功
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountID(String.valueOf(gitHubUser.getId()));
            user.setGmt_Create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_Create());
            user.setAvatar_url(gitHubUser.getAvatar_url());
            userMapper.insertUser(user);

            response.addCookie(new Cookie("token",token));
            //request.getSession().setAttribute("user",gitHubUser);
            return "redirect:/";
        }
    }
}
