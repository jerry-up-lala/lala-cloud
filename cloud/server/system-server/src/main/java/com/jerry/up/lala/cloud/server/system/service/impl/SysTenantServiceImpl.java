package com.jerry.up.lala.cloud.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jerry.up.lala.cloud.api.system.bo.SysTenantBO;
import com.jerry.up.lala.cloud.server.system.dto.SysTenantDTO;
import com.jerry.up.lala.cloud.server.system.entity.SysTenant;
import com.jerry.up.lala.cloud.server.system.entity.User;
import com.jerry.up.lala.cloud.server.system.error.SystemErrors;
import com.jerry.up.lala.cloud.server.system.mapper.SysTenantMapper;
import com.jerry.up.lala.cloud.server.system.service.SysTenantService;
import com.jerry.up.lala.cloud.server.system.service.UserService;
import com.jerry.up.lala.cloud.server.system.vo.*;
import com.jerry.up.lala.framework.boot.crypto.RSAUtil;
import com.jerry.up.lala.framework.boot.page.PageUtil;
import com.jerry.up.lala.framework.boot.satoken.SaTokenUtil;
import com.jerry.up.lala.framework.boot.tenant.TenantContext;
import com.jerry.up.lala.framework.common.exception.Errors;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.model.DataIdBody;
import com.jerry.up.lala.framework.common.r.PageR;
import com.jerry.up.lala.framework.common.util.BeanUtil;
import com.jerry.up.lala.framework.common.util.CheckUtil;
import com.jerry.up.lala.framework.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author jerry
 * @description 针对表【sys_tenant(集团表)】的数据库操作Service实现
 * @createDate 2023-09-18 16:19:50
 */
@Service
public class SysTenantServiceImpl extends MPJBaseServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    @Autowired
    private UserService userService;

    @Override
    public PageR<SysTenantInfoVO> pageQuery(SysTenantQueryVO sysTenantQueryVO) {
        Page<SysTenantDTO> page = PageUtil.initPage(sysTenantQueryVO);
        try {
            MPJLambdaWrapper<SysTenant> query = loadQuery(sysTenantQueryVO);
            IPage<SysTenantDTO> pageResult = selectJoinListPage(page, SysTenantDTO.class, query);
            return PageUtil.toPageR(pageResult, SysTenantInfoVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<SysTenantInfoVO> listQuery(SysTenantQueryVO sysTenantQueryVO) {
        try {
            MPJLambdaWrapper<SysTenant> query = loadQuery(sysTenantQueryVO);
            List<SysTenantDTO> tenantList = selectJoinList(SysTenantDTO.class, query);
            return BeanUtil.toBeanList(tenantList, SysTenantInfoVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<SysTenantBO> listBO(DataBody<List<String>> dataBody) {
        List<String> ids = CheckUtil.dataNull(dataBody);
        try {
            MPJLambdaWrapper<SysTenant> query = loadQuery().in(SysTenant::getId, ids);
            List<SysTenantDTO> tenantList = selectJoinList(SysTenantDTO.class, query);
            return BeanUtil.toBeanList(tenantList, SysTenantBO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public SysTenantInfoVO info(String id) {
        SysTenantDTO tenant = get(id);
        return BeanUtil.toBean(tenant, SysTenantInfoVO.class);
    }

    @Override
    public String password(String id) {
        SysTenantDTO tenant = get(id);
        return RSAUtil.decrypt(tenant.getPassWord());
    }

    @Override
    public void verify(DataIdBody<String, String> dataIdBody) {
        String value = CheckUtil.dataNull(dataIdBody);
        // 根据user_id 查询用户列表
        boolean exists;
        try {
            LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<SysTenant>()
                    .eq(SysTenant::getTenantName, value);
            if (StringUtil.isNotNull(dataIdBody.getId())) {
                queryWrapper.ne(SysTenant::getId, dataIdBody.getId());
            }
            exists = exists(queryWrapper);
        } catch (Exception e) {
            throw ServiceException.error(Errors.VERIFY_ERROR, e);
        }
        if (exists) {
            throw ServiceException.error(SystemErrors.TENANT_NAME_EXISTS);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysTenantAddVO sysTenantAddVO) {
        checkSaveArg(sysTenantAddVO);
        UserSaveVO userSaveVO = sysTenantAddVO.getUser();
        userService.checkUserSaveArg(userSaveVO);
        try {
            // 新增集团
            SysTenant sysTenant = new SysTenant().setTenantName(sysTenantAddVO.getTenantName())
                    .setState(sysTenantAddVO.getState() != null ? sysTenantAddVO.getState() : true);
            sysTenant.setCreateTime(new Date());
            sysTenant.setCreateUser(SaTokenUtil.currentUser().getUserId());
            save(sysTenant);
            // 新增集团管理员
            User user = BeanUtil.toBean(userSaveVO, User.class);
            TenantContext.setTenantId(sysTenant.getId());
            user.setPassWord(RSAUtil.encrypt(user.getPassWord()));
            user.setState(true);
            user.setTenantAdmin(true);
            user.setCreateTime(new Date());
            user.setCreateUser(SaTokenUtil.currentUser().getUserId());
            userService.save(user);
            TenantContext.clear();
        } catch (Exception e) {
            throw ServiceException.error(Errors.SAVE_ERROR, e);
        }
    }

    @Override
    public void update(String id, SysTenantUpdateVO sysTenantUpdateVO) {
        checkSaveArg(id, sysTenantUpdateVO);
        SysTenantDTO oldTenant = get(id);
        try {
            oldTenant.setTenantName(sysTenantUpdateVO.getTenantName())
                    .setState(sysTenantUpdateVO.getState() != null ? sysTenantUpdateVO.getState() : oldTenant.getState());
            oldTenant.setUpdateTime(new Date());
            oldTenant.setUpdateUser(SaTokenUtil.currentUser().getUserId());
            updateById(oldTenant);
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    public void delete(String id) {
        if (StringUtil.isNull(id)) {
            throw ServiceException.args();
        }
        try {
            removeById(id);
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

    @Override
    public void batchDelete(DataBody<List<String>> dataBody) {
        List<String> value = CheckUtil.dataNull(dataBody);
        try {
            remove(new LambdaQueryWrapper<SysTenant>().in(SysTenant::getId, value));
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

    private SysTenantDTO get(String id) {
        if (StringUtil.isNull(id)) {
            throw ServiceException.args();
        }
        SysTenantDTO tenant = selectJoinOne(SysTenantDTO.class, loadQuery().eq(SysTenant::getId, id));
        if (tenant == null) {
            throw ServiceException.notFound();
        }
        return tenant;
    }

    private MPJLambdaWrapper<SysTenant> loadQuery(SysTenantQueryVO sysTenantQueryVO) {
        MPJLambdaWrapper<SysTenant> query = loadQuery();
        if (sysTenantQueryVO != null) {
            String tenantName = sysTenantQueryVO.getTenantName();
            if (StringUtil.isNotNull(tenantName)) {
                query.eq(SysTenant::getTenantName, tenantName);
            }
            Boolean state = sysTenantQueryVO.getState();
            if (state != null) {
                query.eq(SysTenant::getState, state);
            }
        }
        return query;
    }

    private MPJLambdaWrapper<SysTenant> loadQuery() {
        return new MPJLambdaWrapper<SysTenant>()
                .selectAll(SysTenant.class)
                .selectAs(User::getId, SysTenantDTO::getUserId)
                .select(User::getLoginName)
                .select(User::getPassWord)
                .select(User::getRealName)
                .selectAs(User::getState, SysTenantDTO::getUserState)
                .select(User::getTenantAdmin)
                .leftJoin(User.class, on -> on.eq(SysTenant::getId, User::getTenantId).eq(User::getTenantAdmin, true))
                .orderByAsc(SysTenant::getCreateTime);
    }

    public void checkSaveArg(SysTenantUpdateVO sysTenantUpdateVO) {
        checkSaveArg(null, sysTenantUpdateVO);
    }

    public void checkSaveArg(String id, SysTenantUpdateVO sysTenantUpdateVO) {
        if (sysTenantUpdateVO == null) {
            throw ServiceException.args();
        }
        Boolean state = sysTenantUpdateVO.getState();
        if (state == null) {
            throw ServiceException.args();
        }
        verify(new DataIdBody<>(id, sysTenantUpdateVO.getTenantName()));
    }

}




