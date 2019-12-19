package com.hanyin.website.entity;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class IntroductionDetailColumn implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer introductionDetailId;
    private String title;
    private String content;

    private static final long serialVersionUID = 9L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(introductionDetailId).hashCode() +
               GetterService.getString(title).hashCode() +
               GetterService.getString(content).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntroductionDetailColumn)) return false;
        IntroductionDetailColumn input = (IntroductionDetailColumn) obj;
        return GetterService.getInteger(introductionDetailId).equals(GetterService.getInteger(input.introductionDetailId)) &&
               GetterService.getString(title).equals(GetterService.getString(input.title)) &&
               GetterService.getString(content).equals(GetterService.getString(input.content));
    }
}
