package com.zsyj.practice.server.dao;

import com.zsyj.practice.server.entity.po.PracticeSetDetailPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PracticeSetDetailDao {

    /**
     * 新增套题
     */
    int add(PracticeSetDetailPO po);

    /**
     * 根据ID查询套题
     */
    List<PracticeSetDetailPO> selectBySetId(Long setId);

    /**
     * 批量新增套题
     */
    void insertBatch(@Param("detailPOList")List<PracticeSetDetailPO> detailPOList);

}