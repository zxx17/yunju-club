package com.zsyj.web.controller.iot;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.zsyj.system.domain.IotSimLab;
import com.zsyj.system.service.IIotSimLabService;
import com.zsyj.common.utils.poi.ExcelUtil;
import com.zsyj.common.core.page.TableDataInfo;

/**
 * 虚拟仿真实验Controller
 * 
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
@RestController
@RequestMapping("/iotsimlab/lab")
public class IotSimLabController extends BaseController
{
    @Autowired
    private IIotSimLabService iotSimLabService;

    /**
     * 查询虚拟仿真实验列表
     */
    @GetMapping("/list")
    public TableDataInfo list(IotSimLab iotSimLab)
    {
        startPage();
        List<IotSimLab> list = iotSimLabService.selectIotSimLabList(iotSimLab);
        return getDataTable(list);
    }

    /**
     * 导出虚拟仿真实验列表
     */
    @Log(title = "虚拟仿真实验", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IotSimLab iotSimLab)
    {
        List<IotSimLab> list = iotSimLabService.selectIotSimLabList(iotSimLab);
        ExcelUtil<IotSimLab> util = new ExcelUtil<IotSimLab>(IotSimLab.class);
        util.exportExcel(response, list, "虚拟仿真实验数据");
    }

    /**
     * 获取虚拟仿真实验详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(iotSimLabService.selectIotSimLabById(id));
    }

    /**
     * 新增虚拟仿真实验
     */
    @Log(title = "虚拟仿真实验", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IotSimLab iotSimLab)
    {
        return toAjax(iotSimLabService.insertIotSimLab(iotSimLab));
    }

    /**
     * 修改虚拟仿真实验
     */
    @Log(title = "虚拟仿真实验", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IotSimLab iotSimLab)
    {
        return toAjax(iotSimLabService.updateIotSimLab(iotSimLab));
    }

    /**
     * 删除虚拟仿真实验
     */
    @Log(title = "虚拟仿真实验", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(iotSimLabService.deleteIotSimLabByIds(ids));
    }
}
