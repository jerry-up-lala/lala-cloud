package com.jerry.up.lala.cloud.server.core.ctrl;

import com.jerry.up.lala.cloud.server.core.service.WorkplaceService;
import com.jerry.up.lala.cloud.server.core.vo.NoticeUserVO;
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

    @GetMapping("/notice")
    @Api(value = "工作台-通知")
    public R<List<NoticeUserVO>> notice() {
        List<NoticeUserVO> noticeList = workplaceService.notice();
        return R.succeed(noticeList);
    }
}
