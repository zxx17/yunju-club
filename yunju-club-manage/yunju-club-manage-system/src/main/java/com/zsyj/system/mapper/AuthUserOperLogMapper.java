package com.zsyj.system.mapper;

import java.util.List;
import com.zsyj.system.domain.AuthUserOperLog;

/**
 * 社区C端用户操作日志Mapper接口
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-10
 */
public interface AuthUserOperLogMapper 
{
    /**
     * 查询社区C端用户操作日志
     * 
     * @param id 社区C端用户操作日志主键
     * @return 社区C端用户操作日志
     */
    public AuthUserOperLog selectAuthUserOperLogById(Long id);

    /**
     * 查询社区C端用户操作日志列表
     * 
     * @param authUserOperLog 社区C端用户操作日志
     * @return 社区C端用户操作日志集合
     */
    public List<AuthUserOperLog> selectAuthUserOperLogList(AuthUserOperLog authUserOperLog);

    /**
     * 新增社区C端用户操作日志
     * 
     * @param authUserOperLog 社区C端用户操作日志
     * @return 结果
     */
    public int insertAuthUserOperLog(AuthUserOperLog authUserOperLog);

    /**
     * 修改社区C端用户操作日志
     * 
     * @param authUserOperLog 社区C端用户操作日志
     * @return 结果
     */
    public int updateAuthUserOperLog(AuthUserOperLog authUserOperLog);

    /**
     * 删除社区C端用户操作日志
     * 
     * @param id 社区C端用户操作日志主键
     * @return 结果
     */
    public int deleteAuthUserOperLogById(Long id);

    /**
     * 批量删除社区C端用户操作日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAuthUserOperLogByIds(Long[] ids);
}
