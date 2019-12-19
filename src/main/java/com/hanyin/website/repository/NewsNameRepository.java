package com.hanyin.website.repository;

import com.hanyin.website.bean.NewsName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsNameRepository extends JpaRepository<NewsName, Integer> {
}
