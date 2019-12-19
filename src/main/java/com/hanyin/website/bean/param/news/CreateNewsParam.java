package com.hanyin.website.bean.param.news;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNewsParam {
    private String sessionId;
    private String name;
    private String content;
    private String source;
}
