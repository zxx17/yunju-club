package com.zsyj.web.controller.circle;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zsyj.system.domain.SensitiveWords;
import com.zsyj.system.service.ISensitiveWordsService;
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
import com.zsyj.common.utils.poi.ExcelUtil;
import com.zsyj.common.core.page.TableDataInfo;

/**
 * 敏感词Controller
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-14
 */
@RestController
@RequestMapping("/circle/sensitive")
public class SensitiveWordsController extends BaseController
{
    @Autowired
    private ISensitiveWordsService sensitiveWordsService;

    /**
     * 查询敏感词列表
     */
    @PreAuthorize("@ss.hasPermi('circle:sensitive:list')")
    @GetMapping("/list")
    public TableDataInfo list(SensitiveWords sensitiveWords)
    {
        startPage();
        List<SensitiveWords> list = sensitiveWordsService.selectSensitiveWordsList(sensitiveWords);
        return getDataTable(list);
    }

    /**
     * 导出敏感词列表
     */
    @PreAuthorize("@ss.hasPermi('circle:sensitive:export')")
    @Log(title = "敏感词", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SensitiveWords sensitiveWords)
    {
        List<SensitiveWords> list = sensitiveWordsService.selectSensitiveWordsList(sensitiveWords);
        ExcelUtil<SensitiveWords> util = new ExcelUtil<SensitiveWords>(SensitiveWords.class);
        util.exportExcel(response, list, "敏感词数据");
    }

    /**
     * 获取敏感词详细信息
     */
    @PreAuthorize("@ss.hasPermi('circle:sensitive:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sensitiveWordsService.selectSensitiveWordsById(id));
    }

    /**
     * 新增敏感词
     */
    @PreAuthorize("@ss.hasPermi('circle:sensitive:add')")
    @Log(title = "敏感词", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SensitiveWords sensitiveWords)
    {
        return toAjax(sensitiveWordsService.insertSensitiveWords(sensitiveWords));
    }

    /**
     * 修改敏感词
     */
    @PreAuthorize("@ss.hasPermi('circle:sensitive:edit')")
    @Log(title = "敏感词", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SensitiveWords sensitiveWords)
    {
        return toAjax(sensitiveWordsService.updateSensitiveWords(sensitiveWords));
    }

    /**
     * 删除敏感词
     */
    @PreAuthorize("@ss.hasPermi('circle:sensitive:remove')")
    @Log(title = "敏感词", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sensitiveWordsService.deleteSensitiveWordsByIds(ids));
    }
}
