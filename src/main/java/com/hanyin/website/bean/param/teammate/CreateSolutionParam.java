package com.hanyin.website.bean.param.teammate;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateSolutionParam {
    private String sessionId;
    private Integer id;
    private List<String> contents;
}
