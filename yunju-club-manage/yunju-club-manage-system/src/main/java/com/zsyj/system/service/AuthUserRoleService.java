package com.zsyj.system.service;


import com.zsyj.system.domain.AuthUserRole;
import org.apache.ibatis.annotations.Param;

/**
 * (AuthUserRole)表服务接口
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-03 00:18:09
 */
public interface AuthUserRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthUserRole queryById(Long id);

    /**
     * 新增数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    AuthUserRole insert(AuthUserRole authUserRole);

    /**
     * 修改数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    AuthUserRole update(AuthUserRole authUserRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据用户Id，更新角色
     *
     * @param userId 用户id
     * @param roleId 新角色id
     */
    void updateRoleUser(Long userId, Long roleId);
}
