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
public class BannerImage implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String imageUrl;
    private Integer prevId;
    private Integer nextId;

    private static final long serialVersionUID = 11L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(nextId).hashCode() +
               GetterService.getString(imageUrl).hashCode() +
               GetterService.getInteger(prevId).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BannerImage)) return false;
        BannerImage input = (BannerImage) obj;
        return GetterService.getString(imageUrl).equals(GetterService.getString(input.imageUrl)) &&
               GetterService.getInteger(nextId).equals(GetterService.getInteger(input.nextId)) &&
               GetterService.getInteger(prevId).equals(GetterService.getInteger(input.prevId));
    }
}
