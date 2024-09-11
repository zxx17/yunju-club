package com.zsyj.auth.application.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.zsyj.auth.application.convert.AuthUserDTOConverter;
import com.zsyj.auth.domain.entity.AuthUserBO;
import com.zsyj.auth.domain.service.AuthUserDomainService;
import com.zsyj.auth.entity.AuthUserDTO;
import com.zsyj.auth.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 用户相关接口
 * TODO 这里的接口要和前端对接后修改requestmapping的method
 */
@RestController
@RequestMapping("/user/")
@Slf4j
public class UserController {

    @Resource
    private AuthUserDomainService authUserDomainService;

    /**
     * 用户注册
     */
    @RequestMapping("register")
    public Result<Boolean> register(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.register.dto:{}", JSON.toJSONString(authUserDTO));
            }
            // 校验用户注册参数
            checkUserInfo(authUserDTO);
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
            return Result.ok(authUserDomainService.register(authUserBO));
        } catch (Exception e) {
            log.error("UserController.register.error:{}", e.getMessage(), e);
            return Result.fail("注册用户失败");
        }
    }

    /**
     * 修改用户信息
     */
    @RequestMapping("update")
    public Result<Boolean> update(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.update.dto:{}", JSON.toJSONString(authUserDTO));
            }
            checkUserInfo(authUserDTO);
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
            return Result.ok(authUserDomainService.update(authUserBO));
        } catch (Exception e) {
            log.error("UserController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新用户信息失败");
        }
    }


    @RequestMapping("doLogin")
    public Result<SaTokenInfo> doLogin(@RequestParam("validCode") String validCode,
                                @RequestBody(required = false)AuthUserDTO authUserDTO ) {
        try {
            if(authUserDTO == null){
                Preconditions.checkArgument(!StringUtils.isBlank(validCode), "验证码不能为空!");
            }
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
            return Result.ok(authUserDomainService.doLogin(validCode,authUserBO));
        } catch (Exception e) {
            log.error("UserController.doLogin.error:{}", e.getMessage(), e);
            return Result.fail("用户登录失败");
        }
    }


    /**
     * 获取用户信息
     */
    @RequestMapping("getUserInfo")
    public Result<AuthUserDTO> getUserInfo(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.getUserInfo.dto:{}", JSON.toJSONString(authUserDTO));
            }
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getUserName()), "用户名不能为空");
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
            AuthUserBO userInfo = authUserDomainService.getUserInfo(authUserBO);
            userInfo.setPassword(null);
            return Result.ok(AuthUserDTOConverter.INSTANCE.convertBOToDTO(userInfo));
        } catch (Exception e) {
            log.error("UserController.getUserInfo.error:{}", e.getMessage(), e);
            return Result.fail("获取用户信息失败");
        }
    }

    /**
     * 批量获取用户信息
     */
    @RequestMapping("listByIds")
    public Result<List<AuthUserDTO>> listUserInfoByIds(@RequestBody List<String> userNameList) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.listUserInfoByIds.dto:{}", JSON.toJSONString(userNameList));
            }
            Preconditions.checkArgument(!CollectionUtils.isEmpty(userNameList), "id集合不能为空");
            List<AuthUserBO> userInfos = authUserDomainService.listUserInfoByIds(userNameList);
            return Result.ok(AuthUserDTOConverter.INSTANCE.convertBOToDTO(userInfos));
        } catch (Exception e) {
            log.error("UserController.listUserInfoByIds.error:{}", e.getMessage(), e);
            return Result.fail("批量获取用户信息失败");
        }
    }


    /**
     * 用户退出
     */
    @RequestMapping("logOut")
    public Result logOut(@RequestParam String userName) {
        try {
            log.info("UserController.logOut.userName:{}", userName);
            Preconditions.checkArgument(!StringUtils.isBlank(userName), "用户名不能为空");
            StpUtil.logout(userName);
            return Result.ok();
        } catch (Exception e) {
            log.error("UserController.logOut.error:{}", e.getMessage(), e);
            return Result.fail("用户登出失败");
        }
    }

    /**
     * 删除用户
     */
    @RequestMapping("delete")
    public Result<Boolean> delete(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.delete.dto:{}", JSON.toJSONString(authUserDTO));
            }
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
            return Result.ok(authUserDomainService.update(authUserBO));
        } catch (Exception e) {
            log.error("UserController.update.error:{}", e.getMessage(), e);
            return Result.fail("删除用户信息失败");
        }
    }


    /**
     * 用户启用/禁用
     */
    @RequestMapping("changeStatus")
    public Result<Boolean> changeStatus(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("UserController.changeStatus.dto:{}", JSON.toJSONString(authUserDTO));
            }
            Preconditions.checkNotNull(authUserDTO.getStatus(), "用户状态不能为空");
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
            return Result.ok(authUserDomainService.update(authUserBO));
        } catch (Exception e) {
            log.error("UserController.changeStatus.error:{}", e.getMessage(), e);
            return Result.fail("启用/禁用用户信息失败");
        }
    }




    private void checkUserInfo(AuthUserDTO authUserDTO) {
        Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getUserName()), "用户名不能为空");
    }



}
