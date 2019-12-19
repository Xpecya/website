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
public class IntroductionDetail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    private static final long serialVersionUID = 7L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() +
               GetterService.getString(name).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntroductionDetail)) return false;
        IntroductionDetail input = (IntroductionDetail) obj;
        return GetterService.getString(name).equals(GetterService.getString(input.name));
    }
}
