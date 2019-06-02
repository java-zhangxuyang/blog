package com.zhangxy.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Content {
    private Integer id;

    private String title;

    private String content;

    private String author;

    private Date time;

    private Boolean top;

    private Integer look;

    private String url;

    private Integer nid;

    private List<String> tagList;
}