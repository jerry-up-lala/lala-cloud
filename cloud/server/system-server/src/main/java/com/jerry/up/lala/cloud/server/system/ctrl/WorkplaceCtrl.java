package com.jerry.up.lala.cloud.server.system.ctrl;

import com.jerry.up.lala.cloud.server.system.service.WorkplaceService;
import com.jerry.up.lala.cloud.server.system.vo.AntVDualAxesVO;
import com.jerry.up.lala.cloud.server.system.vo.AntVPieVO;
import com.jerry.up.lala.cloud.server.system.vo.SysLogRequestApiNameVO;
import com.jerry.up.lala.cloud.server.system.vo.WorkplaceStatisticVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Description: 工作台
 *
 * @author FMJ
 * @date 2024/1/19 13:30
 */
@RestController
@RequestMapping("/workplace")
public class WorkplaceCtrl {

    @Autowired
    private WorkplaceService workplaceService;

    @GetMapping("/statistic")
    @Api(value = "工作台-数据统计")
    public R<WorkplaceStatisticVO> statistic() {
        WorkplaceStatisticVO result = workplaceService.statistic();
        return R.succeed(result);
    }

    @GetMapping("/log/sum")
    @Api(value = "工作台-请求日志")
    public R<AntVDualAxesVO> logSum() {
        AntVDualAxesVO result = workplaceService.logSum();
        return R.succeed(result);
    }

    @GetMapping("/log/servlet-method")
    @Api(value = "工作台-请求方法占比")
    public R<List<AntVPieVO>> logServletMethod() {
        List<AntVPieVO> result = workplaceService.logServletMethod();
        return R.succeed(result);
    }

    @GetMapping("/log/api-name")
    @Api(value = "工作台-热门接口")
    public R<List<SysLogRequestApiNameVO>> logApiName() {
        List<SysLogRequestApiNameVO> result = workplaceService.logApiName();
        return R.succeed(result);
    }
}
