package com.example.demo.daoPOJO;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    //连接question和user的外键，creator是user的Id
    private Integer creator;
    private Integer comment_count;
    private Integer view_Count;
    private Integer like_Count;
    private String tag;
    private String description;
}