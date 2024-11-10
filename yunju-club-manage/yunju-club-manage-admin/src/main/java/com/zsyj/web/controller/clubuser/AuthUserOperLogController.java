package com.zsyj.web.controller.clubuser;

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
import com.zsyj.system.domain.AuthUserOperLog;
import com.zsyj.system.service.IAuthUserOperLogService;
import com.zsyj.common.utils.poi.ExcelUtil;
import com.zsyj.common.core.page.TableDataInfo;

/**
 * 社区C端用户操作日志Controller
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-10
 */
@RestController
@RequestMapping("/system/userLog")
public class AuthUserOperLogController extends BaseController
{
    @Autowired
    private IAuthUserOperLogService authUserOperLogService;

    /**
     * 查询社区C端用户操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:userLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(AuthUserOperLog authUserOperLog)
    {
        startPage();
        List<AuthUserOperLog> list = authUserOperLogService.selectAuthUserOperLogList(authUserOperLog);
        return getDataTable(list);
    }

    /**
     * 导出社区C端用户操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:userLog:export')")
    @Log(title = "社区C端用户操作日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AuthUserOperLog authUserOperLog)
    {
        List<AuthUserOperLog> list = authUserOperLogService.selectAuthUserOperLogList(authUserOperLog);
        ExcelUtil<AuthUserOperLog> util = new ExcelUtil<AuthUserOperLog>(AuthUserOperLog.class);
        util.exportExcel(response, list, "社区C端用户操作日志数据");
    }

    /**
     * 获取社区C端用户操作日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:userLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(authUserOperLogService.selectAuthUserOperLogById(id));
    }

    /**
     * 新增社区C端用户操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:userLog:add')")
    @Log(title = "社区C端用户操作日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AuthUserOperLog authUserOperLog)
    {
        return toAjax(authUserOperLogService.insertAuthUserOperLog(authUserOperLog));
    }

    /**
     * 修改社区C端用户操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:userLog:edit')")
    @Log(title = "社区C端用户操作日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AuthUserOperLog authUserOperLog)
    {
        return toAjax(authUserOperLogService.updateAuthUserOperLog(authUserOperLog));
    }

    /**
     * 删除社区C端用户操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:userLog:remove')")
    @Log(title = "社区C端用户操作日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(authUserOperLogService.deleteAuthUserOperLogByIds(ids));
    }
}
