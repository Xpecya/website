package com.hanyin.website.entity;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 律所简介
 */
@Getter
@Setter
@Entity
public class Introduction implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String content;
    private String imageUrl;

    private static final long serialVersionUID = 6L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() +
               GetterService.getString(content).hashCode() +
               GetterService.getString(imageUrl).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Introduction)) return false;
        Introduction input = (Introduction) obj;
        return GetterService.getString(content).equals(GetterService.getString(input.content)) &&
               GetterService.getString(imageUrl).equals(GetterService.getString(input.imageUrl));
    }
}
