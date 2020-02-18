package com.example.demo.authorizeController;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.Config.mapper.QuestionMapper;
import com.example.demo.Config.mapper.userMapper;
import com.example.demo.daoPOJO.User;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.service.questionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class indexController {
    @Autowired
    private userMapper userMapper;
    @Autowired
    private com.example.demo.service.questionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        /*
            通过用户第一次登录时随机生成的UUID放入到cookie当中，
            当下一次用户登录时通过检测该用户当中的cookie是否与在数据库保存的是否一致
            来判断用户是否需要再次登录（通过设置cookie的时间来），算是一种令牌判断登录状态
         */
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "index";
        }
        for (Cookie cookie :
                cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User userByToken = userMapper.findUserByToken(token);
                if (userByToken != null) {
                    request.getSession().setAttribute("user", userByToken);
                }
                break;
            }
        }
        /*
            拿取列表信息
         */
        List<QuestionDTO> questionDTOList = questionService.list();
        System.out.println(questionDTOList);
        model.addAttribute("questions",questionDTOList);

        return "index";
    }
}
