package com.hanyin.website.repository;

import com.hanyin.website.entity.BannerImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerImageRepository extends JpaRepository<BannerImage, Integer> {

    BannerImage findByNextIdNull();
    BannerImage findByNextId(Integer nextId);

    BannerImage[] getAllBy();
}
