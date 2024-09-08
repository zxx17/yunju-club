package com.zsyj.auth.application.dto;


import java.io.Serializable;
import java.util.List;

/**
 * (AuthRolePermission)实体类
 */
public class AuthRolePermissionDTO implements Serializable {

    private Long id;
    
    private Long roleId;
    
    private Long permissionId;

    private List<Long> permissionIdList;

    public AuthRolePermissionDTO(Long id, Long roleId, Long permissionId, List<Long> permissionIdList) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.permissionIdList = permissionIdList;
    }

    public AuthRolePermissionDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public List<Long> getPermissionIdList() {
        return permissionIdList;
    }

    public void setPermissionIdList(List<Long> permissionIdList) {
        this.permissionIdList = permissionIdList;
    }
}

