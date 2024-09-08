package com.zsyj.auth.domain.service.impl;

import com.zsyj.auth.common.enums.IsDeletedFlagEnum;
import com.zsyj.auth.domain.convert.AuthRoleBOConverter;
import com.zsyj.auth.domain.entity.AuthRoleBO;
import com.zsyj.auth.domain.service.AuthRoleDomainService;
import com.zsyj.auth.infra.basic.entity.AuthRole;
import com.zsyj.auth.infra.basic.service.AuthRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class AuthRoleDomainServiceImpl implements AuthRoleDomainService {

    @Resource
    private AuthRoleService authRoleService;

    @Override
    public Boolean add(AuthRoleBO authRoleBO) {
        AuthRole authRole = AuthRoleBOConverter.INSTANCE.convertBOToEntity(authRoleBO);
        authRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        Date insertAuthRoleDate = new Date();
        authRole.setCreatedTime(insertAuthRoleDate);
        authRole.setUpdateTime(insertAuthRoleDate);
        Integer count = authRoleService.insert(authRole);
        return count > 0;
    }

    @Override
    public Boolean update(AuthRoleBO authRoleBO) {
        AuthRole authRole = AuthRoleBOConverter.INSTANCE.convertBOToEntity(authRoleBO);
        authRole.setUpdateTime(new Date());
        Integer count = authRoleService.update(authRole);
        return count > 0;
    }

    @Override
    public Boolean delete(AuthRoleBO authRoleBO) {
        AuthRole authRole = new AuthRole();
        authRole.setId(authRoleBO.getId());
        authRole.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = authRoleService.update(authRole);
        return count > 0;
    }

}
