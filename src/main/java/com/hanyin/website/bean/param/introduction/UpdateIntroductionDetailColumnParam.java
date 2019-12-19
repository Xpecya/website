package com.hanyin.website.bean.param.introduction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateIntroductionDetailColumnParam {
    private String sessionId;
    private Integer id;
    private Integer detailId;
    private String title;
    private String content;
}
