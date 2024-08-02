package com.jerry.up.lala.cloud.server.core.service.impl;

import com.jerry.up.lala.cloud.server.core.dto.NoticeUserQueryDTO;
import com.jerry.up.lala.cloud.server.core.service.NoticeUserService;
import com.jerry.up.lala.cloud.server.core.service.WorkplaceService;
import com.jerry.up.lala.cloud.server.core.vo.NoticeUserVO;
import com.jerry.up.lala.framework.boot.satoken.SaTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 工作台实现
 *
 * @author FMJ
 * @date 2024/1/19 13:44
 */
@Service
public class WorkplaceServiceImpl implements WorkplaceService {

    @Autowired
    private NoticeUserService noticeUserService;


    @Override
    public List<NoticeUserVO> notice() {
        NoticeUserQueryDTO queryDTO = new NoticeUserQueryDTO().setUserId(SaTokenUtil.currentUser().getUserId()).setLimit(5L);
        return noticeUserService.listQuery(queryDTO);
    }
}
