package com.hanyin.website.service;

import com.hanyin.website.bean.DetailName;
import com.hanyin.website.entity.Detail;
import com.hanyin.website.repository.DetailNameRepository;
import com.hanyin.website.repository.DetailRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 服务领域服务
 */
@Service
public class DetailService {

    private DetailNameRepository detailNameRepository;
    private DetailRepository detailRepository;

    public DetailService(DetailNameRepository detailNameRepository, DetailRepository detailRepository) {
        this.detailNameRepository = detailNameRepository;
        this.detailRepository = detailRepository;
    }

    /**
     * 获取所有服务领域名称
     * @return 服务领域名称集合
     */
    public String showDetails() {
        Response<List<DetailName>> response = new Response<>();
        response.setData(detailNameRepository.findAll());
        response.setStatus(true);
        return response.response();
    }

    /**
     * 获取指定Id的服务领域内容
     * @param id 服务领域id
     * @return 服务领域内容
     */
    public String showDetail(Integer id) {
        Response<Detail> response = findDetail(id);
        return response.response();
    }

    /**
     * 增加一条服务领域信息
     * @param name 服务领域名称
     * @param content 服务领域细节
     * @return 增加结果
     */
    public String addDetail(String name, String content) {
        Detail detail = new Detail();
        detail.setName(name);
        detail.setContent(content);
        detailRepository.saveAndFlush(detail);
        Response<String> response = new Response<>();
        response.setData("新建成功!");
        response.setStatus(true);
        return response.response();
    }

    /**
     * 删除一条服务领域信息
     * @param id 指定服务领域id
     * @return
     */
    public String deleteDetail(Integer id) {
        Response<Detail> detailResponse = findDetail(id);
        if (!detailResponse.isStatus()) return detailResponse.response();
        Detail detail = detailResponse.getData();
        detailRepository.delete(detail);
        Response<String> response = new Response<>();
        response.setStatus(true);
        return response.response();
    }

    /**
     * 修改一条服务领域信息
     * @param id 服务领域id
     * @param newName 修改后的服务领域名称
     * @param newContent 修改后的服务领域内容
     * @return 修改结果
     */
    public String updateDetail(Integer id, String newName, String newContent) {
        Response<Detail> detailResponse = findDetail(id);
        if (!detailResponse.isStatus()) return detailResponse.response();

        Detail detail = detailResponse.getData();
        if (StringUtils.hasLength(newName)) detail.setName(newName);
        if (StringUtils.hasLength(newContent)) detail.setContent(newContent);
        detailRepository.saveAndFlush(detail);

        Response<String> response = new Response<>();
        response.setData("更新成功！");
        response.setStatus(true);
        return response.response();
    }

    private Response<Detail> findDetail(Integer id) {
        Response<Detail> response = new Response<>();
        if (id == null) response.setErrorMessage("id is null!");
        else {
            Optional<Detail> optional = detailRepository.findById(id);
            if (optional.isEmpty()) response.setErrorMessage("没找到指定服务领域！");
            else {
                Detail detail = optional.get();
                response.setData(detail);
                response.setStatus(true);
            }
        }
        return response;
    }
}
