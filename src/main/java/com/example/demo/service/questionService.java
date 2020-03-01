package com.example.demo.service;

import com.example.demo.Config.mapper.QuestionMapper;
import com.example.demo.Config.mapper.userMapper;
import com.example.demo.daoPOJO.Question;
import com.example.demo.daoPOJO.User;
import com.example.demo.dto.QuestionDTO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class questionService {
    @Autowired
    private QuestionMapper qusetionMapper;
    @Autowired
    private userMapper userMapper;

    public List<QuestionDTO>
    pagelist(int pageNum, int pageSize, Model model){
        PageHelper.startPage(pageNum, pageSize);
        Page<Question> questions = qusetionMapper.listPage();
        PageInfo pageInfo=new PageInfo(questions);
        System.out.println(pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question:
                questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //两个相似的javaBean的转变
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;    }
}
