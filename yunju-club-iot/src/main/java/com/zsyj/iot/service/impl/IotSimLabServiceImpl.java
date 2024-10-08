package com.zsyj.iot.service.impl;

import com.zsyj.iot.entity.IotSimLab;
import com.zsyj.iot.mapper.IotSimLabDao;
import com.zsyj.iot.service.IotSimLabService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

/**
 * 虚拟仿真实验表(IotSimLab)表服务实现类
 *
 * @author Xinxuan Zhuo
 * @since 2024-09-09 08:57:04
 */
@Service("iotSimLabService")
public class IotSimLabServiceImpl implements IotSimLabService {
    @Resource
    private IotSimLabDao iotSimLabDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public IotSimLab queryById(Long id) {
        return this.iotSimLabDao.queryById(id);
    }



    /**
     * 新增数据
     *
     * @param iotSimLab 实例对象
     * @return 实例对象
     */
    @Override
    public IotSimLab insert(IotSimLab iotSimLab) {
        this.iotSimLabDao.insert(iotSimLab);
        return iotSimLab;
    }

    /**
     * 修改数据
     *
     * @param iotSimLab 实例对象
     * @return 实例对象
     */
    @Override
    public IotSimLab update(IotSimLab iotSimLab) {
        this.iotSimLabDao.update(iotSimLab);
        return this.queryById(iotSimLab.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.iotSimLabDao.deleteById(id) > 0;
    }

    @Override
    public List<IotSimLab> queryAllSimLabData() {
        return this.iotSimLabDao.queryAllByLimit();
    }

    @Override
    public Long queryProjectCount() {
        return this.iotSimLabDao.count(new IotSimLab());
    }

    @Override
    public IotSimLab queryByProjectUrl(String projectUrl) {
        return this.iotSimLabDao.queryByProjectUrl(projectUrl);
    }
}
