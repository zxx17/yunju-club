package com.zsyj.circle.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsyj.circle.api.req.GetShareCommentReq;
import com.zsyj.circle.api.req.RemoveShareCommentReq;
import com.zsyj.circle.api.req.SaveShareCommentReplyReq;
import com.zsyj.circle.api.vo.ShareCommentReplyVO;
import com.zsyj.circle.server.entity.po.ShareCommentReply;

import java.util.List;

/**
 * <p>
 * 评论及回复信息 服务类
 * </p>
 *
 * @author Xinxuan Zhuo
 * @since 2024/05/16
 */
public interface ShareCommentReplyService extends IService<ShareCommentReply> {

    Boolean saveComment(SaveShareCommentReplyReq req);

    Boolean removeComment(RemoveShareCommentReq req);

    List<ShareCommentReplyVO> listComment(GetShareCommentReq req);

}
