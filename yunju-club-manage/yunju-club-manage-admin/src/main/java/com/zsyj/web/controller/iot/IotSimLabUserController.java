package com.zsyj.web.controller.iot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSON;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zsyj.common.annotation.Log;
import com.zsyj.common.core.controller.BaseController;
import com.zsyj.common.core.domain.AjaxResult;
import com.zsyj.common.enums.BusinessType;
import com.zsyj.system.domain.IotSimLabUser;
import com.zsyj.system.service.IIotSimLabUserService;
import com.zsyj.common.utils.poi.ExcelUtil;
import com.zsyj.common.core.page.TableDataInfo;

/**
 * 虚拟仿真实验用户记录Controller
 *
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
@RestController
@RequestMapping("/iotsimlab/lab/finish")
public class IotSimLabUserController extends BaseController {
    @Autowired
    private IIotSimLabUserService iotSimLabUserService;

    /**
     * 查询虚拟仿真实验用户记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(IotSimLabUser iotSimLabUser) {
        startPage();
        List<IotSimLabUser> list = iotSimLabUserService.selectIotSimLabUserList(iotSimLabUser);
        return getDataTable(list);
    }

    /**
     * 导出虚拟仿真实验用户记录列表
     */
    @Log(title = "虚拟仿真实验用户记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IotSimLabUser iotSimLabUser) {
        List<IotSimLabUser> list = iotSimLabUserService.selectIotSimLabUserList(iotSimLabUser);
        ExcelUtil<IotSimLabUser> util = new ExcelUtil<IotSimLabUser>(IotSimLabUser.class);
        util.exportExcel(response, list, "虚拟仿真实验用户记录数据");
    }

    /**
     * 获取虚拟仿真实验用户记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(iotSimLabUserService.selectIotSimLabUserById(id));
    }

    /**
     * 新增虚拟仿真实验用户记录
     */
    @Log(title = "虚拟仿真实验用户记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IotSimLabUser iotSimLabUser) {
        return toAjax(iotSimLabUserService.insertIotSimLabUser(iotSimLabUser));
    }

    /**
     * 修改虚拟仿真实验用户记录
     */
    @Log(title = "虚拟仿真实验用户记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IotSimLabUser iotSimLabUser) {
        return toAjax(iotSimLabUserService.updateIotSimLabUser(iotSimLabUser));
    }

    /**
     * 删除虚拟仿真实验用户记录
     */
    @Log(title = "虚拟仿真实验用户记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iotSimLabUserService.deleteIotSimLabUserByIds(ids));
    }

    /**
     * 近一周模拟实验完成情况
     * TODO 后续做成可选周的图表，优化SQL
     */
    @RequestMapping("/simLabWeekData")
    public TableDataInfo userSimLabWeekData() {
        List<BigDecimal> resultList = new ArrayList<>();
        List<HashMap<String, BigDecimal>> list = iotSimLabUserService.userSimLabWeekData();
        HashMap<String, BigDecimal> map = list.get(0);
        map.forEach((k, v) -> resultList.add(v));
        return getDataTable(resultList);
    }

    /**
     * 虚拟仿真实验完成数量
     */
    @GetMapping("/simLabFinishData")
    public Map<String, List<?>> simLabFinishData() {
        Map<String, List<?>> map = iotSimLabUserService.simLabFinishData();
        if (logger.isInfoEnabled()){
            logger.info("查询虚拟仿真实验完成数量响应:{}", JSON.toJSONString(map));
        }
        return map;
    }

}
