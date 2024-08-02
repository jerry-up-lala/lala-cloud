package com.jerry.up.lala.cloud.server.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.up.lala.cloud.api.system.bo.UserBO;
import com.jerry.up.lala.cloud.api.system.bo.UserQueryBO;
import com.jerry.up.lala.cloud.api.system.client.UserClient;
import com.jerry.up.lala.cloud.server.core.dto.NoticeQueryDTO;
import com.jerry.up.lala.cloud.server.core.entity.Notice;
import com.jerry.up.lala.cloud.server.core.entity.NoticeUser;
import com.jerry.up.lala.cloud.server.core.enums.NoticeSendState;
import com.jerry.up.lala.cloud.server.core.enums.NoticeType;
import com.jerry.up.lala.cloud.server.core.error.CoreErrors;
import com.jerry.up.lala.cloud.server.core.mapper.NoticeMapper;
import com.jerry.up.lala.cloud.server.core.service.NoticeService;
import com.jerry.up.lala.cloud.server.core.service.NoticeUserService;
import com.jerry.up.lala.cloud.server.core.vo.*;
import com.jerry.up.lala.framework.boot.page.PageUtil;
import com.jerry.up.lala.framework.common.exception.Errors;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.r.PageR;
import com.jerry.up.lala.framework.common.util.BeanUtil;
import com.jerry.up.lala.framework.common.util.CheckUtil;
import com.jerry.up.lala.framework.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【notice(通知表)】的数据库操作Service实现
 * @createDate 2024-01-10 09:51:19
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private NoticeUserService noticeUserService;

    @Override
    public PageR<NoticeVO> pageQuery(NoticeQueryVO noticeQueryVO) {
        Page<Notice> page = PageUtil.initPage(noticeQueryVO);
        try {
            NoticeQueryDTO queryDTO = BeanUtil.toBean(noticeQueryVO, NoticeQueryDTO.class);
            LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
            // 标题
            String title = queryDTO.getTitle();
            if (StringUtil.isNotNull(title)) {
                queryWrapper.like(Notice::getTitle, title);
            }

            // 类型【1-消息，2-公告】
            Integer noticeType = queryDTO.getNoticeType();
            if (noticeType != null) {
                queryWrapper.eq(Notice::getNoticeType, noticeType);
            }

            // 发送状态 【0-未发送，1-已发送】
            Integer sendState = queryDTO.getSendState();
            if (sendState != null) {
                queryWrapper.eq(Notice::getSendState, sendState);
            }

            // 发送时间
            Date sendTimeStart = queryDTO.getSendTimeStart();
            if (sendTimeStart != null) {
                queryWrapper.ge(Notice::getSendTime, sendTimeStart);
            }
            Date sendTimeEnd = queryDTO.getSendTimeEnd();
            if (sendTimeEnd != null) {
                queryWrapper.le(Notice::getSendTime, sendTimeEnd);
            }

            Page<Notice> pageResult = page(page, queryWrapper);
            return PageUtil.toPageR(pageResult, NoticeVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public NoticeInfoVO info(Long id) {
        Notice notice = selectById(id);
        try {
            NoticeInfoVO result = BeanUtil.toBean(notice, NoticeInfoVO.class);
            List<NoticeUser> noticeUserList = noticeUserService.list(new LambdaQueryWrapper<NoticeUser>().eq(NoticeUser::getNoticeId, id));
            result.setUserIds(noticeUserList.stream().map(NoticeUser::getUserId).collect(Collectors.toList()));
            return result;
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public PageR<NoticeUserAllVO> userPage(NoticeUserAllQueryVO noticeUserAllQueryVO) {
        return noticeUserService.noticeQuery(noticeUserAllQueryVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(NoticeSaveVO noticeSaveVO) {
        List<UserBO> userList = checkSaveArgs(noticeSaveVO);
        try {
            // 入库消息表
            Notice notice = BeanUtil.toBean(noticeSaveVO, Notice.class);
            notice.setSendState(NoticeSendState.UNSENT.getValue());
            save(notice);
            // 入库消息用户表
            noticeUserService.save(notice.getId(), userList);
        } catch (Exception e) {
            throw ServiceException.error(Errors.SAVE_ERROR, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Long id, NoticeSaveVO noticeSaveVO) {
        Notice notice = checkNotice(id);
        List<UserBO> userList = checkSaveArgs(noticeSaveVO);
        try {
            BeanUtil.copy(noticeSaveVO, notice);
            updateById(notice);
            // 入库消息用户表
            noticeUserService.save(notice.getId(), userList);
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(Long id) {
        Notice notice = checkNotice(id);
        try {
            notice.setSendState(NoticeSendState.SENT.getValue());
            notice.setSendTime(new Date());
            updateById(notice);
            // 发送状态更新
            noticeUserService.send(notice.getId());
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Notice notice = checkNotice(id);
        try {
            removeById(notice);
            // 删除用户消息
            noticeUserService.delete(notice.getId());
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(DataBody<List<Long>> dataBody) {
        List<Long> ids = CheckUtil.dataNull(dataBody);
        List<Notice> noticeList = listByIds(ids);
        if (CollUtil.isEmpty(noticeList)) {
            throw ServiceException.notFound();
        }
        boolean send = noticeList.stream().anyMatch(item -> !NoticeSendState.UNSENT.getValue().equals(item.getSendState()));
        if (send) {
            throw ServiceException.error(CoreErrors.NOTICE_SEND_STATE_UNSENT);
        }
        List<Long> noticeIds = noticeList.stream().map(Notice::getId).collect(Collectors.toList());
        removeByIds(noticeIds);
        // 删除用户消息
        noticeUserService.delete(noticeIds);
    }

    private List<UserBO> checkSaveArgs(NoticeSaveVO noticeSaveVO) {
        // 标题
        String title = noticeSaveVO.getTitle();
        // 类型【1-消息，2-公告】
        Integer noticeType = noticeSaveVO.getNoticeType();
        // 通知内容
        String html = noticeSaveVO.getHtml();
        // 接收账号ID列表
        List<String> userIds = noticeSaveVO.getUserIds();
        if (StringUtil.isNull(title) || NoticeType.fromValue(noticeType) == null || StringUtil.isNull(html) || CollUtil.isEmpty(userIds)) {
            throw ServiceException.args();
        }
        // 查询目标用户
        List<UserBO> userList = userClient.list(new UserQueryBO().setState(true).setIds(userIds));
        if (CollUtil.isEmpty(userList)) {
            throw ServiceException.error(CoreErrors.NOTICE_USER_EMPTY);
        }
        return userList;
    }

    private Notice checkNotice(Long id) {
        Notice notice = selectById(id);
        // 未发送状态才允许操作
        if (!NoticeSendState.UNSENT.getValue().equals(notice.getSendState())) {
            throw ServiceException.error(CoreErrors.NOTICE_SEND_STATE_UNSENT);
        }
        return notice;
    }

    private Notice selectById(Long id) {
        if (id == null) {
            throw ServiceException.args();
        }
        Notice notice = getById(id);
        if (notice == null) {
            throw ServiceException.notFound();
        }
        return notice;
    }
}




