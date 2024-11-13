package com.zsyj.system.service.impl;

import com.zsyj.system.domain.AuthPermission;
import com.zsyj.system.mapper.AuthPermissionMapper;
import com.zsyj.system.service.AuthPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AuthPermission)表服务实现类
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-03 00:45:50
 */
@Service("authPermissionService")
public class AuthPermissionServiceImpl implements AuthPermissionService {
    @Resource
    private AuthPermissionMapper authPermissionMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthPermission queryById(Long id) {
        return this.authPermissionMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(AuthPermission authPermission) {
        return this.authPermissionMapper.insert(authPermission);
    }

    /**
     * 修改数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    @Override
    public int update(AuthPermission authPermission) {
        return this.authPermissionMapper.update(authPermission);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.authPermissionMapper.deleteById(id) > 0;
    }

    @Override
    public List<AuthPermission> queryByRoleList(List<Long> roleIdList) {
        return this.authPermissionMapper.queryByRoleList(roleIdList);
    }
}
