package com.hanyin.website.controller;

import com.hanyin.website.bean.param.SimpleDelete;
import com.hanyin.website.bean.param.teammate.CreateSolutionParam;
import com.hanyin.website.bean.param.teammate.CreateTeammateParam;
import com.hanyin.website.bean.param.teammate.UpdateSolutionParam;
import com.hanyin.website.service.FunctionService;
import com.hanyin.website.service.TeammateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class TeammateController {

    private FunctionService functionService;
    private TeammateService teammateService;

    public TeammateController(FunctionService functionService, TeammateService teammateService) {
        this.functionService = functionService;
        this.teammateService = teammateService;
    }

    @GetMapping("/teammates")
    public String showTeammates(Integer page, Integer size) {
        return teammateService.showTeammates(page, size);
    }

    @GetMapping("/teammate")
    public String showTeammate(Integer id) {
        return teammateService.showTeammate(id);
    }

    @GetMapping("/teammates/pages")
    public String pageNumber(Integer size) {
        return teammateService.pageNumber(size);
    }

    @PutMapping("/teammate")
    public String createTeammate(String sessionId, String name, String introduction, MultipartFile image,
                                 Integer position, String major) {
        return functionService.generalImageUpload(sessionId, image,
                imageUrl -> teammateService.createTeammate(name, introduction, imageUrl, position, major));
    }

    @DeleteMapping("/teammate")
    public String deleteTeammate(@RequestBody SimpleDelete delete) {
        String sessionId = delete.getSessionId();
        Integer id = delete.getId();
        return functionService.sessionCheck(sessionId, () -> teammateService.deleteTeammate(id));
    }

    @PostMapping("/teammate")
    public String updateTeammate(String sessionId, Integer id, String name, String introduction,
                                 MultipartFile image, Integer position, String major) {
        return functionService.generalImageUpload(sessionId, image,
                imageUrl -> teammateService.updateTeammate(id, name, introduction, imageUrl, position, major));
    }

    @PutMapping("/solution")
    public String createSolution(@RequestBody String sessionId, @RequestBody Integer id, String content) {
        return functionService.sessionCheck(sessionId, () -> teammateService.createSolution(id, content));
    }

    @PutMapping("/solutions")
    public String createSolutions(@RequestBody CreateSolutionParam param) {
        String sessionId = param.getSessionId();
        Integer id = param.getId();
        List<String> contents = param.getContents();
        return functionService.sessionCheck(sessionId, () -> teammateService.createSolutions(id, contents));
    }

    @PostMapping("/solution")
    public String updateSolution(@RequestBody UpdateSolutionParam param) {
        String sessionId = param.getSessionId();
        Integer solutionId = param.getSolutionId();
        Integer teammateId = param.getTeammateId();
        String content = param.getContent();
        return functionService.sessionCheck(sessionId,
                () -> teammateService.updateSolution(solutionId, teammateId, content));
    }

    @DeleteMapping("/solution")
    public String deleteSolution(@RequestBody SimpleDelete delete) {
        String sessionId = delete.getSessionId();
        Integer id = delete.getId();
        return functionService.sessionCheck(sessionId, () -> teammateService.deleteSolution(id));
    }
}
