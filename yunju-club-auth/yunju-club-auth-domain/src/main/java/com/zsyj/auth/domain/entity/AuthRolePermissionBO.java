package com.zsyj.auth.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (AuthRolePermission)实体类
 */
@Data
public class AuthRolePermissionBO implements Serializable {

    private Long id;
    
    private Long roleId;
    
    private Long permissionId;

    private List<Long> permissionIdList;
}

