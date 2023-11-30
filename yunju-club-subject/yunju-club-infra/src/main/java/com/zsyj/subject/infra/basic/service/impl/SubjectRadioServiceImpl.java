package com.zsyj.subject.infra.basic.service.impl;

import com.zsyj.subject.infra.basic.entity.SubjectRadio;
import com.zsyj.subject.infra.basic.mapper.SubjectRadioDao;
import com.zsyj.subject.infra.basic.service.SubjectRadioService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

/**
 * 单选题表(SubjectRadio)表服务实现类
 *
 * @author makejava
 * @since 2023-11-30 11:38:49
 */
@Service("subjectRadioService")
public class SubjectRadioServiceImpl implements SubjectRadioService {
    @Resource
    private SubjectRadioDao subjectRadioDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectRadio queryById(Integer id) {
        return this.subjectRadioDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param subjectRadio 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectRadio insert(SubjectRadio subjectRadio) {
        this.subjectRadioDao.insert(subjectRadio);
        return subjectRadio;
    }

    /**
     * 修改数据
     *
     * @param subjectRadio 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectRadio update(SubjectRadio subjectRadio) {
        this.subjectRadioDao.update(subjectRadio);
        return this.queryById(subjectRadio.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.subjectRadioDao.deleteById(id) > 0;
    }
}
