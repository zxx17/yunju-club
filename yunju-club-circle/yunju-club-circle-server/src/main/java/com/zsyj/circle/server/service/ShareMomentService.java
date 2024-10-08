package com.zsyj.circle.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsyj.circle.api.common.PageResult;
import com.zsyj.circle.api.req.GetShareMomentReq;
import com.zsyj.circle.api.req.RemoveShareMomentReq;
import com.zsyj.circle.api.req.SaveMomentCircleReq;
import com.zsyj.circle.api.vo.ShareMomentVO;
import com.zsyj.circle.server.entity.po.ShareMoment;

/**
 * <p>
 * 动态信息 服务类
 * </p>
 *
 * @author Xinxuan Zhuo
 * @since 2024/05/16
 */
public interface ShareMomentService extends IService<ShareMoment> {

    Boolean saveMoment(SaveMomentCircleReq req);

    PageResult<ShareMomentVO> getMoments(GetShareMomentReq req);

    Boolean removeMoment(RemoveShareMomentReq req);

    void incrReplyCount(Long id, int count);

}
