package com.hanyin.website.entity;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 服务领域
 */
@Entity
@Getter
@Setter
public class Detail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String content;

    private static final long serialVersionUID = 2L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() +
               GetterService.getString(name).hashCode() +
               GetterService.getString(content).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Detail)) return false;
        Detail input = (Detail) obj;
        return GetterService.getString(name).equals(GetterService.getString(input.name)) &&
               GetterService.getString(content).equals(GetterService.getString(input.content));
    }
}
