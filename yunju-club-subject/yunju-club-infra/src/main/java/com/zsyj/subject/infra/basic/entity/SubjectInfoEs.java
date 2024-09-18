package com.zsyj.subject.infra.basic.entity;

import com.zsyj.subject.common.entity.PageInfo;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class SubjectInfoEs extends PageInfo implements Serializable {

    private String keyWord;

    private Long subjectId;

    private Long docId;

    private String subjectName;

    private String subjectAnswer;

    private String createUser;

    private Long createTime;

    private Integer subjectType;

    private BigDecimal score;
}
