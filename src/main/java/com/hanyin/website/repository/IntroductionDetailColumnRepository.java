package com.hanyin.website.repository;

import com.hanyin.website.entity.IntroductionDetailColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntroductionDetailColumnRepository extends JpaRepository<IntroductionDetailColumn, Integer> {

    List<IntroductionDetailColumn> findAllByIntroductionDetailId(Integer introductionDetailId);
}
