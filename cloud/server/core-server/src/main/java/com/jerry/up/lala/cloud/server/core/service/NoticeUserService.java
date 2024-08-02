package com.jerry.up.lala.cloud.server.core.service;

import com.github.yulichang.base.MPJBaseService;
import com.jerry.up.lala.cloud.api.system.bo.UserBO;
import com.jerry.up.lala.cloud.server.core.dto.NoticeUserQueryDTO;
import com.jerry.up.lala.cloud.server.core.entity.NoticeUser;
import com.jerry.up.lala.cloud.server.core.vo.*;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.r.PageR;

import java.util.List;
import java.util.Map;

/**
* @author jerry
* @description 针对表【notice_user(通知记录表)】的数据库操作Service
* @createDate 2024-01-10 09:51:24
*/
public interface NoticeUserService extends MPJBaseService<NoticeUser> {

    PageR<NoticeUserAllVO> noticeQuery(NoticeUserAllQueryVO noticeUserAllQueryVO);

    PageR<NoticeUserVO> pageQuery(NoticeUserQueryVO noticeUserQueryVO);

    List<NoticeUserVO> listQuery(NoticeUserQueryDTO noticeUserQueryDTO);

    Map<Integer, Long> unreadCount();

    NoticeInfoUserVO info(Long id);

    void read(DataBody<List<Long>> dataBody);

    void readAll();

    void save(Long noticeId, List<UserBO> userList);

    void send(Long noticeId);

    void delete(Long noticeId);

    void delete(List<Long> noticeIds);

    void userDelete(Long id);

    void userBatchDelete(DataBody<List<Long>> dataBody);

}
