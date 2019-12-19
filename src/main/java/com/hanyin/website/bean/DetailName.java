package com.hanyin.website.bean;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 只包含服务领域名称
 */
@Getter
@Setter
@Entity
@Table(name = "detail")
public class DetailName implements Serializable {

    @Id
    private Integer id;
    private String name;

    private static final long serialVersionUID = 6L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() + GetterService.getString(name).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof DetailName)) return false;
        DetailName detailName = (DetailName) obj;
        return GetterService.getString(name).equals(GetterService.getString(detailName.name));
    }
}
