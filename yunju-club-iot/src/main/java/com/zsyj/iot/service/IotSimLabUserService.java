package com.zsyj.iot.service;


import com.zsyj.iot.entity.IotSimLabUser;


/**
 * 虚拟仿真实验用户记录表(IotSimLabUser)表服务接口
 *
 * @author Xinxuan Zhuo
 * @since 2024-09-09 08:57:04
 */
public interface IotSimLabUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    IotSimLabUser queryById(Long id);

    /**
     * 新增数据
     *
     * @param iotSimLabUser 实例对象
     * @return 实例对象
     */
    IotSimLabUser insert(IotSimLabUser iotSimLabUser);

    /**
     * 修改数据
     *
     * @param iotSimLabUser 实例对象
     * @return 实例对象
     */
    IotSimLabUser update(IotSimLabUser iotSimLabUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    Long queryFinishedCount();
}
