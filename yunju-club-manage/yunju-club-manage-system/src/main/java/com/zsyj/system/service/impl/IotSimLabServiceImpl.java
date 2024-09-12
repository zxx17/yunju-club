package com.zsyj.system.service.impl;

import java.util.List;
import com.zsyj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zsyj.system.mapper.IotSimLabMapper;
import com.zsyj.system.domain.IotSimLab;
import com.zsyj.system.service.IIotSimLabService;

/**
 * 虚拟仿真实验Service业务层处理
 * 
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
@Service
public class IotSimLabServiceImpl implements IIotSimLabService 
{
    @Autowired
    private IotSimLabMapper iotSimLabMapper;

    /**
     * 查询虚拟仿真实验
     * 
     * @param id 虚拟仿真实验主键
     * @return 虚拟仿真实验
     */
    @Override
    public IotSimLab selectIotSimLabById(Long id)
    {
        return iotSimLabMapper.selectIotSimLabById(id);
    }

    /**
     * 查询虚拟仿真实验列表
     * 
     * @param iotSimLab 虚拟仿真实验
     * @return 虚拟仿真实验
     */
    @Override
    public List<IotSimLab> selectIotSimLabList(IotSimLab iotSimLab)
    {
        return iotSimLabMapper.selectIotSimLabList(iotSimLab);
    }

    /**
     * 新增虚拟仿真实验
     * 
     * @param iotSimLab 虚拟仿真实验
     * @return 结果
     */
    @Override
    public int insertIotSimLab(IotSimLab iotSimLab)
    {
        return iotSimLabMapper.insertIotSimLab(iotSimLab);
    }

    /**
     * 修改虚拟仿真实验
     * 
     * @param iotSimLab 虚拟仿真实验
     * @return 结果
     */
    @Override
    public int updateIotSimLab(IotSimLab iotSimLab)
    {
        iotSimLab.setUpdateTime(DateUtils.getNowDate());
        return iotSimLabMapper.updateIotSimLab(iotSimLab);
    }

    /**
     * 批量删除虚拟仿真实验
     * 
     * @param ids 需要删除的虚拟仿真实验主键
     * @return 结果
     */
    @Override
    public int deleteIotSimLabByIds(Long[] ids)
    {
        return iotSimLabMapper.deleteIotSimLabByIds(ids);
    }

    /**
     * 删除虚拟仿真实验信息
     * 
     * @param id 虚拟仿真实验主键
     * @return 结果
     */
    @Override
    public int deleteIotSimLabById(Long id)
    {
        return iotSimLabMapper.deleteIotSimLabById(id);
    }
}
