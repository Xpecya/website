package com.hanyin.website.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateIntroductionDetailParam {

    private String sessionId;
    private String name;
    private List<ColumnParam> params;
}
