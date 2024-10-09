package com.zsyj.system.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zsyj.system.domain.IotSimLabUser;

/**
 * 虚拟仿真实验用户记录Service接口
 * 
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
public interface IIotSimLabUserService 
{
    /**
     * 查询虚拟仿真实验用户记录
     * 
     * @param id 虚拟仿真实验用户记录主键
     * @return 虚拟仿真实验用户记录
     */
    public IotSimLabUser selectIotSimLabUserById(Long id);

    /**
     * 查询虚拟仿真实验用户记录列表
     * 
     * @param iotSimLabUser 虚拟仿真实验用户记录
     * @return 虚拟仿真实验用户记录集合
     */
    public List<IotSimLabUser> selectIotSimLabUserList(IotSimLabUser iotSimLabUser);

    /**
     * 新增虚拟仿真实验用户记录
     * 
     * @param iotSimLabUser 虚拟仿真实验用户记录
     * @return 结果
     */
    public int insertIotSimLabUser(IotSimLabUser iotSimLabUser);

    /**
     * 修改虚拟仿真实验用户记录
     * 
     * @param iotSimLabUser 虚拟仿真实验用户记录
     * @return 结果
     */
    public int updateIotSimLabUser(IotSimLabUser iotSimLabUser);

    /**
     * 批量删除虚拟仿真实验用户记录
     * 
     * @param ids 需要删除的虚拟仿真实验用户记录主键集合
     * @return 结果
     */
    public int deleteIotSimLabUserByIds(Long[] ids);

    /**
     * 删除虚拟仿真实验用户记录信息
     * 
     * @param id 虚拟仿真实验用户记录主键
     * @return 结果
     */
    public int deleteIotSimLabUserById(Long id);

    List<HashMap<String, BigDecimal>> userSimLabWeekData();

    Map<String, List<?>> simLabFinishData();
}
