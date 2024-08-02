package com.jerry.up.lala.cloud.server.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.jerry.up.lala.cloud.api.system.client.SysMenuClient;
import com.jerry.up.lala.cloud.server.system.dto.SysLogRequestApiNameDTO;
import com.jerry.up.lala.cloud.server.system.dto.SysLogRequestServletMethodDTO;
import com.jerry.up.lala.cloud.server.system.dto.SysLogRequestStatisticDTO;
import com.jerry.up.lala.cloud.server.system.dto.SysLogRequestSumDTO;
import com.jerry.up.lala.cloud.server.system.properties.ApiProperties;
import com.jerry.up.lala.cloud.server.system.service.SysLogRequestService;
import com.jerry.up.lala.cloud.server.system.service.WorkplaceService;
import com.jerry.up.lala.cloud.server.system.vo.*;
import com.jerry.up.lala.framework.common.exception.Errors;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 工作台实现
 *
 * @author FMJ
 * @date 2024/1/19 13:44
 */
@Service
public class WorkplaceServiceImpl implements WorkplaceService {

    @Autowired
    private SysMenuClient sysMenuClient;

    @Autowired
    private SysLogRequestService sysLogRequestService;

    @Autowired
    private ApiProperties apiProperties;

    @Override
    public WorkplaceStatisticVO statistic() {
        try {
            Long menu = sysMenuClient.count();
            SysLogRequestStatisticDTO statisticDTO = sysLogRequestService.statistic();
            return new WorkplaceStatisticVO().setMenu(menu)
                    .setApiCount(apiProperties.getCount())
                    .setApiDoc(apiProperties.getDoc())
                    .setRequest(statisticDTO.getRequest())
                    .setRequestQoq(statisticDTO.getRequestQoq());
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public AntVDualAxesVO logSum() {
        try {
            List<SysLogRequestSumDTO> sumList = sysLogRequestService.sum();
            List<AntVDualAxesDataVO> column = new ArrayList<>();
            List<AntVDualAxesDataVO> data = new ArrayList<>();
            if (CollUtil.isNotEmpty(sumList)) {
                for (SysLogRequestSumDTO sumDTO : sumList) {
                    String date = sumDTO.getDate();
                    column.add(new AntVDualAxesDataVO().setX(date).setY(sumDTO.getCount()));
                    data.add(new AntVDualAxesDataVO().setX(date).setY(sumDTO.getResponseSuccessCount())
                            .setName("1"));
                    data.add(new AntVDualAxesDataVO().setX(date).setY(sumDTO.getResponseErrorCount())
                            .setName("2"));
                }
            }
            return new AntVDualAxesVO().setColumn(column).setData(data);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<AntVPieVO> logServletMethod() {
        try {
            List<SysLogRequestServletMethodDTO> list = sysLogRequestService.servletMethod();
            return list.stream().map(item -> new AntVPieVO().setType(item.getServletMethod()).setValue(item.getCount())).collect(Collectors.toList());
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<SysLogRequestApiNameVO> logApiName() {
        try {
            List<SysLogRequestApiNameDTO> sysLogRequestApiNameDTO = sysLogRequestService.apiName();
            return BeanUtil.toBeanList(sysLogRequestApiNameDTO, SysLogRequestApiNameVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

}
