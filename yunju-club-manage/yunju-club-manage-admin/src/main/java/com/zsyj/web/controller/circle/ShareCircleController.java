package com.zsyj.web.controller.circle;

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
import com.zsyj.system.domain.ShareCircle;
import com.zsyj.system.service.IShareCircleService;
import com.zsyj.common.utils.poi.ExcelUtil;
import com.zsyj.common.core.page.TableDataInfo;

/**
 * 圈子信息Controller
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-14
 */
@RestController
@RequestMapping("/circle/topic")
public class ShareCircleController extends BaseController
{
    @Autowired
    private IShareCircleService shareCircleService;

    /**
     * 查询圈子信息列表
     */
    @PreAuthorize("@ss.hasPermi('circle:topic:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShareCircle shareCircle)
    {
        startPage();
        List<ShareCircle> list = shareCircleService.selectShareCircleList(shareCircle);
        return getDataTable(list);
    }

    /**
     * 导出圈子信息列表
     */
    @PreAuthorize("@ss.hasPermi('circle:topic:export')")
    @Log(title = "圈子信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShareCircle shareCircle)
    {
        List<ShareCircle> list = shareCircleService.selectShareCircleList(shareCircle);
        ExcelUtil<ShareCircle> util = new ExcelUtil<ShareCircle>(ShareCircle.class);
        util.exportExcel(response, list, "圈子信息数据");
    }

    /**
     * 获取圈子信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('circle:topic:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shareCircleService.selectShareCircleById(id));
    }

    /**
     * 新增圈子信息
     */
    @PreAuthorize("@ss.hasPermi('circle:topic:add')")
    @Log(title = "圈子信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShareCircle shareCircle)
    {
        return toAjax(shareCircleService.insertShareCircle(shareCircle));
    }

    /**
     * 修改圈子信息
     */
    @PreAuthorize("@ss.hasPermi('circle:topic:edit')")
    @Log(title = "圈子信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShareCircle shareCircle)
    {
        return toAjax(shareCircleService.updateShareCircle(shareCircle));
    }

    /**
     * 删除圈子信息
     */
    @PreAuthorize("@ss.hasPermi('circle:topic:remove')")
    @Log(title = "圈子信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shareCircleService.deleteShareCircleByIds(ids));
    }
}
