package com.zsyj.system.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.zsyj.common.core.domain.entity.SysUser;
import com.zsyj.common.core.redis.RedisCache;
import com.zsyj.common.utils.DateUtils;
import com.zsyj.common.utils.StringUtils;
import com.zsyj.system.domain.*;
import com.zsyj.system.mapper.SysUserPostMapper;
import com.zsyj.system.mapper.SysUserRoleMapper;
import com.zsyj.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zsyj.system.mapper.AuthUserMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户信息Service业务层处理
 *
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
@Service
public class AuthUserServiceImpl implements IAuthUserService {
    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    @Resource
    private RedisCache redisCache;


    /**
     * 查询用户信息
     *
     * @param id 用户信息主键
     * @return 用户信息
     */
    @Override
    public AuthUser selectAuthUserById(Long id) {
        return authUserMapper.selectAuthUserById(id);
    }

    /**
     * 查询用户信息列表
     *
     * @param authUser 用户信息
     * @return 用户信息
     */
    @Override
    public List<AuthUser> selectAuthUserList(AuthUser authUser) {
        return authUserMapper.selectAuthUserList(authUser);
    }

    /**
     * 新增用户信息
     *
     * @param authUser 用户信息
     * @return 结果
     */
    @Override
    public int insertAuthUser(AuthUser authUser) {
        return authUserMapper.insertAuthUser(authUser);
    }

    /**
     * 修改用户信息
     *
     * @param authUser 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAuthUser(AuthUser authUser) {
        // TODO 目前的录入题目权限控制先这样做，后面再通过c端的rbac来做，数据库已经是完整的模型，目前不做过多页面，且目前只有两个角色，关联2个权限

        //  如果需要更新用户出题权限
        if (!Objects.isNull(authUser.getRoleKey())){
            String userName = authUser.getUserName();
            String roleKey = authUser.getRoleKey();
            // 1.重建缓存-角色
            AuthRole authRole = new AuthRole();
            authRole.setRoleKey(roleKey);
            List<AuthRole> roleList = new LinkedList<>();
            roleList.add(authRole);
            redisCache.setCacheObject("auth.role."+userName, new Gson().toJson(roleList));


            // 2.更新数据库表auth_role_user表
            // 根据前端的roleKey查出roleId
            AuthRole roleResult = authRoleService.queryByCondition(authRole);
            Long roleId = roleResult.getId();
            // 当前用户id
            Long userId = authUser.getId();
            // 根据用户id，更新新的roleId
            authUserRoleService.updateRoleUser(userId, roleId);


            // 3.重建缓存-权限
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
            redisCache.setCacheObject("auth.permission."+userName, new Gson().toJson(permissionList));

        }
        // 更新auth_user表
        authUser.setUpdateTime(DateUtils.getNowDate());
        return authUserMapper.updateAuthUser(authUser);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteAuthUserByIds(Long[] ids) {
        return authUserMapper.deleteAuthUserByIds(ids);
    }

    /**
     * 删除用户信息信息
     *
     * @param id 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteAuthUserById(Long id) {
        return authUserMapper.deleteAuthUserById(id);
    }


    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotEmpty(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>(posts.length);
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            userPostMapper.batchUserPost(list);
        }
    }

    public void insertUserRole(SysUser user) {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotEmpty(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }
}
