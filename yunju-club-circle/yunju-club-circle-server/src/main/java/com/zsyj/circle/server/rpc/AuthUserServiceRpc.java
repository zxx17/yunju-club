package com.zsyj.circle.server.rpc;

import com.zsyj.auth.api.UserFeignService;
import com.zsyj.auth.entity.AuthUserDTO;
import com.zsyj.auth.entity.Result;
import com.zsyj.circle.server.entity.dto.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/13
 */
@Component
public class AuthUserServiceRpc {

    @Resource
    private UserFeignService userFeignService;

    /**
     * 根据用户名远程调用获取用户信息
     */
    public UserInfo getUserInfo(String userName) {
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setUserName(userName);
        Result<AuthUserDTO> result = userFeignService.getUserInfo(authUserDTO);
        UserInfo userInfo = new UserInfo();
        if (!result.getSuccess()) {
            return userInfo;
        }
        AuthUserDTO data = result.getData();
        userInfo.setUserName(data.getUserName());
        userInfo.setNickName(data.getNickName());
        userInfo.setAvatar(data.getAvatar());
        return userInfo;
    }

    /**
     * 批量获取用户信息
     */
    public Map<String, UserInfo> batchGetUserInfo(List<String> userNameList) {
        if (CollectionUtils.isEmpty(userNameList)) {
            return Collections.emptyMap();
        }
        Result<List<AuthUserDTO>> listResult = userFeignService.listUserInfoByIds(userNameList);
        if (Objects.isNull(listResult) || !listResult.getSuccess() || Objects.isNull(listResult.getData())) {
            return Collections.emptyMap();
        }
        Map<String, UserInfo> result = new HashMap<>();
        for (AuthUserDTO data : listResult.getData()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(data.getUserName());
            userInfo.setNickName(data.getNickName());
            userInfo.setAvatar(data.getAvatar());
            result.put(userInfo.getUserName(), userInfo);
        }
        return result;
    }

}
