package com.hanyin.website.repository;

import com.hanyin.website.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution, Integer> {

    List<Solution> findAllByTeammateId(Integer teammateId);
}
