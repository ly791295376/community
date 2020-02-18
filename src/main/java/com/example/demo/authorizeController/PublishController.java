package com.example.demo.authorizeController;

import com.example.demo.Config.mapper.QuestionMapper;
import com.example.demo.Config.mapper.userMapper;
import com.example.demo.daoPOJO.Question;
import com.example.demo.daoPOJO.User;
import com.example.demo.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private userMapper userMapper;
    @GetMapping("/publish")
    public String publish(Model model){

      //  List<TagDTO> tagDTOS = TagCache.get();
   //     model.addAttribute("tags",tagDTOS);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "id", required = false) Long id,
            Model model,
            HttpServletRequest request
            ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("id",id);
//     //   model.addAttribute("tags",TagCache.get());
//
        if (title == null || "".equals(title)){
            model.addAttribute("error","标题不能为空！");
            return "publish";
        }
        if (description == null || "".equals(description)){
            model.addAttribute("error","内容不能为空！");
            return "publish";
        }
        if (tag == null || "".equals(tag)){
            model.addAttribute("error","标签不能为空！");
            return "publish";
        }
       // String errorTag = TagCache.errorTag(tag);
//        if(StringUtils.isNotBlank(errorTag)){
//            model.addAttribute("error","此标签不存在："+errorTag);
//            return "publish";
//        }
    //    User user = (User) request.getSession().getAttribute("user");
        User user = null;

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "index";
        }
        for (Cookie cookie :
                cookies) {
            if (cookie.getName().equals("token")) {
                String token =  cookie.getValue();
                user = userMapper.findUserByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }
        Question question = new Question();
        question.setCreator(user.getId());
        question.setTag(tag);
        question.setDescription(description);
        question.setTitle(title);
        question.setId(id);
        questionMapper.create(question);
       // questionService.createOrUpdate(question);
        return "redirect:/";

    }

}
