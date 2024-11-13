package com.zsyj.system.service.impl;

import com.zsyj.system.domain.AuthRole;
import com.zsyj.system.mapper.AuthRoleMapper;
import com.zsyj.system.service.AuthRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AuthRole)表服务实现类
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-02 23:55:19
 */
@Service("authRoleService")
public class AuthRoleServiceImpl implements AuthRoleService {
    @Resource
    private AuthRoleMapper authRoleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthRole queryById(Long id) {
        return this.authRoleMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(AuthRole authRole) {
        return this.authRoleMapper.insert(authRole);
    }

    /**
     * 修改数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    @Override
    public int update(AuthRole authRole) {
        return this.authRoleMapper.update(authRole);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.authRoleMapper.deleteById(id) > 0;
    }

    @Override
    public AuthRole queryByCondition(AuthRole authRole) {
        return authRoleMapper.queryAllByLimit(authRole);
    }

    @Override
    public List<AuthRole> queryByRoleList(List<Long> roleIdList) {
        return authRoleMapper.queryByRoleList(roleIdList);
    }
}
