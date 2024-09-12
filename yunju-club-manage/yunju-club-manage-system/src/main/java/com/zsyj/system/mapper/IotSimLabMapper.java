package com.zsyj.system.mapper;

import java.util.List;
import com.zsyj.system.domain.IotSimLab;

/**
 * 虚拟仿真实验Mapper接口
 * 
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
public interface IotSimLabMapper 
{
    /**
     * 查询虚拟仿真实验
     * 
     * @param id 虚拟仿真实验主键
     * @return 虚拟仿真实验
     */
    public IotSimLab selectIotSimLabById(Long id);

    /**
     * 查询虚拟仿真实验列表
     * 
     * @param iotSimLab 虚拟仿真实验
     * @return 虚拟仿真实验集合
     */
    public List<IotSimLab> selectIotSimLabList(IotSimLab iotSimLab);

    /**
     * 新增虚拟仿真实验
     * 
     * @param iotSimLab 虚拟仿真实验
     * @return 结果
     */
    public int insertIotSimLab(IotSimLab iotSimLab);

    /**
     * 修改虚拟仿真实验
     * 
     * @param iotSimLab 虚拟仿真实验
     * @return 结果
     */
    public int updateIotSimLab(IotSimLab iotSimLab);

    /**
     * 删除虚拟仿真实验
     * 
     * @param id 虚拟仿真实验主键
     * @return 结果
     */
    public int deleteIotSimLabById(Long id);

    /**
     * 批量删除虚拟仿真实验
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIotSimLabByIds(Long[] ids);
}
