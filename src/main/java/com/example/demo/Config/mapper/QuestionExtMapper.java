package com.example.demo.Config.mapper;


import com.example.demo.daoPOJO.Question;

public interface QuestionExtMapper {


    int incView(Question record);
    int incCommentCount(Question record);

}