package com.hanyin.website.entity;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 用户
 */
@Entity
@Getter
@Setter
public class UserTable implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;

    private static final long serialVersionUID = 1L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() +
               GetterService.getString(username).hashCode() +
               GetterService.getString(password).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserTable)) return false;
        UserTable input = (UserTable) obj;
        return GetterService.getString(username).equals(GetterService.getString(input.username)) &&
               GetterService.getString(password).equals(GetterService.getString(input.password));
    }
}
