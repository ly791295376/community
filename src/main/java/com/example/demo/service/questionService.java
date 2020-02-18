package com.example.demo.service;

import com.example.demo.Config.mapper.QuestionMapper;
import com.example.demo.Config.mapper.userMapper;
import com.example.demo.daoPOJO.Question;
import com.example.demo.daoPOJO.User;
import com.example.demo.dto.QuestionDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class questionService {
    @Autowired
    private QuestionMapper qusetionMapper;
    @Autowired
    private userMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = qusetionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question:
             questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
