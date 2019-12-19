package com.hanyin.website.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Banner implements Serializable {

    @Id
    private Integer id;
    private Integer speed;

    private static final long serialVersionUID = 10L;
}
