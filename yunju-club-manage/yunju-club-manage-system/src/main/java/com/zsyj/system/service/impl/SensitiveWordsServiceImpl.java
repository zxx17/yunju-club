package com.zsyj.system.service.impl;

import com.zsyj.system.domain.SensitiveWords;
import com.zsyj.system.mapper.SensitiveWordsMapper;
import com.zsyj.system.service.ISensitiveWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 敏感词Service业务层处理
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-14
 */
@Service
public class SensitiveWordsServiceImpl implements ISensitiveWordsService
{
    @Autowired
    private SensitiveWordsMapper sensitiveWordsMapper;

    /**
     * 查询敏感词
     * 
     * @param id 敏感词主键
     * @return 敏感词
     */
    @Override
    public SensitiveWords selectSensitiveWordsById(Long id)
    {
        return sensitiveWordsMapper.selectSensitiveWordsById(id);
    }

    /**
     * 查询敏感词列表
     * 
     * @param sensitiveWords 敏感词
     * @return 敏感词
     */
    @Override
    public List<SensitiveWords> selectSensitiveWordsList(SensitiveWords sensitiveWords)
    {
        return sensitiveWordsMapper.selectSensitiveWordsList(sensitiveWords);
    }

    /**
     * 新增敏感词
     * 
     * @param sensitiveWords 敏感词
     * @return 结果
     */
    @Override
    public int insertSensitiveWords(SensitiveWords sensitiveWords)
    {
        return sensitiveWordsMapper.insertSensitiveWords(sensitiveWords);
    }

    /**
     * 修改敏感词
     * 
     * @param sensitiveWords 敏感词
     * @return 结果
     */
    @Override
    public int updateSensitiveWords(SensitiveWords sensitiveWords)
    {
        return sensitiveWordsMapper.updateSensitiveWords(sensitiveWords);
    }

    /**
     * 批量删除敏感词
     * 
     * @param ids 需要删除的敏感词主键
     * @return 结果
     */
    @Override
    public int deleteSensitiveWordsByIds(Long[] ids)
    {
        return sensitiveWordsMapper.deleteSensitiveWordsByIds(ids);
    }

    /**
     * 删除敏感词信息
     * 
     * @param id 敏感词主键
     * @return 结果
     */
    @Override
    public int deleteSensitiveWordsById(Long id)
    {
        return sensitiveWordsMapper.deleteSensitiveWordsById(id);
    }
}
