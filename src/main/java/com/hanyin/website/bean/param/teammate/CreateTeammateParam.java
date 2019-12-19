package com.hanyin.website.bean.param.teammate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTeammateParam {
    private String sessionId;
    private String name;
    private String introduction;
    private String imageUrl;
    private Integer position;
    private String major;
}
