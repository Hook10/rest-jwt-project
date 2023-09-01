package com.hook.restjwtproject.service;

import com.hook.restjwtproject.dto.UserDto;
import java.util.List;

public interface AdminService {

    void setAdmin(Long id);
    List<UserDto> getAllUsers();
}
