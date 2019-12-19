package com.hanyin.website.service;

import com.hanyin.website.bean.ColumnParam;
import com.hanyin.website.entity.Introduction;
import com.hanyin.website.entity.IntroductionDetail;
import com.hanyin.website.entity.IntroductionDetailColumn;
import com.hanyin.website.repository.IntroductionDetailColumnRepository;
import com.hanyin.website.repository.IntroductionDetailRepository;
import com.hanyin.website.repository.IntroductionRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class IntroductionService {

    // 数据库中简介有且只有一条数据
    // 数据id
    private static final Integer ID = 1;

    private IntroductionRepository introductionRepository;
    private IntroductionDetailRepository introductionDetailRepository;
    private IntroductionDetailColumnRepository introductionDetailColumnRepository;

    public IntroductionService(IntroductionRepository introductionRepository,
                               IntroductionDetailRepository introductionDetailRepository,
                               IntroductionDetailColumnRepository introductionDetailColumnRepository) {
        this.introductionRepository = introductionRepository;
        this.introductionDetailRepository = introductionDetailRepository;
        this.introductionDetailColumnRepository = introductionDetailColumnRepository;
    }

    /**
     * 修改事务所简介
     * @param content 内容
     * @param imageUrl 图片地址
     * @return 修改结果
     */
    public String updateIntroduction(String content, String imageUrl) {
        Response<String> response = new Response<>();
        Response<Introduction> introductionResponse = getIntroduction();
        if (!introductionResponse.isStatus()) return introductionResponse.response();

        Introduction introduction = introductionResponse.getData();
        if (StringUtils.hasLength(content)) introduction.setContent(content);
        if (StringUtils.hasLength(imageUrl)) introduction.setContent(imageUrl);
        introductionRepository.saveAndFlush(introduction);
        response.setStatus(true);
        response.setData("修改成功！");
        return response.response();
    }

    /**
     * 查找事务所简介
     * @return 简介
     */
    public String showIntroduction() {
        Response<Introduction> response = getIntroduction();
        return response.response();
    }

    /**
     * 展示所有详情名称
     * @return 所有详情名称
     */
    public String showIntroductionDetailNames() {
        Response<List<IntroductionDetail>> response = new Response<>();
        List<IntroductionDetail> details = introductionDetailRepository.findAll();

        boolean status = details.size() > 0;
        if (status) {
            response.setData(details);
            response.setStatus(true);
        } else response.setErrorMessage("没找到详情介绍！");
        return response.response();
    }

    /**
     * 展示一条详情
     * @return 详情信息
     */
    public String showIntroductionDetail(Integer detailId) {
        return getIntroductionDetailColumns(detailId).response();
    }

    /**
     * 新建一条详情
     * @param name 详情名称
     * @return 新建结果
     */
    public String createIntroductionDetail(String name, List<ColumnParam> params) {
        Response<String> response = new Response<>();
        if (StringUtils.isEmpty(name)) response.setErrorMessage("name is empty!");
        else {
            IntroductionDetail detail = new IntroductionDetail();
            detail.setName(name);
            detail = introductionDetailRepository.save(detail);
            Integer detailId = detail.getId();

            List<IntroductionDetailColumn> columns = new LinkedList<>();
            Iterator<ColumnParam> paramIterator = params.iterator();
            while (paramIterator.hasNext()) {
                ColumnParam next = paramIterator.next();
                IntroductionDetailColumn column = new IntroductionDetailColumn();
                column.setIntroductionDetailId(detailId);
                column.setTitle(next.getName());
                column.setContent(next.getContent());
                columns.add(column);
            }
            if (columns.size() > 0) introductionDetailColumnRepository.saveAll(columns);
            response.setStatus(true);
            response.setData("新建成功！");
        }
        return response.response();
    }

    /**
     * 新建一条详情的内容
     * @return
     */
    public String createIntroductionDetailColumn(Integer id, String title, String content) {
        Response<IntroductionDetail> detailResponse = getIntroductionDetail(id);
        if (!detailResponse.isStatus()) return detailResponse.response();

        IntroductionDetailColumn column = new IntroductionDetailColumn();
        column.setIntroductionDetailId(id);
        if (StringUtils.hasLength(title)) column.setTitle(title);
        if (StringUtils.hasLength(content)) column.setContent(content);
        introductionDetailColumnRepository.save(column);
        Response<String> response = new Response<>();
        response.setStatus(true);
        response.setData("新建成功！");
        return response.response();
    }

    /**
     * 修改一条详情
     * @param id 详情id
     * @param name 名称
     * @return 修改结果
     */
    public String updateIntroductionDetail(Integer id, String name) {
        Response<IntroductionDetail> detailResponse = getIntroductionDetail(id);
        if(!detailResponse.isStatus()) return detailResponse.response();

        IntroductionDetail detail = detailResponse.getData();
        if (StringUtils.hasLength(name)) detail.setName(name);
        introductionDetailRepository.saveAndFlush(detail);
        Response<String> response = new Response<>();
        response.setData("修改成功！");
        response.setStatus(true);
        return response.response();
    }

    /**
     * 修改一条详情内容
     * @param id 内容id
     * @param detailId 详情Id
     * @param title 标题
     * @param content 内容
     * @return 修改结果
     */
    public String updateIntroductionDetailColumn(Integer id, Integer detailId, String title, String content) {
        Response<IntroductionDetailColumn> columnResponse = getIntroductionDetailColumn(id);
        if (!columnResponse.isStatus()) return columnResponse.response();

        IntroductionDetailColumn column = columnResponse.getData();
        if (detailId != null) {
            Response<IntroductionDetail> detailResponse = getIntroductionDetail(detailId);
            if (!detailResponse.isStatus()) return detailResponse.response();
            column.setIntroductionDetailId(detailId);
        }
        if (StringUtils.hasLength(title)) column.setTitle(title);
        if (StringUtils.hasLength(content)) column.setContent(content);
        introductionDetailColumnRepository.saveAndFlush(column);
        Response<String> response = new Response<>();
        response.setData("修改成功！");
        response.setStatus(true);
        return response.response();
    }

    /**
     * 删除一条详情
     * 如果该条详情有内容，将所有内容一并删除
     * @param id 详情id
     * @return 删除结果
     */
    public String deleteIntroductionDetail(Integer id) {
        Response<IntroductionDetail> detailResponse = getIntroductionDetail(id);
        if (!detailResponse.isStatus()) return detailResponse.response();

        Response<List<IntroductionDetailColumn>> columnsResponse = getIntroductionDetailColumns(id);
        if (columnsResponse.isStatus()) introductionDetailColumnRepository.deleteAll(columnsResponse.getData());
        introductionDetailRepository.deleteById(id);

        Response<String> response = new Response<>();
        response.setStatus(true);
        response.setData("删除成功！");
        return response.response();
    }

    /**
     * 删除一条详情内容
     * @param id 详情内容id
     * @return 删除结果
     */
    public String deleteIntroductionDetailColumn(Integer id) {
        Response<IntroductionDetailColumn> columnResponse = getIntroductionDetailColumn(id);
        if (!columnResponse.isStatus()) return columnResponse.response();

        introductionDetailColumnRepository.deleteById(id);
        Response<String> response = new Response<>();
        response.setStatus(true);
        response.setData("删除成功！");
        return response.response();
    }

    private Response<List<IntroductionDetailColumn>> getIntroductionDetailColumns(Integer id) {
        Response<List<IntroductionDetailColumn>> response = new Response<>();
        if (id == null) response.setErrorMessage("id is null!");
        else {
            List<IntroductionDetailColumn> columns = introductionDetailColumnRepository.findAllByIntroductionDetailId(id);
            boolean status = columns.size() > 0;
            if (status) {
                response.setData(columns);
                response.setStatus(true);
            } else response.setErrorMessage("没找到详情内容！");
        }
        return response;
    }

    private Response<IntroductionDetailColumn> getIntroductionDetailColumn(Integer id) {
        Response<IntroductionDetailColumn> response = new Response<>();
        if (id == null) response.setErrorMessage("id is null!");
        else {
            Optional<IntroductionDetailColumn> columnOptional = introductionDetailColumnRepository.findById(id);
            if (columnOptional.isEmpty()) response.setErrorMessage("column not found!");
            else {
                response.setStatus(true);
                response.setData(columnOptional.get());
            }
        }
        return response;
    }

    private Response<IntroductionDetail> getIntroductionDetail(Integer id) {
        Response<IntroductionDetail> response = new Response<>();
        if (id == null) response.setErrorMessage("id is null!");
        else {
            Optional<IntroductionDetail> detailOptional = introductionDetailRepository.findById(id);
            if (detailOptional.isEmpty()) response.setErrorMessage("detail not found!");
            else {
                response.setStatus(true);
                response.setData(detailOptional.get());
            }
        }
        return response;
    }

    private Response<Introduction> getIntroduction() {
        Response<Introduction> response = new Response<>();
        Optional<Introduction> optional = introductionRepository.findById(ID);
        if (optional.isEmpty()) response.setErrorMessage("未找到系统简介，请咨询数据库管理员！");
        else {
            response.setStatus(true);
            response.setData(optional.get());
        }
        return response;
    }
}
