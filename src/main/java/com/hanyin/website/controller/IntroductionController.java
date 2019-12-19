package com.hanyin.website.controller;

import com.hanyin.website.bean.ColumnParam;
import com.hanyin.website.bean.CreateIntroductionDetailParam;
import com.hanyin.website.bean.param.SimpleDelete;
import com.hanyin.website.bean.param.introduction.UpdateIntroductionDetailColumnParam;
import com.hanyin.website.bean.param.introduction.UpdateIntroductionDetailParam;
import com.hanyin.website.bean.param.introduction.createIntroductionDetailColumnParam;
import com.hanyin.website.service.FunctionService;
import com.hanyin.website.service.IntroductionService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class IntroductionController {

    private IntroductionService introductionService;
    private FunctionService functionService;

    public IntroductionController(IntroductionService introductionService,
                                  FunctionService functionService) {
        this.introductionService = introductionService;
        this.functionService = functionService;
    }

    @GetMapping("/introduction")
    public String showIntroduction() {
        return introductionService.showIntroduction();
    }

    @PostMapping("/introduction")
    public String updateIntroduction(String sessionId, String content, MultipartFile image) {
        return functionService.generalImageUpload(sessionId, image,
                imageUrl -> introductionService.updateIntroduction(content, imageUrl));
    }

    @GetMapping("/introduction/detail/names")
    public String showIntroductionDetailNames() {
        return introductionService.showIntroductionDetailNames();
    }

    @GetMapping("/introduction/detail")
    public String showIntroductionDetail(Integer id) {
        return introductionService.showIntroductionDetail(id);
    }

    @PutMapping("/introduction/detail")
    public String createIntroductionDetail(@RequestBody CreateIntroductionDetailParam param) {
        String sessionId = param.getSessionId();
        String name = param.getName();
        List<ColumnParam> params = param.getParams();
        return functionService.sessionCheck(sessionId, () -> introductionService.createIntroductionDetail(name, params));
    }

    @PutMapping("/introduction/detail/column")
    public String createIntroductionDetailColumn(@RequestBody createIntroductionDetailColumnParam param) {
        String sessionId = param.getSessionId();
        Integer id = param.getId();
        String title = param.getTitle();
        String content = param.getContent();
        return functionService.sessionCheck(sessionId,
                () -> introductionService.createIntroductionDetailColumn(id, title, content));
    }

    @PostMapping("/introduction/detail")
    public String updateIntroductionDetail(@RequestBody UpdateIntroductionDetailParam param) {
        String sessionId = param.getSessionId();
        Integer id = param.getId();
        String name = param.getName();
        return functionService.sessionCheck(sessionId, () -> introductionService.updateIntroductionDetail(id, name));
    }

    @PostMapping("/introduction/detail/column")
    public String updateIntroductionDetailColumn(@RequestBody UpdateIntroductionDetailColumnParam param) {
        String sessionId = param.getSessionId();
        Integer id = param.getId();
        Integer detailId = param.getDetailId();
        String title = param.getTitle();
        String content = param.getContent();
        return functionService.sessionCheck(sessionId,
                () -> introductionService.updateIntroductionDetailColumn(id, detailId, title, content));
    }

    @DeleteMapping("/introduction/detail")
    public String deleteIntroductionDetail(@RequestBody SimpleDelete delete) {
        String sessionId = delete.getSessionId();
        Integer id = delete.getId();
        return functionService.sessionCheck(sessionId, () -> introductionService.deleteIntroductionDetail(id));
    }

    @DeleteMapping("/introduction/detail/column")
    public String deleteIntroductionDetailColumn(@RequestBody SimpleDelete delete) {
        String sessionId = delete.getSessionId();
        Integer id = delete.getId();
        return functionService.sessionCheck(sessionId, () -> introductionService.deleteIntroductionDetailColumn(id));
    }
}
