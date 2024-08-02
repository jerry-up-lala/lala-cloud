package com.jerry.up.lala.cloud.server.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jerry.up.lala.cloud.api.system.bo.UserBO;
import com.jerry.up.lala.cloud.api.system.bo.UserQueryBO;
import com.jerry.up.lala.cloud.api.system.client.UserClient;
import com.jerry.up.lala.cloud.server.core.dto.NoticeUserCountDTO;
import com.jerry.up.lala.cloud.server.core.dto.NoticeUserDTO;
import com.jerry.up.lala.cloud.server.core.dto.NoticeUserQueryDTO;
import com.jerry.up.lala.cloud.server.core.entity.Notice;
import com.jerry.up.lala.cloud.server.core.entity.NoticeUser;
import com.jerry.up.lala.cloud.server.core.enums.NoticeSendState;
import com.jerry.up.lala.cloud.server.core.enums.NoticeType;
import com.jerry.up.lala.cloud.server.core.enums.NoticeUserReadState;
import com.jerry.up.lala.cloud.server.core.mapper.NoticeUserMapper;
import com.jerry.up.lala.cloud.server.core.service.NoticeUserService;
import com.jerry.up.lala.cloud.server.core.vo.*;
import com.jerry.up.lala.framework.boot.page.PageUtil;
import com.jerry.up.lala.framework.boot.satoken.SaTokenUtil;
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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【notice_user(通知记录表)】的数据库操作Service实现
 * @createDate 2024-01-10 09:51:24
 */
@Service
public class NoticeUserServiceImpl extends MPJBaseServiceImpl<NoticeUserMapper, NoticeUser> implements NoticeUserService {

    @Autowired
    private UserClient userClient;

    @Override
    public PageR<NoticeUserAllVO> noticeQuery(NoticeUserAllQueryVO noticeUserAllQueryVO) {
        Page<NoticeUserDTO> page = PageUtil.initPage(noticeUserAllQueryVO);
        try {
            NoticeUserQueryDTO noticeUserQueryDTO = BeanUtil.toBean(noticeUserAllQueryVO, NoticeUserQueryDTO.class);
            IPage<NoticeUserDTO> pageResult = pageDTO(page, noticeUserQueryDTO);
            return PageUtil.toPageR(pageResult, NoticeUserAllVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public PageR<NoticeUserVO> pageQuery(NoticeUserQueryVO noticeUserQueryVO) {
        Page<NoticeUserDTO> page = PageUtil.initPage(noticeUserQueryVO);
        try {
            NoticeUserQueryDTO noticeUserQueryDTO = BeanUtil.toBean(noticeUserQueryVO, NoticeUserQueryDTO.class);
            // 只查询当前用户 已发送的消息
            noticeUserQueryDTO.setUserId(SaTokenUtil.currentUser().getUserId());
            noticeUserQueryDTO.setSendState(NoticeSendState.SENT.getValue());
            IPage<NoticeUserDTO> pageResult = pageDTO(page, noticeUserQueryDTO);
            return PageUtil.toPageR(pageResult, NoticeUserVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<NoticeUserVO> listQuery(NoticeUserQueryDTO noticeUserQueryDTO) {
        try {
            MPJLambdaWrapper<NoticeUser> query = loadQuery(noticeUserQueryDTO);
            List<NoticeUserDTO> noticeUserList = selectJoinList(NoticeUserDTO.class, query);
            loadUserInfo(noticeUserList);
            return BeanUtil.toBeanList(noticeUserList, NoticeUserVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private void loadUserInfo(List<NoticeUserDTO> noticeUserList) {
        List<String> userIds = noticeUserList.stream().map(NoticeUserDTO::getUserId).distinct().collect(Collectors.toList());
        List<UserBO> userBOList = userClient.list(new UserQueryBO().setIds(userIds));
        if (CollUtil.isEmpty(userBOList)) {
            return;
        }
        Map<String, UserBO> userMap = userBOList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(UserBO::getId, Function.identity(), (key1, key2) -> key1));
        noticeUserList.forEach(noticeUser -> {
            UserBO userBO = userMap.get(noticeUser.getUserId());
            if (userBO != null) {
                noticeUser.setLoginName(userBO.getLoginName());
                noticeUser.setRealName(userBO.getRealName());
            }
        });
    }

    @Override
    public Map<Integer, Long> unreadCount() {
        try {
            Map<Integer, Long> result = new HashMap<>();
            List<NoticeUserCountDTO> countList = getBaseMapper().groupCount(SaTokenUtil.currentUser().getUserId(), NoticeUserReadState.UNREAD.getValue());
            Map<Integer, Long> countMap = countList.stream().filter(Objects::nonNull)
                    .collect(Collectors.toMap(NoticeUserCountDTO::getNoticeType, NoticeUserCountDTO::getCount, (key1, key2) -> key2));
            Arrays.stream(NoticeType.values()).forEach(noticeType -> {
                Integer key = noticeType.getValue();
                Long value = countMap.get(key);
                result.put(key, value != null ? value : 0L);
            });
            // 所有数据相加
            result.put(0, countList.stream().mapToLong(NoticeUserCountDTO::getCount).sum());
            return result;
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public NoticeInfoUserVO info(Long id) {
        if (id == null) {
            throw ServiceException.args();
        }
        NoticeUserDTO noticeUserDTO = infoDTO(id);
        read(ListUtil.of(id));
        return BeanUtil.toBean(noticeUserDTO, NoticeInfoUserVO.class);
    }

    @Override
    public void read(DataBody<List<Long>> dataBody) {
        List<Long> ids = CheckUtil.dataNull(dataBody);
        read(ids);
    }

    @Override
    public void readAll() {
        read(new ArrayList<>());
    }

    public void read(List<Long> ids) {
        try {
            Date now = new Date();
            LambdaUpdateWrapper<NoticeUser> updateWrapper = new LambdaUpdateWrapper<NoticeUser>()
                    .eq(NoticeUser::getReadState, NoticeUserReadState.UNREAD.getValue())
                    .set(NoticeUser::getReadState, NoticeUserReadState.READ.getValue())
                    .set(NoticeUser::getReadTime, now)
                    .set(NoticeUser::getUpdateTime, now)
                    .set(NoticeUser::getUpdateUser, SaTokenUtil.currentUser().getUserId());
            if (CollUtil.isNotEmpty(ids)) {
                updateWrapper.in(NoticeUser::getId, ids);
            }
            update(updateWrapper);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private NoticeUserDTO infoDTO(Long id) {
        NoticeUserDTO noticeUserDTO;
        try {
            MPJLambdaWrapper<NoticeUser> query = loadQuery(new NoticeUserQueryDTO()
                    .setNoticeId(id).setUserId(SaTokenUtil.currentUser().getUserId()));
            noticeUserDTO = selectJoinOne(NoticeUserDTO.class, query);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
        if (noticeUserDTO == null) {
            throw ServiceException.notFound();
        }
        UserBO userBO = userClient.info(new DataBody<>(noticeUserDTO.getUserId()));
        if (userBO != null) {
            noticeUserDTO.setLoginName(userBO.getLoginName());
            noticeUserDTO.setRealName(userBO.getRealName());
        }
        return noticeUserDTO;
    }

    private IPage<NoticeUserDTO> pageDTO(Page<NoticeUserDTO> page, NoticeUserQueryDTO noticeUserQueryDTO) {
        MPJLambdaWrapper<NoticeUser> query = loadQuery(noticeUserQueryDTO);
        IPage<NoticeUserDTO> pageResult = selectJoinListPage(page, NoticeUserDTO.class, query);
        loadUserInfo(pageResult.getRecords());
        return pageResult;
    }

    private MPJLambdaWrapper<NoticeUser> loadQuery(NoticeUserQueryDTO noticeUserQueryDTO) {
        MPJLambdaWrapper<NoticeUser> wrapper = new MPJLambdaWrapper<NoticeUser>().selectAll(NoticeUser.class)
                .select(Notice::getTitle, Notice::getHtml, Notice::getNoticeType, Notice::getSendTime)
                .leftJoin(Notice.class, on -> on.eq(NoticeUser::getNoticeId, Notice::getId))
                .orderByDesc(Notice::getSendTime).orderByDesc(NoticeUser::getReadTime);
        if (noticeUserQueryDTO != null) {
            // 限制大小
            Long limit = noticeUserQueryDTO.getLimit();
            if (limit != null) {
                wrapper.last(" limit " + limit);
            }
            // 账号ID
            String userId = noticeUserQueryDTO.getUserId();
            // 用户名
            String loginName = noticeUserQueryDTO.getLoginName();
            // 姓名
            String realName = noticeUserQueryDTO.getRealName();
            if (StringUtil.isNotNull(userId) || StringUtil.isNotNull(loginName) || StringUtil.isNotNull(realName)) {
                List<UserBO> userList = userClient.list(new UserQueryBO().setId(userId).setLoginName(loginName).setRealName(realName));
                List<String> userIds = CollUtil.isNotEmpty(userList) ?
                        userList.stream().map(UserBO::getId).collect(Collectors.toList()) : new ArrayList<>();
                wrapper.in(NoticeUser::getUserId, userIds);
            }
            // 标题
            String title = noticeUserQueryDTO.getTitle();
            if (StringUtil.isNotNull(title)) {
                wrapper.like(Notice::getTitle, title);
            }
            // 类型【1-消息，2-公告】
            Integer noticeType = noticeUserQueryDTO.getNoticeType();
            if (noticeType != null) {
                wrapper.eq(Notice::getNoticeType, noticeType);
            }
            // 发送状态 【0-未发送，1-已发送】
            Integer sendState = noticeUserQueryDTO.getSendState();
            if (sendState != null) {
                wrapper.eq(Notice::getSendState, sendState);
            }
            // 发送时间
            Date sendTimeStart = noticeUserQueryDTO.getSendTimeStart();
            if (sendTimeStart != null) {
                wrapper.ge(Notice::getSendTime, sendTimeStart);
            }
            Date sendTimeEnd = noticeUserQueryDTO.getSendTimeEnd();
            if (sendTimeEnd != null) {
                wrapper.le(Notice::getSendTime, sendTimeEnd);
            }
            // 通知ID
            Long noticeId = noticeUserQueryDTO.getNoticeId();
            if (noticeId != null) {
                wrapper.eq(NoticeUser::getNoticeId, noticeId);
            }
            // 阅读状态【0-未发送，1-未读，2-已读】
            Integer readState = noticeUserQueryDTO.getReadState();
            if (readState != null) {
                wrapper.eq(NoticeUser::getReadState, readState);
            }
            // 阅读时间
            Date readTimeStart = noticeUserQueryDTO.getReadTimeStart();
            if (readTimeStart != null) {
                wrapper.ge(NoticeUser::getReadTime, readTimeStart);
            }
            Date readTimeEnd = noticeUserQueryDTO.getReadTimeEnd();
            if (readTimeEnd != null) {
                wrapper.le(NoticeUser::getReadTime, readTimeEnd);
            }
        }
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long noticeId, List<UserBO> userList) {
        delete(noticeId);
        List<NoticeUser> noticeUserList = userList.stream().map(user -> {
            NoticeUser noticeUser = new NoticeUser().setNoticeId(noticeId).setUserId(user.getId())
                    .setReadState(NoticeUserReadState.UNSENT.getValue());
            noticeUser.setDeleted(false);
            return noticeUser;
        }).collect(Collectors.toList());
        getBaseMapper().insertBatch(noticeUserList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(Long noticeId) {
        LambdaUpdateWrapper<NoticeUser> updateWrapper = new LambdaUpdateWrapper<NoticeUser>()
                .set(NoticeUser::getReadState, NoticeUserReadState.UNREAD.getValue())
                .set(NoticeUser::getUpdateUser, SaTokenUtil.currentUser().getUserId())
                .set(NoticeUser::getUpdateTime, new Date())
                .eq(NoticeUser::getNoticeId, noticeId);
        update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long noticeId) {
        remove(new LambdaQueryWrapper<NoticeUser>().eq(NoticeUser::getNoticeId, noticeId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> noticeIds) {
        remove(new LambdaQueryWrapper<NoticeUser>().in(NoticeUser::getNoticeId, noticeIds));
    }

    @Override
    public void userDelete(Long id) {
        try {
            remove(new LambdaQueryWrapper<NoticeUser>().eq(NoticeUser::getId, id).eq(NoticeUser::getUserId, SaTokenUtil.currentUser().getUserId()));
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

    @Override
    public void userBatchDelete(DataBody<List<Long>> dataBody) {
        List<Long> ids = CheckUtil.dataNull(dataBody);
        try {
            remove(new LambdaQueryWrapper<NoticeUser>().in(NoticeUser::getId, ids).eq(NoticeUser::getUserId, SaTokenUtil.currentUser().getUserId()));
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

}




