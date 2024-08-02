package com.jerry.up.lala.cloud.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.cloud.api.system.bo.UserBO;
import com.jerry.up.lala.cloud.api.system.bo.UserQueryBO;
import com.jerry.up.lala.cloud.server.system.bo.UserLoadBO;
import com.jerry.up.lala.cloud.server.system.dto.UserDTO;
import com.jerry.up.lala.cloud.server.system.dto.UserQueryDTO;
import com.jerry.up.lala.cloud.server.system.entity.User;
import com.jerry.up.lala.cloud.server.system.vo.*;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.model.DataIdBody;
import com.jerry.up.lala.framework.common.r.PageR;

import java.util.List;

/**
* @author jerry
* @description 针对表【user(账号表)】的数据库操作Service
* @createDate 2023-09-06 09:56:37
*/
public interface UserService extends IService<User> {

    List<UserDTO> listDTO(UserQueryDTO queryDTO, UserLoadBO userLoadBO);

    List<UserVO> list(UserQueryVO userQueryVO);

    List<UserBO> listBO(UserQueryBO userQueryBO);

    PageR<UserVO> pageQuery(UserQueryVO userQueryVO);

    UserInfoVO info(String id);

    UserBO infoBO(DataBody<String> dataBody);

    UserPersonalVO personalInfo();

    String password(String id);

    void personalUpdatePassWord(UserPersonalPassWordVO userPersonalPassWordVO);

    void verify(DataIdBody<String, String> dataIdBody);

    void add(UserSaveVO userSaveVO);

    void update(String id, UserSaveVO userSaveVO);

    void personalSave(UserPersonalSaveVO userPersonalSaveVO);

    void checkUserSaveArg(UserSaveVO userSaveVO);

    void state(UserStateVO userStateVO);

}
