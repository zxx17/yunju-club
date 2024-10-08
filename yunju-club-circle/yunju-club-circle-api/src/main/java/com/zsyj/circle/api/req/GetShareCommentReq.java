package com.zsyj.circle.api.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 鸡圈内容信息
 * </p>
 *
 * @author XinxuanZhuo
 * @since 2024/05/16
 */
@Getter
@Setter
public class GetShareCommentReq implements Serializable {

    private Long id;

}
