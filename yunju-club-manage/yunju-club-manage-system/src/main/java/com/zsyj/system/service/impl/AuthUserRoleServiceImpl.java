package com.zsyj.system.service.impl;

import com.zsyj.system.domain.AuthUserRole;
import com.zsyj.system.mapper.AuthUserRoleMapper;
import com.zsyj.system.service.AuthUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (AuthUserRole)表服务实现类
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-03 00:18:09
 */
@Service("authUserRoleService")
public class AuthUserRoleServiceImpl implements AuthUserRoleService {
    @Resource
    private AuthUserRoleMapper authUserRoleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthUserRole queryById(Long id) {
        return this.authUserRoleMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public AuthUserRole insert(AuthUserRole authUserRole) {
        this.authUserRoleMapper.insert(authUserRole);
        return authUserRole;
    }

    /**
     * 修改数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public AuthUserRole update(AuthUserRole authUserRole) {
        this.authUserRoleMapper.update(authUserRole);
        return this.queryById(authUserRole.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.authUserRoleMapper.deleteById(id) > 0;
    }

    @Override
    public void updateRoleUser(Long userId, Long roleId) {
       this.authUserRoleMapper.updateRoleUser(userId,roleId);
    }
}
