package com.zsyj.system.service.impl;

import java.util.List;
import com.zsyj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zsyj.system.mapper.ShareCircleMapper;
import com.zsyj.system.domain.ShareCircle;
import com.zsyj.system.service.IShareCircleService;

/**
 * 圈子信息Service业务层处理
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-14
 */
@Service
public class ShareCircleServiceImpl implements IShareCircleService 
{
    @Autowired
    private ShareCircleMapper shareCircleMapper;

    /**
     * 查询圈子信息
     * 
     * @param id 圈子信息主键
     * @return 圈子信息
     */
    @Override
    public ShareCircle selectShareCircleById(Long id)
    {
        return shareCircleMapper.selectShareCircleById(id);
    }

    /**
     * 查询圈子信息列表
     * 
     * @param shareCircle 圈子信息
     * @return 圈子信息
     */
    @Override
    public List<ShareCircle> selectShareCircleList(ShareCircle shareCircle)
    {
        return shareCircleMapper.selectShareCircleList(shareCircle);
    }

    /**
     * 新增圈子信息
     * 
     * @param shareCircle 圈子信息
     * @return 结果
     */
    @Override
    public int insertShareCircle(ShareCircle shareCircle)
    {
        return shareCircleMapper.insertShareCircle(shareCircle);
    }

    /**
     * 修改圈子信息
     * 
     * @param shareCircle 圈子信息
     * @return 结果
     */
    @Override
    public int updateShareCircle(ShareCircle shareCircle)
    {
        shareCircle.setUpdateTime(DateUtils.getNowDate());
        return shareCircleMapper.updateShareCircle(shareCircle);
    }

    /**
     * 批量删除圈子信息
     * 
     * @param ids 需要删除的圈子信息主键
     * @return 结果
     */
    @Override
    public int deleteShareCircleByIds(Long[] ids)
    {
        return shareCircleMapper.deleteShareCircleByIds(ids);
    }

    /**
     * 删除圈子信息信息
     * 
     * @param id 圈子信息主键
     * @return 结果
     */
    @Override
    public int deleteShareCircleById(Long id)
    {
        return shareCircleMapper.deleteShareCircleById(id);
    }
}
