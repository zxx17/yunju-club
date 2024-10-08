package com.zsyj.iot.service;


import com.zsyj.iot.entity.IotSimLab;

import java.util.List;

/**
 * 虚拟仿真实验表(IotSimLab)表服务接口
 *
 * @author Xinxuan Zhuo
 * @since 2024-09-09 08:57:03
 */
public interface IotSimLabService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    IotSimLab queryById(Long id);



    /**
     * 新增数据
     *
     * @param iotSimLab 实例对象
     * @return 实例对象
     */
    IotSimLab insert(IotSimLab iotSimLab);

    /**
     * 修改数据
     *
     * @param iotSimLab 实例对象
     * @return 实例对象
     */
    IotSimLab update(IotSimLab iotSimLab);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    List<IotSimLab> queryAllSimLabData();

    Long queryProjectCount();

    IotSimLab queryByProjectUrl(String projectUrl);
}
