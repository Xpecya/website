package com.hanyin.website.entity;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 会话管理
 */
@Getter
@Setter
@Entity
public class Session {

    @Id
    private String id;
    private Integer userId;

    private static final long serialVersionUID = 8L;

    @Override
    public int hashCode() {
        return GetterService.getString(id).hashCode() +
               GetterService.getInteger(userId).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Session)) return false;
        Session input = (Session) obj;
        return GetterService.getInteger(userId).equals(GetterService.getInteger(input.userId)) &&
               GetterService.getString(id).equals(GetterService.getString(input.id));
    }
}
