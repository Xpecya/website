package com.hanyin.website.entity;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 团队成员
 */
@Entity
@Getter
@Setter
public class Teammate {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String introduction;
    private String imageUrl;
    private Integer position; // 排名编号 数字越大显示越靠前
    private String major;

    private static final long serialVersionUID = 4L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() +
               GetterService.getString(name).hashCode() +
               GetterService.getString(introduction).hashCode() +
               GetterService.getString(major).hashCode() +
               GetterService.getString(imageUrl).hashCode() +
               GetterService.getInteger(position).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Teammate)) return false;
        Teammate input = (Teammate) obj;
        return GetterService.getString(name).equals(GetterService.getString(input.name)) &&
               GetterService.getString(introduction).equals(GetterService.getString(input.introduction)) &&
               GetterService.getString(major).equals(GetterService.getString(input.major)) &&
               GetterService.getString(imageUrl).equals(GetterService.getString(input.imageUrl)) &&
               GetterService.getInteger(position).equals(GetterService.getInteger(input.position));
    }
}
