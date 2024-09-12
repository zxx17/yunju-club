package com.zsyj.system.service;

import java.util.List;
import com.zsyj.system.domain.AuthUser;

/**
 * 用户信息Service接口
 * 
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
public interface IAuthUserService 
{
    /**
     * 查询用户信息
     * 
     * @param id 用户信息主键
     * @return 用户信息
     */
    public AuthUser selectAuthUserById(Long id);

    /**
     * 查询用户信息列表
     * 
     * @param authUser 用户信息
     * @return 用户信息集合
     */
    public List<AuthUser> selectAuthUserList(AuthUser authUser);

    /**
     * 新增用户信息
     * 
     * @param authUser 用户信息
     * @return 结果
     */
    public int insertAuthUser(AuthUser authUser);

    /**
     * 修改用户信息
     * 
     * @param authUser 用户信息
     * @return 结果
     */
    public int updateAuthUser(AuthUser authUser);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的用户信息主键集合
     * @return 结果
     */
    public int deleteAuthUserByIds(Long[] ids);

    /**
     * 删除用户信息信息
     * 
     * @param id 用户信息主键
     * @return 结果
     */
    public int deleteAuthUserById(Long id);
}
