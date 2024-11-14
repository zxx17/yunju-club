package com.zsyj.system.mapper;

import com.zsyj.system.domain.SensitiveWords;

import java.util.List;

/**
 * 敏感词Mapper接口
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-14
 */
public interface SensitiveWordsMapper 
{
    /**
     * 查询敏感词
     * 
     * @param id 敏感词主键
     * @return 敏感词
     */
    public SensitiveWords selectSensitiveWordsById(Long id);

    /**
     * 查询敏感词列表
     * 
     * @param sensitiveWords 敏感词
     * @return 敏感词集合
     */
    public List<SensitiveWords> selectSensitiveWordsList(SensitiveWords sensitiveWords);

    /**
     * 新增敏感词
     * 
     * @param sensitiveWords 敏感词
     * @return 结果
     */
    public int insertSensitiveWords(SensitiveWords sensitiveWords);

    /**
     * 修改敏感词
     * 
     * @param sensitiveWords 敏感词
     * @return 结果
     */
    public int updateSensitiveWords(SensitiveWords sensitiveWords);

    /**
     * 删除敏感词
     * 
     * @param id 敏感词主键
     * @return 结果
     */
    public int deleteSensitiveWordsById(Long id);

    /**
     * 批量删除敏感词
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSensitiveWordsByIds(Long[] ids);
}
