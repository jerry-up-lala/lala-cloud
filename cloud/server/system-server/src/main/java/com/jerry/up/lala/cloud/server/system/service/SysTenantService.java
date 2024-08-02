package com.jerry.up.lala.cloud.server.system.service;


import com.github.yulichang.base.MPJBaseService;
import com.jerry.up.lala.cloud.api.system.bo.SysTenantBO;
import com.jerry.up.lala.cloud.server.system.entity.SysTenant;
import com.jerry.up.lala.cloud.server.system.vo.SysTenantAddVO;
import com.jerry.up.lala.cloud.server.system.vo.SysTenantInfoVO;
import com.jerry.up.lala.cloud.server.system.vo.SysTenantQueryVO;
import com.jerry.up.lala.cloud.server.system.vo.SysTenantUpdateVO;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.model.DataIdBody;
import com.jerry.up.lala.framework.common.r.PageR;

import java.util.List;

/**
 * @author jerry
 * @description 针对表【sys_tenant(集团表)】的数据库操作Service
 * @createDate 2023-09-18 16:19:50
 */
public interface SysTenantService extends MPJBaseService<SysTenant> {

    /**
     * <p>Description: 分页查询
     *
     * @param sysTenantQueryVO 查询条件
     * @return 分页数据
     * @author FMJ
     * @date 2023/8/16 17:58
     * @since v1.0.0
     */
    PageR<SysTenantInfoVO> pageQuery(SysTenantQueryVO sysTenantQueryVO);

    /**
     * <p>Description: 查询
     *
     * @param sysTenantQueryVO 查询条件
     * @return tenant列表
     * @author FMJ
     * @date 2023/8/16 17:22
     * @since v1.0.0
     */
    List<SysTenantInfoVO> listQuery(SysTenantQueryVO sysTenantQueryVO);

    /**
     * <p>Description: 根据集团ID查询集团列表
     * @param dataBody 集团ID
     * @author FMJ
     * @date 2024/7/30 09:49
     * @since v1.0.0
     */
    List<SysTenantBO> listBO(DataBody<List<String>> dataBody);

    /**
     * <p>Description: 查询
     *
     * @param id 主键
     * @return tenant信息
     * @author FMJ
     * @date 2023/8/16 16:02
     * @since v1.0.0
     */
    SysTenantInfoVO info(String id);


    String password(String id);

    /**
     * <p>Description: 校验集团名称是否存在
     *
     * @param dataIdBody 集团ID 新增传递null/集团名称
     * @author FMJ
     * @date 2023/9/25 09:27
     * @since v1.0.0
     */
    void verify(DataIdBody<String, String> dataIdBody);

    /**
     * <p>Description: 新增
     *
     * @param sysTenantAddVO 表单
     * @author FMJ
     * @date 2023/8/16 16:02
     * @since v1.0.0
     */
    void add(SysTenantAddVO sysTenantAddVO);

    /**
     * <p>Description: 更新
     *
     * @param id             主键
     * @param sysTenantUpdateVO 表单
     * @author FMJ
     * @date 2023/8/16 16:02
     * @since v1.0.0
     */
    void update(String id, SysTenantUpdateVO sysTenantUpdateVO);

    /**
     * <p>Description: 删除
     *
     * @param id 主键
     * @author FMJ
     * @date 2023/8/16 16:02
     * @since v1.0.0
     */
    void delete(String id);

    /**
     * <p>Description: 删除
     *
     * @param dataBody 批量删除
     * @author FMJ
     * @date 2023/8/16 16:02
     * @since v1.0.0
     */
    void batchDelete(DataBody<List<String>> dataBody);

}
