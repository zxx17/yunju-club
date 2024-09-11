package com.zsyj.iot.controller;

import com.zsyj.iot.entity.IotSimLab;
import com.zsyj.iot.entity.Result;
import com.zsyj.iot.entity.dto.SimLabMenuDTO;
import com.zsyj.iot.service.IotSimLabService;
import com.zsyj.iot.service.IotSimLabUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 虚拟实验室前端控制器.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/9
 */
@Slf4j
@RestController
@RequestMapping("/simLab")
public class IOTSimLabController {

    @Resource
    private IotSimLabService iotSimLabService;

    @Resource
    private IotSimLabUserService iotSimLabUserService;


    /**
     * 左侧虚拟仿真实验树形菜单
     * 一次性查询 组装成树形结构返回给前端，提高效率
     */
    @GetMapping("/menu")
    public Result<List<SimLabMenuDTO>> simLabMenu() {
        try {
            List<IotSimLab> iotSimLabList = iotSimLabService.queryAllSimLabData();
            List<SimLabMenuDTO> res = convertToDTO(iotSimLabList);
            return Result.ok(res);
        } catch (Exception e) {
            log.error("查询虚拟仿真实验数据失败", e);
            return Result.fail("查询失败");
        }
    }


    /**
     * 累计完成实验总数和项目总数
     */
    @GetMapping("/count")
    public Result<Map<String, Long>> count() {
        Map<String, Long> res = new HashMap<>();
        Long finishedCount = iotSimLabUserService.queryFinishedCount();
        Long projectCount = iotSimLabService.queryProjectCount();
        res.put("finishedCount", finishedCount);
        res.put("projectCount", projectCount);
        return Result.ok(res);
    }


    /**
     * 用户实验记录（点击触发，新增，完成是更新） 异步的保存用户操作数据
     */


    /**
     * 将 IotSimLab 转换为 SimLabMenuDTO
     *
     * @param iotSimLabList 虚拟仿真实验室数据 列表
     * @return dto
     */
    private List<SimLabMenuDTO> convertToDTO(List<IotSimLab> iotSimLabList) {
        List<SimLabMenuDTO> simLabMenuDTOList = new ArrayList<>();
        // 推荐的项目组装
        List<IotSimLab> recommendProject = iotSimLabList.stream()
                .filter(lab -> lab.getProjectType() == 1)
                .collect(Collectors.toList());
        SimLabMenuDTO simLabMenuDTOr = new SimLabMenuDTO();
        simLabMenuDTOr.setLabel("推荐的项目(Quick Start)");
        simLabMenuDTOr.setKey("recommend");
        for (IotSimLab iotSimLab : recommendProject) {
            SimLabMenuDTO child = new SimLabMenuDTO();
            child.setLabel(iotSimLab.getProjectName());
            child.setKey(iotSimLab.getProjectUrl());
            child.setProjectUrl(iotSimLab.getProjectUrl());
            child.setProjectDesc(iotSimLab.getProjectDesc());
            simLabMenuDTOr.getChildren().add(child);
        }
        simLabMenuDTOList.add(simLabMenuDTOr);

        // 根据开发板名字组装
        Map<String, SimLabMenuDTO> deviceMap = new HashMap<>();
        for (IotSimLab iotSimLab : iotSimLabList) {
            SimLabMenuDTO deviceChild = deviceMap.computeIfAbsent(iotSimLab.getDeviceName(), k -> {
                SimLabMenuDTO child = new SimLabMenuDTO();
                child.setLabel(k);
                child.setKey(k);
                child.setChildren(new ArrayList<>());
                return child;
            });
            SimLabMenuDTO projectChild = new SimLabMenuDTO();
            projectChild.setKey(iotSimLab.getProjectUrl());
            projectChild.setProjectUrl(iotSimLab.getProjectUrl());
            projectChild.setProjectDesc(iotSimLab.getProjectDesc());
            projectChild.setLabel(iotSimLab.getProjectName());
            deviceChild.getChildren().add(projectChild);
        }
        // 添加设备子项到主 DTO 的子项列表中
        SimLabMenuDTO simLabMenuDTOd = new SimLabMenuDTO();
        simLabMenuDTOd.setLabel("开发板列表");
        simLabMenuDTOd.setKey("device");
        simLabMenuDTOd.setChildren(new ArrayList<>(deviceMap.values()));
        simLabMenuDTOList.add(simLabMenuDTOd);

        return simLabMenuDTOList;
    }

}
