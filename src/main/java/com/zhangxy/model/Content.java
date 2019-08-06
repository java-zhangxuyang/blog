package com.zhangxy.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Content implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String title;

    private String content;

    private String author;

    private Date time;

    private Boolean top;

    private Integer look;

    private String url;

    private Integer nid;
    
    private String nav;

    private List<String> tagList;
    
    private List<Integer> tidList;
}