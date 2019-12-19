package com.hanyin.website.bean;

import com.hanyin.website.entity.Teammate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeammateDetail {

    private Integer id;
    private String name;
    private String introduction;
    private String imageUrl;
    private Integer position;
    private String major;
    private List<String> solutions;

    public TeammateDetail(Teammate teammate) {
        this.id = teammate.getId();
        this.name = teammate.getName();
        this.introduction = teammate.getIntroduction();
        this.imageUrl = teammate.getImageUrl();
        this.position = teammate.getPosition();
        this.major = teammate.getMajor();
    }
}
