package com.hanyin.website.entity;

import com.hanyin.website.service.GetterService;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 新闻
 */
@Entity
@Getter
@Setter
public class News implements Serializable {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String content;
    private String source;
    private Date publishDate;
    private Integer checkTimes;

    private static final long serialVersionUID = 3L;

    @Override
    public int hashCode() {
        return GetterService.getInteger(id).hashCode() +
               GetterService.getString(name).hashCode() +
               GetterService.getString(content).hashCode() +
               GetterService.getString(source).hashCode() +
               GetterService.getDate(publishDate).hashCode() +
               GetterService.getInteger(checkTimes).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof News)) return false;
        News input = (News) obj;
        return GetterService.getString(name).equals(GetterService.getString(input.name)) &&
               GetterService.getString(content).equals(GetterService.getString(input.content)) &&
               GetterService.getString(source).equals(GetterService.getString(input.source)) &&
               GetterService.getDate(publishDate).equals(GetterService.getDate(input.publishDate)) &&
               GetterService.getInteger(checkTimes).equals(GetterService.getInteger(input.getCheckTimes()));
    }

    public Map<String, Object> toJsonMap() {
        Map<String, Object> jsonMap = new HashMap<>();
        if (id != null) jsonMap.put("id", id);
        if (name != null) jsonMap.put("name", name);
        if (content != null) jsonMap.put("content", content);
        if (source != null) jsonMap.put("source", source);
        if (publishDate != null)jsonMap.put("publishDate", FORMAT.format(publishDate));
        if (checkTimes != null) jsonMap.put("checkTimes", checkTimes);
        return jsonMap;
    }
}
