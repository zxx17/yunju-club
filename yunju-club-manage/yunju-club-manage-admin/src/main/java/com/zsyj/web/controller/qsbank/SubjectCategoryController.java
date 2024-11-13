package com.zsyj.web.controller.qsbank;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zsyj.common.utils.poi.ExcelUtil;
import com.zsyj.system.domain.SubjectCategory;
import com.zsyj.system.service.ISubjectCategoryService;
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
import com.zsyj.common.core.page.TableDataInfo;

/**
 * 题目分类Controller
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/qsbank/category")
public class SubjectCategoryController extends BaseController
{
    @Autowired
    private ISubjectCategoryService subjectCategoryService;

    /**
     * 查询题目分类列表
     */
    @PreAuthorize("@ss.hasPermi('qsbank:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(SubjectCategory subjectCategory)
    {
        startPage();
        List<SubjectCategory> list = subjectCategoryService.selectSubjectCategoryList(subjectCategory);
        return getDataTable(list);
    }

    /**
     * 导出题目分类列表
     */
    @PreAuthorize("@ss.hasPermi('qsbank:category:export')")
    @Log(title = "题目分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SubjectCategory subjectCategory)
    {
        List<SubjectCategory> list = subjectCategoryService.selectSubjectCategoryList(subjectCategory);
        ExcelUtil<SubjectCategory> util = new ExcelUtil<SubjectCategory>(SubjectCategory.class);
        util.exportExcel(response, list, "题目分类数据");
    }

    /**
     * 获取题目分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('qsbank:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(subjectCategoryService.selectSubjectCategoryById(id));
    }

    /**
     * 新增题目分类
     */
    @PreAuthorize("@ss.hasPermi('qsbank:category:add')")
    @Log(title = "题目分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SubjectCategory subjectCategory)
    {
        return toAjax(subjectCategoryService.insertSubjectCategory(subjectCategory));
    }

    /**
     * 修改题目分类
     */
    @PreAuthorize("@ss.hasPermi('qsbank:category:edit')")
    @Log(title = "题目分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SubjectCategory subjectCategory)
    {
        return toAjax(subjectCategoryService.updateSubjectCategory(subjectCategory));
    }

    /**
     * 删除题目分类
     */
    @PreAuthorize("@ss.hasPermi('qsbank:category:remove')")
    @Log(title = "题目分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(subjectCategoryService.deleteSubjectCategoryByIds(ids));
    }
}
