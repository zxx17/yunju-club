package com.zsyj.auth.domain.service;

import com.zsyj.auth.domain.entity.AuthRoleBO;

/**
 * 角色领域service
 */
public interface AuthRoleDomainService {

    Boolean add(AuthRoleBO authRoleBO);

    Boolean update(AuthRoleBO authRoleBO);

    Boolean delete(AuthRoleBO authRoleBO);

}
