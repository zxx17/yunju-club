package com.zsyj.circle.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsyj.circle.api.req.RemoveShareCircleReq;
import com.zsyj.circle.api.req.SaveShareCircleReq;
import com.zsyj.circle.api.req.UpdateShareCircleReq;
import com.zsyj.circle.api.vo.ShareCircleVO;
import com.zsyj.circle.server.entity.po.ShareCircle;

import java.util.List;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/8
 */
public interface ShareCircleService extends IService<ShareCircle> {


    List<ShareCircleVO> listResult();

    Boolean saveCircle(SaveShareCircleReq req);

    Boolean updateCircle(UpdateShareCircleReq req);

    Boolean removeCircle(RemoveShareCircleReq req);
}
