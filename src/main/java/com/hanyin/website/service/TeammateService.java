package com.hanyin.website.service;

import com.hanyin.website.bean.TeammateDetail;
import com.hanyin.website.bean.TeammateShow;
import com.hanyin.website.entity.Solution;
import com.hanyin.website.entity.Teammate;
import com.hanyin.website.repository.SolutionRepository;
import com.hanyin.website.repository.TeammateRepository;
import com.hanyin.website.repository.TeammateShowRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 团队成员服务
 */
@Service
public class TeammateService {

    private TeammateShowRepository teammateShowRepository;
    private TeammateRepository teammateRepository;
    private SolutionRepository solutionRepository;

    public TeammateService(TeammateShowRepository teammateShowRepository,
                           TeammateRepository teammateRepository,
                           SolutionRepository solutionRepository) {
        this.teammateShowRepository = teammateShowRepository;
        this.teammateRepository = teammateRepository;
        this.solutionRepository = solutionRepository;
    }

    /**
     * 获取团队成员概要列表
     * @param page 页码
     * @param size 分页条数
     * @return 分页返回内容
     */
    public String showTeammates(Integer page, Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 5 : size;
        Sort sort = Sort.by(Sort.Direction.DESC, "position");
        Response<List<TeammateShow>> response = new Response<>();

        Page<TeammateShow> teammates = teammateShowRepository.findAll(PageRequest.of(page, size, sort));
        teammates.getTotalPages();
        response.setData(teammates.getContent());
        response.setStatus(true);
        return response.response();
    }

    /**
     * 获取一名团队成员的详细信息
     * @param id 团队成员id
     * @return 团队成员详细信息
     */
    public String showTeammate(Integer id) {
        Response<Teammate> teammateResponse = getTeammate(id);
        if (!teammateResponse.isStatus()) return teammateResponse.response();

        Teammate teammate = teammateResponse.getData();
        List<String> solutionContents = new LinkedList<>();
        TeammateDetail teammateDetail = new TeammateDetail(teammate);
        teammateDetail.setSolutions(solutionContents);

        Integer teammateId = teammate.getId();
        Response<List<Solution>> solutionsResponse = getSolutions(teammateId);
        if (solutionsResponse.isStatus()) {
            List<Solution> solutions = solutionsResponse.getData();
            Iterator<Solution> solutionIterator = solutions.iterator();
            while (solutionIterator.hasNext()) {
                Solution next = solutionIterator.next();
                solutionContents.add(next.getContent());
            }
        }

        Response<TeammateDetail> response = new Response<>();
        response.setStatus(true);
        response.setData(teammateDetail);
        return response.response();
    }

    /**
     * 获取总分页页数
     * @param size 每页的宽度
     * @return 所有分页总数
     */
    public String pageNumber(Integer size) {
        size = size == null ? 5 : size;
        int pages = teammateShowRepository.findAll(PageRequest.of(0, size)).getTotalPages();
        Response<Integer> response = new Response<>();
        response.setData(pages);
        response.setStatus(true);
        return response.response();
    }

    /**
     * 新建一名团队成员
     * @param name 团队成员姓名
     * @param introduction 团队成员介绍
     * @param imageUrl 团队成员头像地址
     * @param position 团队成员地位
     * @param major 团队成员专业
     * @return 新建结果
     */
    public String createTeammate(String name, String introduction, String imageUrl, Integer position, String major) {
        Teammate teammate = new Teammate();
        teammate.setName(name);
        teammate.setIntroduction(introduction);
        teammate.setImageUrl(imageUrl);
        teammate.setPosition(position);
        teammate.setMajor(major);
        teammateRepository.save(teammate);
        Response<String> response = new Response<>();
        response.setStatus(true);
        response.setData("新建团队成员成功！");
        return response.response();
    }

    /**
     * 删除一名指定的团队成员
     * 如果成员有工作经验则一并删除
     * @param id 成员id
     * @return 删除结果！
     */
    public String deleteTeammate(Integer id) {
        Response<Teammate> teammateResponse = getTeammate(id);
        if (!teammateResponse.isStatus()) return teammateResponse.response();

        Response<List<Solution>> solutionsResponse = getSolutions(id);
        if (solutionsResponse.isStatus()) solutionRepository.deleteAll(solutionsResponse.getData());
        teammateRepository.deleteById(id);
        Response<String> response = new Response<>();
        response.setData("删除成功！");
        response.setStatus(true);
        return response.response();
    }

    /**
     * 修改一名团队成员信息
     * @param id 成员id
     * @param name 团队成员姓名
     * @param introduction 团队成员介绍
     * @param imageUrl 团队成员头像地址
     * @param position 团队成员地位
     * @param major 团队成员专业
     * @return 修改结果
     */
    public String updateTeammate(Integer id, String name, String introduction,
                                 String imageUrl, Integer position, String major) {
        Response<Teammate> teammateResponse = getTeammate(id);
        if (!teammateResponse.isStatus()) return teammateResponse.response();

        Teammate teammate = teammateResponse.getData();
        if (StringUtils.hasLength(name)) teammate.setName(name);
        if (StringUtils.hasLength(introduction)) teammate.setIntroduction(introduction);
        if (StringUtils.hasLength(imageUrl)) teammate.setIntroduction(imageUrl);
        if (StringUtils.hasLength(major)) teammate.setMajor(major);
        if (position != null) teammate.setPosition(position);
        teammateRepository.saveAndFlush(teammate);

        Response<String> response = new Response<>();
        response.setStatus(true);
        response.setData("修改成功！");
        return response.response();
    }

    /**
     * 为一名团队成员新建一条工作经验
     * @param id 成员id
     * @param content 内容
     * @return 新建结果
     */
    public String createSolution(Integer id, String content) {
        Response<String> response = new Response<>();
        if (content == null) response.setErrorMessage("content is null!");
        else {
            Response<Teammate> teammateResponse = getTeammate(id);
            if (!teammateResponse.isStatus()) return teammateResponse.response();
            Solution solution = new Solution();
            solution.setTeammateId(id);
            solution.setContent(content);
            solutionRepository.save(solution);
            response.setStatus(true);
            response.setData("新建成功！");
        }
        return response.response();
    }

    /**
     * 为一名团队成员新建多条工作经验
     * @param id 成员id
     * @param contents 内容
     * @return 新建结果
     */
    public String createSolutions(Integer id, List<String> contents) {
        Response<String> response = new Response<>();
        if (contents == null || contents.size() == 0) response.setErrorMessage("contents is null!");
        else {
            Response<Teammate> teammateResponse = getTeammate(id);
            if (!teammateResponse.isStatus()) return teammateResponse.response();

            Iterator<String> contentsIterator = contents.iterator();
            while (contentsIterator.hasNext()) {
                String content = contentsIterator.next();
                Solution solution = new Solution();
                solution.setTeammateId(id);
                solution.setContent(content);
                solutionRepository.save(solution);
            }

            response.setStatus(true);
            response.setData("新建成功！");
        }
        return response.response();
    }

    /**
     * 修改一条工作经验
     * @param id 工作经验id
     * @param teammateId 团队成员id
     * @param content 内容
     * @return 修改结果
     */
    public String updateSolution(Integer id, Integer teammateId, String content) {
        Response<Solution> solutionResponse = getSolution(id);
        if (!solutionResponse.isStatus()) return solutionResponse.response();

        Solution solution = solutionResponse.getData();
        if (teammateId != null) solution.setTeammateId(teammateId);
        if (StringUtils.hasLength(content)) solution.setContent(content);
        solutionRepository.saveAndFlush(solution);

        Response<String> response = new Response<>();
        response.setStatus(true);
        response.setData("修改成功！");
        return response.response();
    }

    /**
     * 删除一条工作经验
     * @param id 经验Id
     * @return 删除结果
     */
    public String deleteSolution(Integer id) {
        Response<Solution> solutionResponse = getSolution(id);
        if (!solutionResponse.isStatus()) return solutionResponse.response();

        Solution solution = solutionResponse.getData();
        solutionRepository.delete(solution);

        Response<String> response = new Response<>();
        response.setStatus(true);
        response.setData("删除成功！");
        return response.response();
    }

    private Response<List<Solution>> getSolutions(Integer id) {
        Response<List<Solution>> response = new Response<>();
        if (id == null) response.setErrorMessage("id is null!");
        else {
            List<Solution> solutions = solutionRepository.findAllByTeammateId(id);
            boolean status = solutions.size() > 0;
            if (!status) response.setErrorMessage("未找到工作经验！");
            else {
                response.setStatus(true);
                response.setData(solutions);
            }
        }
        return response;
    }

    private Response<Teammate> getTeammate(Integer id) {
        Response<Teammate> response = new Response<>();
        if (id == null) response.setErrorMessage("id is null!");
        else {
            Optional<Teammate> optional = teammateRepository.findById(id);
            if (optional.isEmpty()) response.setErrorMessage("未找到指定团队成员！");
            else {
                response.setStatus(true);
                Teammate teammate = optional.get();
                response.setData(teammate);
            }
        }
        return response;
    }

    private Response<Solution> getSolution(Integer id) {
        Response<Solution> response = new Response<>();
        if (id == null) response.setErrorMessage("id is null!");
        else {
            Optional<Solution> optional = solutionRepository.findById(id);
            if (optional.isEmpty()) response.setErrorMessage("未找到指定经历！");
            else {
                response.setStatus(true);
                Solution solution = optional.get();
                response.setData(solution);
            }
        }
        return response;
    }
}
