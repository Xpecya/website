package com.hanyin.website.bean;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "teammate")
public class TeammateShow {

    @Id
    private Integer id;
    private String name;
    private String imageUrl;
    private Integer position;
    private String introduction;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() +
               GetterService.getString(name).hashCode() +
               GetterService.getString(imageUrl).hashCode() +
               GetterService.getString(introduction).hashCode() +
               GetterService.getInteger(position).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TeammateShow)) return false;
        TeammateShow input = (TeammateShow) obj;
        return GetterService.getString(name).equals(GetterService.getString(input.name)) &&
               GetterService.getString(imageUrl).equals(GetterService.getString(input.imageUrl)) &&
               GetterService.getString(introduction).equals(GetterService.getString(input.introduction)) &&
               GetterService.getInteger(position).equals(GetterService.getInteger(input.position));
    }
}
