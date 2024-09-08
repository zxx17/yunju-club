package com.zsyj.auth.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色bo
 */
public class AuthRoleBO implements Serializable {

    private Long id;
    
    private String roleName;
    
    private String roleKey;

    public AuthRoleBO() {
    }



    public AuthRoleBO(Long id, String roleName, String roleKey) {
        this.id = id;
        this.roleName = roleName;
        this.roleKey = roleKey;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }
}

