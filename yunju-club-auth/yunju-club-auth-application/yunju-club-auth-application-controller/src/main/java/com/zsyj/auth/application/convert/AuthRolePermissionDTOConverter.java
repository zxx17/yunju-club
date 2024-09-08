package com.zsyj.auth.application.convert;

import com.zsyj.auth.application.dto.AuthRolePermissionDTO;
import com.zsyj.auth.domain.entity.AuthRolePermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 权限dto转换器
 */
@Mapper
public interface AuthRolePermissionDTOConverter {

    AuthRolePermissionDTOConverter INSTANCE = Mappers.getMapper(AuthRolePermissionDTOConverter.class);

    AuthRolePermissionBO convertAuthRolePermissionDTOToBO(AuthRolePermissionDTO authRolePermissionDTO);

}
