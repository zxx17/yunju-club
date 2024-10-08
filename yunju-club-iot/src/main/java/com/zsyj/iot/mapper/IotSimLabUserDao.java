package com.zsyj.iot.mapper;


import com.zsyj.iot.entity.IotSimLabUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 虚拟仿真实验用户记录表(IotSimLabUser)表数据库访问层
 *
 * @author Xinxuan Zhuo
 * @since 2024-09-09 08:57:04
 */
public interface IotSimLabUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    IotSimLabUser queryById(Long id);


    /**
     * 统计总行数
     *
     * @param iotSimLabUser 查询条件
     * @return 总行数
     */
    long count(IotSimLabUser iotSimLabUser);

    /**
     * 新增数据
     *
     * @param iotSimLabUser 实例对象
     * @return 影响行数
     */
    int insert(IotSimLabUser iotSimLabUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<IotSimLabUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<IotSimLabUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<IotSimLabUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<IotSimLabUser> entities);

    /**
     * 修改数据
     *
     * @param iotSimLabUser 实例对象
     * @return 影响行数
     */
    int update(IotSimLabUser iotSimLabUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    void insertOrUpdate(IotSimLabUser iotSimLabUser);
}

