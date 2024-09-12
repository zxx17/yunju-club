package com.zsyj.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zsyj.common.core.domain.entity.SysUser;
import com.zsyj.common.utils.DateUtils;
import com.zsyj.common.utils.SecurityUtils;
import com.zsyj.common.utils.StringUtils;
import com.zsyj.system.domain.SysUserPost;
import com.zsyj.system.domain.SysUserRole;
import com.zsyj.system.mapper.SysUserMapper;
import com.zsyj.system.mapper.SysUserPostMapper;
import com.zsyj.system.mapper.SysUserRoleMapper;
import com.zsyj.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zsyj.system.mapper.AuthUserMapper;
import com.zsyj.system.domain.AuthUser;
import com.zsyj.system.service.IAuthUserService;

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
    private ISysUserService userService;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;


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
    public int updateAuthUser(AuthUser authUser) {
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
