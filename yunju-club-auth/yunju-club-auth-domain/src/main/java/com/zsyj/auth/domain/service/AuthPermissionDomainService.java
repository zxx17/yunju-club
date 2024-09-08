package com.zsyj.auth.domain.service;

import com.zsyj.auth.domain.entity.AuthPermissionBO;

import java.util.List;

/**
 * 角色领域service
 */
public interface AuthPermissionDomainService {

    Boolean add(AuthPermissionBO authPermissionBO);

    Boolean update(AuthPermissionBO authPermissionBO);

    Boolean delete(AuthPermissionBO authPermissionBO);

    List<String> getPermission(String userName);
}
