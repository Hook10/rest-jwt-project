package com.hook.restjwtproject.service;

import com.hook.restjwtproject.domain.User;
import com.hook.restjwtproject.dto.UserDto;
import com.hook.restjwtproject.dto.UserRegistrationDto;
import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    void save(UserRegistrationDto userRegistrationDto);

    boolean isUserExists(String username);

    void setRoleAdmin(Long id);

    List<UserDto> getAllUsers();
}
