package com.hook.restjwtproject.service.imp;

import com.hook.restjwtproject.dto.UserDto;
import com.hook.restjwtproject.service.AdminService;
import com.hook.restjwtproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserService userService;

    @Override
    public void setAdmin(Long id) {
        userService.setRoleAdmin(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
