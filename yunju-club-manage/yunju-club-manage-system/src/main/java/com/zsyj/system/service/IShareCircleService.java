package com.zsyj.system.service;

import java.util.List;
import com.zsyj.system.domain.ShareCircle;

/**
 * 圈子信息Service接口
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-14
 */
public interface IShareCircleService 
{
    /**
     * 查询圈子信息
     * 
     * @param id 圈子信息主键
     * @return 圈子信息
     */
    public ShareCircle selectShareCircleById(Long id);

    /**
     * 查询圈子信息列表
     * 
     * @param shareCircle 圈子信息
     * @return 圈子信息集合
     */
    public List<ShareCircle> selectShareCircleList(ShareCircle shareCircle);

    /**
     * 新增圈子信息
     * 
     * @param shareCircle 圈子信息
     * @return 结果
     */
    public int insertShareCircle(ShareCircle shareCircle);

    /**
     * 修改圈子信息
     * 
     * @param shareCircle 圈子信息
     * @return 结果
     */
    public int updateShareCircle(ShareCircle shareCircle);

    /**
     * 批量删除圈子信息
     * 
     * @param ids 需要删除的圈子信息主键集合
     * @return 结果
     */
    public int deleteShareCircleByIds(Long[] ids);

    /**
     * 删除圈子信息信息
     * 
     * @param id 圈子信息主键
     * @return 结果
     */
    public int deleteShareCircleById(Long id);
}
