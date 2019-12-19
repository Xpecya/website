package com.hanyin.website.service;

import com.hanyin.website.bean.BannerImageShow;
import com.hanyin.website.entity.Banner;
import com.hanyin.website.entity.BannerImage;
import com.hanyin.website.repository.BannerImageRepository;
import com.hanyin.website.repository.BannerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BannerService {

    /**
     * 轮播图对象只有一条数据，id为1
     */
    private static final Integer ID = 1;

    private BannerRepository bannerRepository;
    private BannerImageRepository bannerImageRepository;

    public BannerService(BannerRepository bannerRepository, BannerImageRepository bannerImageRepository) {
        this.bannerRepository = bannerRepository;
        this.bannerImageRepository = bannerImageRepository;
    }

    /**
     * 获取轮播图配置
     * @return 配置
     */
    public String getConfig() {
        Banner banner = bannerRepository.findById(ID).get();
        Response<Banner> response = new Response<>();
        response.setStatus(true);
        response.setData(banner);
        return response.response();
    }

    /**
     * 获取轮播图内容
     * 返回列表按照链表顺序从头到尾依次排序
     * @return 内容
     */
    public String getBannerImages() {
        Response<List<BannerImageShow>> response = new Response<>();
        BannerImage[] images = bannerImageRepository.getAllBy();

        int length = images.length;
        boolean status = length > 0;
        if (!status) response.setErrorMessage("images not found!");
        else {
            boolean[] checkArray = new boolean[length];
            List<BannerImageShow> resultImages = new LinkedList<>();
            Integer nextId = null;
            Integer counter = 0;
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    if (checkArray[j]) continue;
                    BannerImage image = images[j];
                    Integer prevId = image.getPrevId();
                    if ((prevId != null && prevId.equals(nextId)) || (prevId == null || nextId == null)) {
                        BannerImageShow show = new BannerImageShow();
                        show.setImageUrl(image.getImageUrl());
                        resultImages.add(show);
                        checkArray[j] = true;
                        nextId = image.getNextId();
                        counter += 1;
                    }
                }
                if (counter.equals(length)) break;
            }
            response.setStatus(true);
            response.setData(resultImages);
        }
        return response.response();
    }

    /**
     * 修改轮播图配置
     * @return 修改结果
     */
    public String updateConfig(Integer speed) {
        Response<String> response = new Response<>();
        if (speed == null || speed < 0) response.setErrorMessage("速度缺失或不合法!");
        else {
            Banner banner = bannerRepository.findById(ID).get();
            banner.setSpeed(speed);
            bannerRepository.saveAndFlush(banner);
            response.setStatus(true);
            response.setData("修改成功！");
        }
        return response.response();
    }

    /**
     * 插入一张图片，位置默认为最后一个
     * @param imageUrl 图片url
     * @return 插入结果
     */
    public String createBannerImage(String imageUrl) {
        Response<String> response = new Response<>();
        if (StringUtils.isEmpty(imageUrl)) response.setErrorMessage("no image!");
        else {
            BannerImage bannerImage = new BannerImage();
            bannerImage.setImageUrl(imageUrl);
            if (bannerImageRepository.count() > 0) {
                BannerImage tailImage = bannerImageRepository.findByNextIdNull();
                bannerImage.setPrevId(tailImage.getId());
                bannerImage = bannerImageRepository.save(bannerImage);
                tailImage.setNextId(bannerImage.getId());
                bannerImageRepository.saveAndFlush(tailImage);
            } else bannerImageRepository.save(bannerImage);
            response.setStatus(true);
            response.setData("上传成功！");
        }
        return response.response();
    }

    /**
     * 删除一张轮播图
     * @return 删除结果
     */
    public String deleteBannerImage(Integer id) {
        Response<String> response = new Response<>();
        if (id == null) response.setErrorMessage("id is null!");
        else {
            Optional<BannerImage> optional = bannerImageRepository.findById(id);
            if (optional.isEmpty()) response.setErrorMessage("没找到指定图片！");
            else {
                BannerImage image = optional.get();
                Integer nextImageId = image.getNextId();
                if (nextImageId != null) {
                    BannerImage prevImage = bannerImageRepository.findByNextId(id);
                    if (prevImage != null) {
                        prevImage.setNextId(nextImageId);
                        bannerImageRepository.saveAndFlush(prevImage);
                    }
                }
                bannerImageRepository.deleteById(id);
                response.setStatus(true);
                response.setData("删除成功！");
            }
        }
        return response.response();
    }
}
