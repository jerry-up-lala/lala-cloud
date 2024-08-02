package com.jerry.up.lala.cloud.server.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jerry.up.lala.cloud.api.system.bo.SysTenantBO;
import com.jerry.up.lala.cloud.api.system.client.SysTenantClient;
import com.jerry.up.lala.cloud.server.system.entity.SysLogRequest;
import com.jerry.up.lala.cloud.server.system.mapper.SysLogRequestMapper;
import com.jerry.up.lala.cloud.server.system.service.SysLogRequestService;
import com.jerry.up.lala.cloud.server.system.vo.SysLogRequestQueryVO;
import com.jerry.up.lala.cloud.server.system.vo.SysLogRequestVO;
import com.jerry.up.lala.cloud.server.system.dto.*;
import com.jerry.up.lala.framework.boot.api.ApiLog;
import com.jerry.up.lala.framework.boot.page.PageUtil;
import com.jerry.up.lala.framework.common.exception.Errors;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.model.PageQuery;
import com.jerry.up.lala.framework.common.r.PageR;
import com.jerry.up.lala.framework.common.util.BeanUtil;
import com.jerry.up.lala.framework.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【sys_log_request(请求日志表)】的数据库操作Service实现
 * @createDate 2023-12-22 11:25:01
 */
@Service
public class SysLogRequestServiceImpl extends MPJBaseServiceImpl<SysLogRequestMapper, SysLogRequest> implements SysLogRequestService {

    @Autowired
    private SysTenantClient sysTenantClient;

    @Override
    public void save(ApiLog apiLog) {
        if (apiLog != null) {
            SysLogRequest sysLogRequest = BeanUtil.toBean(apiLog, SysLogRequest.class);
            sysLogRequest.setCreateTime(new Date());
            save(sysLogRequest);
        }
    }

    @Override
    public PageR<SysLogRequestVO> pageQuery(SysLogRequestQueryVO sysLogRequestQueryVO) {
        SysLogRequestQueryDTO queryDTO = BeanUtil.toBean(sysLogRequestQueryVO, SysLogRequestQueryDTO.class);
        IPage<SysLogRequestDTO> pageResult = pageByDTO(sysLogRequestQueryVO, queryDTO);
        try {
            return PageUtil.toPageR(pageResult, SysLogRequestVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public SysLogRequestStatisticDTO statistic() {
        SysLogRequestStatisticDTO statisticDTO = getBaseMapper()
                .statistic(DateUtil.formatDate(DateUtil.yesterday()), DateUtil.today());
        Long yesterday = statisticDTO.getYesterday();
        Long today = statisticDTO.getToday();
        if (yesterday > 0) {
            BigDecimal requestQoq = NumberUtil.div(NumberUtil.mul(NumberUtil.sub(today, yesterday), 100), yesterday, 2);
            statisticDTO.setRequestQoq(requestQoq);
        }
        return statisticDTO;
    }

    @Override
    public List<SysLogRequestSumDTO> sum() {
        Date now = DateUtil.date();
        List<String> requestDateList = DateUtil.rangeToList(DateUtil.offsetDay(now, -10), now, DateField.DAY_OF_YEAR)
                .stream().map(DateUtil::formatDate).collect(Collectors.toList());
        return getBaseMapper().sum(requestDateList);
    }

    @Override
    public List<SysLogRequestServletMethodDTO> servletMethod() {
        return getBaseMapper().servletMethod();
    }

    @Override
    public List<SysLogRequestApiNameDTO> apiName() {
        return getBaseMapper().apiName();
    }

    private IPage<SysLogRequestDTO> pageByDTO(PageQuery pageQuery, SysLogRequestQueryDTO queryDTO) {
        Page<SysLogRequestDTO> page = PageUtil.initPage(pageQuery);
        try {
            MPJLambdaWrapper<SysLogRequest> query = loadQuery(queryDTO);
            IPage<SysLogRequestDTO> result = selectJoinListPage(page, SysLogRequestDTO.class, query);
            loadTenantInfo(result.getRecords());
            return result;
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private void loadTenantInfo(List<SysLogRequestDTO> sysLogRequestList) {
        List<String> tenantIds = sysLogRequestList.stream().map(SysLogRequestDTO::getTenantId).distinct().collect(Collectors.toList());
        List<SysTenantBO> sysTenantBOList = sysTenantClient.list(new DataBody<>(tenantIds));
        if (CollUtil.isEmpty(sysTenantBOList)) {
            return;
        }
        Map<String, SysTenantBO> tenantMap = sysTenantBOList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(SysTenantBO::getId, Function.identity(), (key1, key2) -> key1));
        sysLogRequestList.forEach(sysLogRequest -> {
            SysTenantBO sysTenantBO = tenantMap.get(sysLogRequest.getTenantId());
            if (sysTenantBO != null) {
                sysLogRequest.setTenantName(sysTenantBO.getTenantName());
            }
        });

    }

    private MPJLambdaWrapper<SysLogRequest> loadQuery(SysLogRequestQueryDTO queryDTO) {
        MPJLambdaWrapper<SysLogRequest> query = loadQuery();
        String apiName = queryDTO.getApiName();
        if (StringUtil.isNotNull(apiName)) {
            query.like(SysLogRequest::getApiName, apiName);
        }

        Boolean responseSuccess = queryDTO.getResponseSuccess();
        if (responseSuccess != null) {
            query.eq(SysLogRequest::getResponseSuccess, responseSuccess);
        }

        String responseErrorCode = queryDTO.getResponseErrorCode();
        if (StringUtil.isNotNull(responseErrorCode)) {
            query.like(SysLogRequest::getResponseErrorCode, responseErrorCode);
        }

        String responseErrorMsg = queryDTO.getResponseErrorMsg();
        if (StringUtil.isNotNull(responseErrorMsg)) {
            query.like(SysLogRequest::getResponseErrorMsg, responseErrorMsg);
        }

        String clientIp = queryDTO.getClientIp();
        if (StringUtil.isNotNull(clientIp)) {
            query.like(SysLogRequest::getClientIp, clientIp);
        }

        List<String> tenantIds = queryDTO.getTenantIds();
        if (CollUtil.isNotEmpty(tenantIds)) {
            query.in(SysLogRequest::getTenantId, tenantIds);
        }

        String loginName = queryDTO.getLoginName();
        if (StringUtil.isNotNull(loginName)) {
            query.like(SysLogRequest::getLoginName, loginName);
        }

        String userId = queryDTO.getUserId();
        if (StringUtil.isNotNull(userId)) {
            query.like(SysLogRequest::getUserId, userId);
        }

        List<String> servletMethods = queryDTO.getServletMethods();
        if (CollUtil.isNotEmpty(servletMethods)) {
            query.in(SysLogRequest::getServletMethod, servletMethods);
        }

        String servletPath = queryDTO.getServletPath();
        if (StringUtil.isNotNull(servletPath)) {
            query.like(SysLogRequest::getServletPath, servletPath);
        }

        Date requestTimeStart = queryDTO.getRequestTimeStart();
        if (requestTimeStart != null) {
            query.ge(SysLogRequest::getRequestTime, requestTimeStart);
        }

        Date requestTimeEnd = queryDTO.getRequestTimeEnd();
        if (requestTimeEnd != null) {
            query.le(SysLogRequest::getRequestTime, requestTimeEnd);
        }

        Long responseTimeStart = queryDTO.getResponseTimeStart();
        if (responseTimeStart != null) {
            query.ge(SysLogRequest::getResponseTime, responseTimeStart);
        }

        Long responseTimeEnd = queryDTO.getResponseTimeEnd();
        if (responseTimeEnd != null) {
            query.le(SysLogRequest::getResponseTime, responseTimeEnd);
        }

        String classMethod = queryDTO.getClassMethod();
        if (StringUtil.isNotNull(classMethod)) {
            query.like(SysLogRequest::getClassMethod, classMethod);
        }

        String classParams = queryDTO.getClassParams();
        if (StringUtil.isNotNull(classParams)) {
            query.like(SysLogRequest::getClassParams, classParams);
        }

        String userAgent = queryDTO.getUserAgent();
        if (StringUtil.isNotNull(userAgent)) {
            query.like(SysLogRequest::getUserAgent, userAgent);
        }

        String requestParams = queryDTO.getRequestParams();
        if (StringUtil.isNotNull(requestParams)) {
            query.like(SysLogRequest::getRequestParams, requestParams);
        }

        String requestBody = queryDTO.getRequestBody();
        if (StringUtil.isNotNull(requestBody)) {
            query.like(SysLogRequest::getRequestBody, requestBody);
        }

        String requestUrlInfo = queryDTO.getRequestUrlInfo();
        if (StringUtil.isNotNull(requestUrlInfo)) {
            query.like(SysLogRequest::getRequestUrlInfo, requestUrlInfo);
        }

        String clientInfo = queryDTO.getClientInfo();
        if (StringUtil.isNotNull(clientInfo)) {
            query.like(SysLogRequest::getClientInfo, clientInfo);
        }

        String serverInfo = queryDTO.getServerInfo();
        if (StringUtil.isNotNull(serverInfo)) {
            query.like(SysLogRequest::getServerInfo, serverInfo);
        }

        return query;
    }

    private MPJLambdaWrapper<SysLogRequest> loadQuery() {
        return JoinWrappers.lambda(SysLogRequest.class)
                .selectAll(SysLogRequest.class).orderByDesc(SysLogRequest::getRequestTime);
    }
}




