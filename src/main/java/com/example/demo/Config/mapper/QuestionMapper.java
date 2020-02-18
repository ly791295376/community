package com.example.demo.Config.mapper;

import com.example.demo.daoPOJO.Question;


import java.util.List;

public interface QuestionMapper {
      void create(Question question);

      List<Question> list();
//    long countByExample(QuestionExample example);
//
//    int deleteByExample(QuestionExample example);
//
//    int deleteByPrimaryKey(Long id);
//
//    int insert(Question record);
//
//    int insertSelective(Question record);
//
//    List<Question> selectByExampleWithBLOBsWithRowbounds(QuestionExample example, RowBounds rowBounds);
//
//    List<Question> selectByExampleWithBLOBs(QuestionExample example);
//
//    List<Question> selectByExampleWithRowbounds(QuestionExample example, RowBounds rowBounds);
//
//    List<Question> selectByExample(QuestionExample example);
//
//    Question selectByPrimaryKey(Long id);
//
//    int updateByExampleSelective(@Param("record") Question record, @Param("example") QuestionExample example);
//
//    int updateByExampleWithBLOBs(@Param("record") Question record, @Param("example") QuestionExample example);
//
//    int updateByExample(@Param("record") Question record, @Param("example") QuestionExample example);
//
//    int updateByPrimaryKeySelective(Question record);
//
//    int updateByPrimaryKeyWithBLOBs(Question record);
//
//    int updateByPrimaryKey(Question record);
}