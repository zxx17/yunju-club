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
import com.zsyj.system.domain.AuthUser;
import com.zsyj.system.service.IAuthUserService;
import com.zsyj.common.utils.poi.ExcelUtil;
import com.zsyj.common.core.page.TableDataInfo;

/**
 * 用户信息Controller
 * 
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
@RestController
@RequestMapping("/system/club-user")
public class AuthUserController extends BaseController
{
    @Autowired
    private IAuthUserService authUserService;

    /**
     * 查询用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(AuthUser authUser)
    {
        startPage();
        List<AuthUser> list = authUserService.selectAuthUserList(authUser);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AuthUser authUser)
    {
        List<AuthUser> list = authUserService.selectAuthUserList(authUser);
        ExcelUtil<AuthUser> util = new ExcelUtil<AuthUser>(AuthUser.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(authUserService.selectAuthUserById(id));
    }

    /**
     * 新增用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AuthUser authUser)
    {
        return toAjax(authUserService.insertAuthUser(authUser));
    }

    /**
     * 修改用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AuthUser authUser)
    {
        return toAjax(authUserService.updateAuthUser(authUser));
    }

    /**
     * 删除用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(authUserService.deleteAuthUserByIds(ids));
    }
}
