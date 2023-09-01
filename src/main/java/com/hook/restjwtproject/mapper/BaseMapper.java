package com.hook.restjwtproject.mapper;

import com.hook.restjwtproject.domain.BaseDomain;
import com.hook.restjwtproject.dto.BaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = BaseMapper.class)
public interface BaseMapper {

    BaseDto convertToDto(BaseDomain baseDomain);

}
