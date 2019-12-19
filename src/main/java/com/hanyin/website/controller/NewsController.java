package com.hanyin.website.controller;

import com.hanyin.website.bean.param.SimpleDelete;
import com.hanyin.website.bean.param.news.CreateNewsParam;
import com.hanyin.website.bean.param.news.UpdateNewsParam;
import com.hanyin.website.service.FunctionService;
import com.hanyin.website.service.NewsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewsController {

    private NewsService newsService;
    private FunctionService functionService;

    public NewsController(NewsService newsService, FunctionService functionService) {
        this.newsService = newsService;
        this.functionService = functionService;
    }

    @GetMapping("/news/titles")
    public String showNews(Integer page, Integer size) {
        return newsService.showNews(page, size);
    }

    @GetMapping("/news")
    public String showNews(Integer id) {
        return newsService.showNews(id);
    }

    @GetMapping("/news/pages")
    public String pageNumber(Integer size) {
        return newsService.pageNumber(size);
    }

    @PutMapping("/news")
    public String createNews(@RequestBody CreateNewsParam param) {
        String sessionId = param.getSessionId();
        String name = param.getName();
        String content = param.getContent();
        String source = param.getSource();
        return functionService.sessionCheck(sessionId, () -> newsService.createNews(name, content, source));
    }

    @PostMapping("/news")
    public String updateNews(@RequestBody UpdateNewsParam param) {
        String sessionId = param.getSessionId();
        Integer id = param.getId();
        String name = param.getName();
        String content = param.getContent();
        String source = param.getSource();
        return functionService.sessionCheck(sessionId, () -> newsService.updateNews(id, name, content, source));
    }

    @DeleteMapping("/news")
    public String deleteNews(@RequestBody SimpleDelete delete) {
        String sessionId = delete.getSessionId();
        Integer id = delete.getId();
        return functionService.sessionCheck(sessionId, () -> newsService.deleteNews(id));
    }
}
