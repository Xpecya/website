package com.hanyin.website.controller;

import com.hanyin.website.bean.param.SimpleDelete;
import com.hanyin.website.bean.param.detail.AddDetailParam;
import com.hanyin.website.bean.param.detail.DeleteDetailParam;
import com.hanyin.website.bean.param.detail.UpdateDetailParam;
import com.hanyin.website.service.DetailService;
import com.hanyin.website.service.FunctionService;

import org.springframework.web.bind.annotation.*;

@RestController
public class DetailController {

    private DetailService detailService;
    private FunctionService functionService;

    public DetailController(DetailService detailService, FunctionService functionService) {
        this.detailService = detailService;
        this.functionService = functionService;
    }

    @GetMapping("/details")
    public String showDetails() {
        return detailService.showDetails();
    }

    @GetMapping("/detail")
    public String showDetail(Integer id) {
        return detailService.showDetail(id);
    }

    @PutMapping("/detail")
    public String addDetail(@RequestBody AddDetailParam param) {
        String sessionId = param.getSessionId();
        String name = param.getName();
        String content = param.getContent();
        return functionService.sessionCheck(sessionId, () -> detailService.addDetail(name, content));
    }

    @DeleteMapping("/detail")
    public String deleteDetail(@RequestBody SimpleDelete delete) {
        String sessionId = delete.getSessionId();
        Integer id = delete.getId();
        return functionService.sessionCheck(sessionId, () -> detailService.deleteDetail(id));
    }

    @PostMapping("/detail")
    public String updateDetail(@RequestBody UpdateDetailParam param) {
        String sessionId = param.getSessionId();
        Integer id = param.getId();
        String name = param.getName();
        String content = param.getContent();
        return functionService.sessionCheck(sessionId, () -> detailService.updateDetail(id, name, content));
    }
}
