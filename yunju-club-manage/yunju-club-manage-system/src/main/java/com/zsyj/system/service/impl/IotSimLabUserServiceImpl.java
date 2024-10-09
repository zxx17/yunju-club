package com.zsyj.system.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.zsyj.common.utils.DateUtils;
import com.zsyj.system.domain.IotSimLab;
import com.zsyj.system.mapper.IotSimLabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zsyj.system.mapper.IotSimLabUserMapper;
import com.zsyj.system.domain.IotSimLabUser;
import com.zsyj.system.service.IIotSimLabUserService;

/**
 * 虚拟仿真实验用户记录Service业务层处理
 *
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
@Service
public class IotSimLabUserServiceImpl implements IIotSimLabUserService {
    @Autowired
    private IotSimLabUserMapper iotSimLabUserMapper;

    @Autowired
    private IotSimLabMapper iotSimLabMapper;

    /**
     * 查询虚拟仿真实验用户记录
     *
     * @param id 虚拟仿真实验用户记录主键
     * @return 虚拟仿真实验用户记录
     */
    @Override
    public IotSimLabUser selectIotSimLabUserById(Long id) {
        return iotSimLabUserMapper.selectIotSimLabUserById(id);
    }

    /**
     * 查询虚拟仿真实验用户记录列表
     *
     * @param iotSimLabUser 虚拟仿真实验用户记录
     * @return 虚拟仿真实验用户记录
     */
    @Override
    public List<IotSimLabUser> selectIotSimLabUserList(IotSimLabUser iotSimLabUser) {
        return iotSimLabUserMapper.selectIotSimLabUserList(iotSimLabUser);
    }

    /**
     * 新增虚拟仿真实验用户记录
     *
     * @param iotSimLabUser 虚拟仿真实验用户记录
     * @return 结果
     */
    @Override
    public int insertIotSimLabUser(IotSimLabUser iotSimLabUser) {
        return iotSimLabUserMapper.insertIotSimLabUser(iotSimLabUser);
    }

    /**
     * 修改虚拟仿真实验用户记录
     *
     * @param iotSimLabUser 虚拟仿真实验用户记录
     * @return 结果
     */
    @Override
    public int updateIotSimLabUser(IotSimLabUser iotSimLabUser) {
        iotSimLabUser.setUpdateTime(DateUtils.getNowDate());
        return iotSimLabUserMapper.updateIotSimLabUser(iotSimLabUser);
    }

    /**
     * 批量删除虚拟仿真实验用户记录
     *
     * @param ids 需要删除的虚拟仿真实验用户记录主键
     * @return 结果
     */
    @Override
    public int deleteIotSimLabUserByIds(Long[] ids) {
        return iotSimLabUserMapper.deleteIotSimLabUserByIds(ids);
    }

    /**
     * 删除虚拟仿真实验用户记录信息
     *
     * @param id 虚拟仿真实验用户记录主键
     * @return 结果
     */
    @Override
    public int deleteIotSimLabUserById(Long id) {
        return iotSimLabUserMapper.deleteIotSimLabUserById(id);
    }

    @Override
    public List<HashMap<String, BigDecimal>> userSimLabWeekData() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.minusDays(now.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return iotSimLabUserMapper.userSimLabWeekData(startOfWeek, endOfWeek);
    }

    @Override
    public Map<String, List<?>> simLabFinishData() {
        Map<String, List<?>> resMap = new HashMap<>(8);
        List<IotSimLab> dto = iotSimLabMapper.simLabFinishData();

        // 处理 xAxis 数据
        List<String> xList = dto.stream()
                .map(IotSimLab::getProjectName)
                .collect(Collectors.toList());
        resMap.put("xAxis", xList);

        // 处理 yAxis 数据，并将 null 转换为 0
        List<Long> yList = dto.stream()
                .map(iotSimLab -> Optional.ofNullable(iotSimLab.getFinishedCount()).orElse(0L))
                .collect(Collectors.toList());
        resMap.put("yAxis", yList);

        return resMap;
    }

}
