package com.zsyj.iot.service.impl;

import com.zsyj.iot.entity.IotSimLabUser;
import com.zsyj.iot.mapper.IotSimLabUserDao;
import com.zsyj.iot.service.IotSimLabUserService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

/**
 * 虚拟仿真实验用户记录表(IotSimLabUser)表服务实现类
 *
 * @author Xinxuan Zhuo
 * @since 2024-09-09 08:57:04
 */
@Service("iotSimLabUserService")
public class IotSimLabUserServiceImpl implements IotSimLabUserService {
    @Resource
    private IotSimLabUserDao iotSimLabUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public IotSimLabUser queryById(Long id) {
        return this.iotSimLabUserDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param iotSimLabUser 实例对象
     * @return 实例对象
     */
    @Override
    public IotSimLabUser insert(IotSimLabUser iotSimLabUser) {
        this.iotSimLabUserDao.insert(iotSimLabUser);
        return iotSimLabUser;
    }

    /**
     * 修改数据
     *
     * @param iotSimLabUser 实例对象
     * @return 实例对象
     */
    @Override
    public IotSimLabUser update(IotSimLabUser iotSimLabUser) {
        this.iotSimLabUserDao.update(iotSimLabUser);
        return this.queryById(iotSimLabUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.iotSimLabUserDao.deleteById(id) > 0;
    }
}
