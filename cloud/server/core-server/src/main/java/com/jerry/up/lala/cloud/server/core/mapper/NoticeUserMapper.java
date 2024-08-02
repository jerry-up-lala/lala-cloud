package com.jerry.up.lala.cloud.server.core.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.jerry.up.lala.cloud.server.core.dto.NoticeUserCountDTO;
import com.jerry.up.lala.cloud.server.core.entity.NoticeUser;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author jerry
 * @description 针对表【notice_user(通知记录表)】的数据库操作Mapper
 * @createDate 2024-01-10 09:51:24
 * @Entity com.jerry.up.lala.cloud.server.core.entity.NoticeUser
 */
public interface NoticeUserMapper extends MPJBaseMapper<NoticeUser> {

    int insertBatch(@Param("noticeUserCollection") Collection<NoticeUser> noticeUserCollection);

    List<NoticeUserCountDTO> groupCount(@Param("userId") String userId, @Param("readState") Integer readState);

}




