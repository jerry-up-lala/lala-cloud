package com.jerry.up.lala.cloud.server.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.cloud.server.core.entity.Notice;
import com.jerry.up.lala.cloud.server.core.vo.*;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.r.PageR;

import java.util.List;

/**
* @author jerry
* @description 针对表【notice(通知表)】的数据库操作Service
* @createDate 2024-01-10 09:51:19
*/
public interface NoticeService extends IService<Notice> {

    PageR<NoticeVO> pageQuery(NoticeQueryVO noticeQueryVO);

    NoticeInfoVO info(Long id);

    PageR<NoticeUserAllVO> userPage(NoticeUserAllQueryVO noticeUserAllQueryVO);

    void add(NoticeSaveVO noticeSaveVO);

    void edit(Long id, NoticeSaveVO noticeSaveVO);

    void send(Long id);

    void delete(Long id);

    void batchDelete(DataBody<List<Long>> dataBody);

}
