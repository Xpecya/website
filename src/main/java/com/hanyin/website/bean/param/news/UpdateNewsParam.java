package com.hanyin.website.bean.param.news;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNewsParam {
    private String sessionId;
    private Integer id;
    private String name;
    private String content;
    private String source;
}
