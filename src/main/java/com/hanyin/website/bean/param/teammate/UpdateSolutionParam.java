package com.hanyin.website.bean.param.teammate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSolutionParam {

    private String sessionId;
    private Integer solutionId;
    private Integer teammateId;
    private String content;
}
