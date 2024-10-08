package com.zsyj.iot.mapper;

import com.zsyj.iot.entity.IotSimLab;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 虚拟仿真实验表(IotSimLab)表数据库访问层
 *
 * @author Xinxuan Zhuo
 * @since 2024-09-09 08:57:01
 */
public interface IotSimLabDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    IotSimLab queryById(Long id);



    /**
     * 统计总行数
     *
     * @param iotSimLab 查询条件
     * @return 总行数
     */
    long count(IotSimLab iotSimLab);

    /**
     * 新增数据
     *
     * @param iotSimLab 实例对象
     * @return 影响行数
     */
    int insert(IotSimLab iotSimLab);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<IotSimLab> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<IotSimLab> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<IotSimLab> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<IotSimLab> entities);

    /**
     * 修改数据
     *
     * @param iotSimLab 实例对象
     * @return 影响行数
     */
    int update(IotSimLab iotSimLab);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    List<IotSimLab> queryAllByLimit();

    IotSimLab queryByProjectUrl(String projectUrl);
}

