package com.example.demo.daoPOJO;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountID;
    private String token;
    private long gmt_Create;
    private long gmt_modified;
    private String avatar_url;

}
