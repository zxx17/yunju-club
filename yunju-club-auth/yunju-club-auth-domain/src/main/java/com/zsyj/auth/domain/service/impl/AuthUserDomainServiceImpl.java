package com.zsyj.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.gson.Gson;
import com.zsyj.auth.common.enums.AuthUserStatusEnum;
import com.zsyj.auth.common.enums.IsDeletedFlagEnum;
import com.zsyj.auth.domain.constant.AuthConstant;
import com.zsyj.auth.domain.convert.AuthUserBOConverter;
import com.zsyj.auth.domain.entity.AuthUserBO;
import com.zsyj.auth.domain.redis.RedisUtil;
import com.zsyj.auth.domain.service.AuthUserDomainService;
import com.zsyj.auth.infra.basic.entity.*;
import com.zsyj.auth.infra.basic.service.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthUserDomainServiceImpl implements AuthUserDomainService {

    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private RedisUtil redisUtil;

    private static final String salt = "zsyj-club-salt";

    private static final String authPermissionPrefix = "auth.permission";

    private static final String authRolePrefix = "auth.role";

    private static final String LOGIN_PREFIX = "loginCode";

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        //校验用户是否存在
        AuthUser existAuthUser = new AuthUser();
        existAuthUser.setUserName(authUserBO.getUserName());
        List<AuthUser> existUser = authUserService.queryByCondition(existAuthUser);
        if (existUser.size() > 0) {
            return true;
        }
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        if (StringUtils.isNotBlank(authUser.getPassword())) {
            authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), salt));
        }
        if (StringUtils.isBlank(authUser.getAvatar())) {
            // TODO 更换默认头像
            authUser.setAvatar("http://117.72.10.84:9000/user/icon/微信图片_20231203153718(1).png");
        }
        if (StringUtils.isBlank(authUser.getNickName())) {
            // TODO 更换默认昵称
            authUser.setNickName("default-0.0-");
        }
        Date registerTime = new Date();
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        authUser.setCreatedTime(registerTime);
        authUser.setUpdateTime(registerTime);
        // 插入用户信息
        Integer count = authUserService.insert(authUser);

        //建立一个初步的默认的角色的关联  插入角色信息 到用户角色关联表
        AuthRole authRole = new AuthRole();
        authRole.setRoleKey(AuthConstant.NORMAL_USER);
        AuthRole roleResult = authRoleService.queryByCondition(authRole);
        Long roleId = roleResult.getId();
        Long userId = authUser.getId();
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(userId);
        authUserRole.setRoleId(roleId);
        authUserRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        authUserRole.setCreatedTime(registerTime);
        authUserRole.setUpdateTime(registerTime);
        authUserRoleService.insert(authUserRole);
        // 缓存角色信息
        String roleKey = redisUtil.buildKey(authRolePrefix, authUser.getUserName());
        List<AuthRole> roleList = new LinkedList<>();
        roleList.add(authRole);
        redisUtil.set(roleKey, new Gson().toJson(roleList));

        // 初始化用户权限
        AuthRolePermission authRolePermission = new AuthRolePermission();
        // 根据RoleId查对应的权限ID
        authRolePermission.setRoleId(roleId);
        List<AuthRolePermission> rolePermissionList = authRolePermissionService.
                queryByCondition(authRolePermission);
        List<Long> permissionIdList = rolePermissionList.stream()
                .map(AuthRolePermission::getPermissionId).collect(Collectors.toList());
        //根据权限Id查权限
        List<AuthPermission> permissionList = authPermissionService.queryByRoleList(permissionIdList);
        String permissionKey = redisUtil.buildKey(authPermissionPrefix, authUser.getUserName());
        // 缓存该用户的角色的权限
        redisUtil.set(permissionKey, new Gson().toJson(permissionList));

        return count > 0;
    }

    @Override
    public Boolean update(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        if(!StringUtils.isBlank(authUser.getPassword())){
            authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), salt));
        }
        authUser.setUpdateTime(new Date());
        Integer count = authUserService.updateByUserName(authUser);
        return count > 0;
    }

    @Override
    public Boolean delete(AuthUserBO authUserBO) {
        AuthUser authUser = new AuthUser();
        authUser.setId(authUserBO.getId());
        authUser.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = authUserService.update(authUser);
        //有任何的更新，都要与缓存进行同步的修改
        return count > 0;
    }

    @Override
    public SaTokenInfo doLogin(String validCode, AuthUserBO bo) {
        if(bo == null){
            String loginKey = redisUtil.buildKey(LOGIN_PREFIX, validCode);
            String openId = redisUtil.get(loginKey);
            if (StringUtils.isBlank(openId)) {
                log.warn("doLogin.error:{}", "验证码错误");
                return null;
            }
            AuthUserBO authUserBO = new AuthUserBO();
            authUserBO.setUserName(openId);
            this.register(authUserBO);
            StpUtil.login(openId);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return tokenInfo;
        }else{
            AuthUser authUser = new AuthUser();
            authUser.setUserName(bo.getUserName());
            List<AuthUser> authUsers = authUserService.queryByCondition(authUser);
            if (CollectionUtils.isEmpty(authUsers)){
                log.warn("doLogin.error:{}", "用户不存在");
                return null;
            }
            if (authUsers.get(0).getPassword().equals(SaSecureUtil.md5BySalt(bo.getPassword(), salt))){
                StpUtil.login(authUsers.get(0).getUserName());
                SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
                return tokenInfo;
            }else{
                log.warn("doLogin.error:{}", "密码错误");
                return null;
            }
        }

    }

    @Override
    public AuthUserBO getUserInfo(AuthUserBO authUserBO) {
        AuthUser authUser = new AuthUser();
        authUser.setUserName(authUserBO.getUserName());
        // 容错性处理
        List<AuthUser> userList = authUserService.queryByCondition(authUser);
        if (CollectionUtils.isEmpty(userList)) {
            return new AuthUserBO();
        }
        AuthUser user = userList.get(0);
        return AuthUserBOConverter.INSTANCE.convertEntityToBO(user);
    }

    @Override
    public List<AuthUserBO> listUserInfoByIds(List<String> userNameList) {
        List<AuthUser> userList = authUserService.listUserInfoByIds(userNameList);
        if (CollectionUtils.isEmpty(userList)) {
            return Collections.emptyList();
        }
        return AuthUserBOConverter.INSTANCE.convertEntityToBO(userList);
    }

}
