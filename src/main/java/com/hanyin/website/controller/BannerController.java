package com.hanyin.website.controller;

import com.hanyin.website.bean.param.SimpleDelete;
import com.hanyin.website.bean.param.banner.UpdateConfigParam;
import com.hanyin.website.service.BannerService;
import com.hanyin.website.service.FunctionService;
import com.hanyin.website.service.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/banner")
public class BannerController {

    private FunctionService functionService;
    private BannerService bannerService;

    public BannerController(FunctionService functionService, BannerService bannerService) {
        this.functionService = functionService;
        this.bannerService = bannerService;
    }

    @GetMapping("/config")
    public String getConfig() {
        return bannerService.getConfig();
    }

    @GetMapping("/images")
    public String getBannerImages() {
        return bannerService.getBannerImages();
    }

    @PostMapping("/config")
    public String updateConfig(@RequestBody UpdateConfigParam param) {
        String sessionId = param.getSessionId();
        Integer speed = param.getSpeed();
        return functionService.sessionCheck(sessionId, () -> bannerService.updateConfig(speed));
    }

    @PutMapping("/image")
    public String createBannerImage(String sessionId, MultipartFile image) {
        return functionService.generalImageUpload(sessionId, image, bannerService::createBannerImage);
    }

    @DeleteMapping("/image")
    public String deleteBannerImage(@RequestBody SimpleDelete delete) {
        String sessionId = delete.getSessionId();
        Integer id = delete.getId();
        return functionService.sessionCheck(sessionId, () -> bannerService.deleteBannerImage(id));
    }
}
