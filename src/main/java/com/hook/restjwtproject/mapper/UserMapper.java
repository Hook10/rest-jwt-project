package com.hook.restjwtproject.mapper;

import com.hook.restjwtproject.domain.User;
import com.hook.restjwtproject.dto.UserDto;
import com.hook.restjwtproject.dto.UserRegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User convertToDomain(UserRegistrationDto userRegistrationDto);

    @Mapping(target = "role", source = "user.role.name")
    UserDto convertToDto(User user);
}
