package com.zsyj.auth.application.dto;


import java.io.Serializable;

/**
 * 角色dto
 */
public class AuthRoleDTO implements Serializable {

    private Long id;
    
    private String roleName;
    
    private String roleKey;

    public AuthRoleDTO() {
    }

    public AuthRoleDTO(Long id, String roleName, String roleKey) {
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

