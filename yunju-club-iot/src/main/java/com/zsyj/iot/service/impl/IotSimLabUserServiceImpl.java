package com.zsyj.iot.service.impl;

import com.zsyj.iot.controller.request.SimLabRecordRequest;
import com.zsyj.iot.entity.IotSimLab;
import com.zsyj.iot.entity.IotSimLabUser;
import com.zsyj.iot.mapper.IotSimLabUserDao;
import com.zsyj.iot.service.IotSimLabService;
import com.zsyj.iot.service.IotSimLabUserService;
import com.zsyj.iot.util.LoginUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.Date;

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

    @Resource
    private IotSimLabService iotSimLabService;

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

    @Override
    public Long queryFinishedCount() {
        IotSimLabUser iotSimLabUser = new IotSimLabUser();
        iotSimLabUser.setIsFinished(1);
        return this.iotSimLabUserDao.count(iotSimLabUser);
    }

    /**
     * TODO bug解决和优化（新增或更新不生效 后续重新设计此模块 线程安全问题。。。）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void record(SimLabRecordRequest request) {
        String userId = LoginUtil.getLoginId();
        // 获取实验项目Id
        IotSimLab iotSimLab = iotSimLabService.queryByProjectUrl(request.getProjectUrl());
        if (iotSimLab == null){
            return;
        }
        // 插入用户实验表记录
        IotSimLabUser iotSimLabUser = new IotSimLabUser();
        iotSimLabUser.setUserId(userId);
        iotSimLabUser.setIsFinished(request.getIsFinished());
        iotSimLabUser.setSimLabId(iotSimLab.getId());
        iotSimLabUser.setCreatedBy(userId);
        iotSimLabUser.setCreatedTime(new Date());
        iotSimLabUser.setUpdateBy(userId);
        iotSimLabUser.setUpdateTime(new Date());
        iotSimLabUser.setIsDeleted(0);
//        iotSimLabUserDao.insertOrUpdate(iotSimLabUser);
        iotSimLabUserDao.insert(iotSimLabUser);
        // 如果完成，累加实验表完成次数
        if (request.getIsFinished() == 1) {
            iotSimLab.setFinishedCount(iotSimLab.getFinishedCount() == null ? 1: iotSimLab.getFinishedCount() + 1);
            iotSimLabService.update(iotSimLab);
        }

    }


}
