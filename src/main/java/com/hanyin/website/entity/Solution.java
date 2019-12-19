package com.hanyin.website.entity;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 律师解决过的案件
 */
@Entity
@Getter
@Setter
public class Solution {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer teammateId;
    private String content;

    private static final long serialVersionUID = 5L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() +
               GetterService.getInteger(teammateId).hashCode() +
               GetterService.getString(content).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Solution)) return false;
        Solution input = (Solution) obj;
        return GetterService.getInteger(teammateId).equals(GetterService.getInteger(input.teammateId)) &&
               GetterService.getString(content).equals(GetterService.getString(input.content));
    }
}
