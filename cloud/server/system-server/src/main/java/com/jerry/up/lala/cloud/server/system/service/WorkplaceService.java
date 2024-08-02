package com.jerry.up.lala.cloud.server.system.service;

import com.jerry.up.lala.cloud.server.system.vo.AntVDualAxesVO;
import com.jerry.up.lala.cloud.server.system.vo.AntVPieVO;
import com.jerry.up.lala.cloud.server.system.vo.SysLogRequestApiNameVO;
import com.jerry.up.lala.cloud.server.system.vo.WorkplaceStatisticVO;

import java.util.List;

/**
 * <p>Description: 工作台
 *
 * @author FMJ
 * @date 2024/1/19 13:39
 */
public interface WorkplaceService {
    WorkplaceStatisticVO statistic();

    AntVDualAxesVO logSum();

    List<AntVPieVO> logServletMethod();

    List<SysLogRequestApiNameVO> logApiName();

}
