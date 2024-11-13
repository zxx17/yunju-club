package com.zsyj.subject.api;


import com.zsyj.subject.entity.Result;
import com.zsyj.subject.entity.SubjectCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * subject服务feign
 */
@FeignClient("yunju-club-subject-dev")
public interface SubjectFeignService {

    /**
     * 查询分类大类
     *
     * @return json result List<SubjectCategoryDTO>
     */
    @RequestMapping("/subject/category/queryPrimaryCategory")
    Result<List<SubjectCategoryDTO>> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO);

}
