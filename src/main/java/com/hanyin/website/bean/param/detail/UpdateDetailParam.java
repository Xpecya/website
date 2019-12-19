package com.hanyin.website.bean.param.detail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDetailParam {

    private String sessionId;
    private Integer id;
    private String name;
    private String content;
}
