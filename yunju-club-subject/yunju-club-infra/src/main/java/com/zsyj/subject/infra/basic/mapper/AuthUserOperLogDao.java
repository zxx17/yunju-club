package com.zsyj.subject.infra.basic.mapper;

import com.zsyj.subject.infra.basic.entity.AuthUserOperLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * C端用户操作表(AuthUserOperLog)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-18 21:03:33
 */
public interface AuthUserOperLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthUserOperLog queryById(Long id);
    

    /**
     * 统计总行数
     *
     * @param authUserOperLog 查询条件
     * @return 总行数
     */
    long count(AuthUserOperLog authUserOperLog);

    /**
     * 新增数据
     *
     * @param authUserOperLog 实例对象
     * @return 影响行数
     */
    int insert(AuthUserOperLog authUserOperLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AuthUserOperLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AuthUserOperLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AuthUserOperLog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AuthUserOperLog> entities);

    /**
     * 修改数据
     *
     * @param authUserOperLog 实例对象
     * @return 影响行数
     */
    int update(AuthUserOperLog authUserOperLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    List<AuthUserOperLog> queryByCondition(AuthUserOperLog authUserOperLog);
}

