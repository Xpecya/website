package com.hanyin.website.service;

import com.hanyin.website.bean.NewsName;
import com.hanyin.website.entity.News;
import com.hanyin.website.repository.NewsNameRepository;
import com.hanyin.website.repository.NewsRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 新闻服务
 */
@Service
public class NewsService {

    private NewsNameRepository newsNameRepository;
    private NewsRepository newsRepository;

    public NewsService(NewsNameRepository newsNameRepository,
                       NewsRepository newsRepository) {
        this.newsNameRepository = newsNameRepository;
        this.newsRepository = newsRepository;
    }

    /**
     * 获取最新热点新闻标题名
     * @return 所有新闻的名称的id
     */
    public String showNews(Integer page, Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
        Response<List<NewsName>> response = new Response<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        Page<NewsName> newsNamePage = newsNameRepository.findAll(PageRequest.of(page, size, sort));
        List<NewsName> newsNames = newsNamePage.getContent();
        response.setData(newsNames);
        response.setStatus(newsNames.size() > 0);
        if (!response.isStatus()) response.setErrorMessage("没找到新闻！");
        return response.response();
    }

    /**
     * 获取一条新闻的详细内容
     * @return 新闻内容
     */
    public String showNews(Integer id) {
        Response<Map<String, Object>> response = new Response<>();
        Response<News> newsResponse = getNews(id);
        if (!newsResponse.isStatus()) return newsResponse.response();

        News news = newsResponse.getData();
        Map<String, Object> newsMap = news.toJsonMap();
        response.setData(newsMap);
        response.setStatus(true);

        // 查看次数+1
        Integer currentTime = news.getCheckTimes();
        currentTime += 1;
        news.setCheckTimes(currentTime);
        newsRepository.saveAndFlush(news);
        return response.response();
    }

    /**
     * 获取总分页页数
     * @return 所有分页总数
     */
    public String pageNumber(Integer size) {
        size = size == null ? 5 : size;
        int pages = newsRepository.findAll(PageRequest.of(0, size)).getTotalPages();
        Response<Integer> response = new Response<>();
        response.setData(pages);
        response.setStatus(true);
        return response.response();
    }

    /**
     * 新增一条新闻
     * @param name 标题
     * @param content 内容
     * @param source 来源
     * @return 新建结果
     */
    public String createNews(String name, String content, String source) {
        News news = new News();
        if (name != null) news.setName(name);
        if (content != null) news.setContent(content);
        if (source != null) news.setSource(source);
        news.setPublishDate(new Date(System.currentTimeMillis()));
        news.setCheckTimes(0);
        newsRepository.save(news);
        Response<String> response = new Response<>();
        response.setData("新建成功！");
        response.setStatus(true);
        return response.response();
    }

    /**
     * 修改新闻
     * @param id 新闻id
     * @param name 新闻标题
     * @param content 新闻内容
     * @param source 新闻来源
     * @return 修改结果
     */
    public String updateNews(Integer id, String name, String content, String source) {
        Response<News> newsResponse = getNews(id);
        if (!newsResponse.isStatus()) return newsResponse.response();

        News news = newsResponse.getData();
        if (StringUtils.hasLength(name)) news.setName(name);
        if (StringUtils.hasLength(content)) news.setContent(content);
        if (StringUtils.hasLength(source)) news.setSource(source);

        newsRepository.saveAndFlush(news);
        Response<String> response = new Response<>();
        response.setData("修改成功！");
        response.setStatus(true);
        return response.response();
    }

    /**
     * 删除一条新闻
     * @param id 新闻id
     * @return 删除结果
     */
    public String deleteNews(Integer id) {
        Response<News> newsResponse = new Response<>();
        if (!newsResponse.isStatus()) return newsResponse.response();

        News news = newsResponse.getData();
        newsRepository.delete(news);

        Response<String> response = new Response<>();
        response.setData("删除成功！");
        response.setStatus(true);
        return response.response();
    }

    private Response<News> getNews(Integer id) {
        Response<News> response = new Response<>();
        if (id == null) response.setErrorMessage("id is null!");
        else {
            Optional<News> optional = newsRepository.findById(id);
            if (optional.isEmpty()) response.setErrorMessage("未找到指定新闻！");
            else {
                News news = optional.get();
                response.setData(news);
            }
        }
        return response;
    }
}
