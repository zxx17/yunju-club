package com.zsyj.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zsyj.common.annotation.Excel;
import com.zsyj.common.core.domain.BaseEntity;

/**
 * 敏感词对象 sensitive_words
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-14
 */
public class SensitiveWords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 内容 */
    @Excel(name = "内容")
    private String words;

    /** 1=黑名单 2=白名单 */
    @Excel(name = "1=黑名单 2=白名单")
    private Long type;

    /** 是否被删除 0为删除 1已删除 */
    @Excel(name = "是否被删除 0为删除 1已删除")
    private Long isDeleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWords(String words) 
    {
        this.words = words;
    }

    public String getWords() 
    {
        return words;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setIsDeleted(Long isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("words", getWords())
            .append("type", getType())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
