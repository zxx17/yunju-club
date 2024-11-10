package com.zsyj.system.service.impl;

import java.util.List;
import com.zsyj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zsyj.system.mapper.AuthUserOperLogMapper;
import com.zsyj.system.domain.AuthUserOperLog;
import com.zsyj.system.service.IAuthUserOperLogService;

/**
 * 社区C端用户操作日志Service业务层处理
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-10
 */
@Service
public class AuthUserOperLogServiceImpl implements IAuthUserOperLogService 
{
    @Autowired
    private AuthUserOperLogMapper authUserOperLogMapper;

    /**
     * 查询社区C端用户操作日志
     * 
     * @param id 社区C端用户操作日志主键
     * @return 社区C端用户操作日志
     */
    @Override
    public AuthUserOperLog selectAuthUserOperLogById(Long id)
    {
        return authUserOperLogMapper.selectAuthUserOperLogById(id);
    }

    /**
     * 查询社区C端用户操作日志列表
     * 
     * @param authUserOperLog 社区C端用户操作日志
     * @return 社区C端用户操作日志
     */
    @Override
    public List<AuthUserOperLog> selectAuthUserOperLogList(AuthUserOperLog authUserOperLog)
    {
        return authUserOperLogMapper.selectAuthUserOperLogList(authUserOperLog);
    }

    /**
     * 新增社区C端用户操作日志
     * 
     * @param authUserOperLog 社区C端用户操作日志
     * @return 结果
     */
    @Override
    public int insertAuthUserOperLog(AuthUserOperLog authUserOperLog)
    {
        return authUserOperLogMapper.insertAuthUserOperLog(authUserOperLog);
    }

    /**
     * 修改社区C端用户操作日志
     * 
     * @param authUserOperLog 社区C端用户操作日志
     * @return 结果
     */
    @Override
    public int updateAuthUserOperLog(AuthUserOperLog authUserOperLog)
    {
        authUserOperLog.setUpdateTime(DateUtils.getNowDate());
        return authUserOperLogMapper.updateAuthUserOperLog(authUserOperLog);
    }

    /**
     * 批量删除社区C端用户操作日志
     * 
     * @param ids 需要删除的社区C端用户操作日志主键
     * @return 结果
     */
    @Override
    public int deleteAuthUserOperLogByIds(Long[] ids)
    {
        return authUserOperLogMapper.deleteAuthUserOperLogByIds(ids);
    }

    /**
     * 删除社区C端用户操作日志信息
     * 
     * @param id 社区C端用户操作日志主键
     * @return 结果
     */
    @Override
    public int deleteAuthUserOperLogById(Long id)
    {
        return authUserOperLogMapper.deleteAuthUserOperLogById(id);
    }
}
