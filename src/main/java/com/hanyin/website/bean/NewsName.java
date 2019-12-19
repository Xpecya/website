package com.hanyin.website.bean;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "news")
public class NewsName implements Serializable {

    @Id
    private Integer id;
    private String name;
    private Date publishDate;

    private static final long serialVersionUID = 7L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() +
               GetterService.getString(name).hashCode() +
               GetterService.getDate(publishDate).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NewsName)) return false;
        NewsName input = (NewsName) obj;
        return GetterService.getString(name).equals(GetterService.getString(input.name)) &&
               GetterService.getDate(publishDate).equals(GetterService.getDate(input.publishDate));
    }
}
