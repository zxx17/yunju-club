package com.zsyj.practice.server.service;

import com.zsyj.practice.api.vo.SpecialPracticeVO;

import java.util.List;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/23
 */
public interface PracticeSetService {

    /**
     * 获取专项练习内容
     * 每个一级大类是一个专项练习
     */
    List<SpecialPracticeVO> getSpecialPracticeContent();
}
