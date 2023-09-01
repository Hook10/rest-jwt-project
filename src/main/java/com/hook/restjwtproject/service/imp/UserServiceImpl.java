package com.hook.restjwtproject.service.imp;

import static com.hook.restjwtproject.utils.Constants.ENTITY_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.hook.restjwtproject.utils.Constants.ROLE_ADMIN;
import static com.hook.restjwtproject.utils.Constants.ROLE_USER;
import static com.hook.restjwtproject.utils.Constants.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.hook.restjwtproject.utils.ResponseUtils.DATA_SOURCE_LOOKUP_FAILURE_EXCEPTION_MESSAGE;

import com.hook.restjwtproject.domain.Role;
import com.hook.restjwtproject.domain.User;
import com.hook.restjwtproject.dto.UserDto;
import com.hook.restjwtproject.dto.UserRegistrationDto;
import com.hook.restjwtproject.mapper.UserMapper;
import com.hook.restjwtproject.repository.RoleRepository;
import com.hook.restjwtproject.repository.UserRepository;
import com.hook.restjwtproject.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public void save(UserRegistrationDto userRegistrationDto) {
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        User user = userMapper.convertToDomain(userRegistrationDto);
        Optional<Role> optionalRole = roleRepository.findByName(ROLE_USER);
        if (optionalRole.isPresent()) {
            user.setRole(optionalRole.get());
            userRepository.save(user);
        } else {
            throw new DataSourceLookupFailureException(DATA_SOURCE_LOOKUP_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public boolean isUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void setRoleAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE));
        Role roleAdmin = roleRepository.findByName(ROLE_ADMIN).orElseThrow(() -> new DataSourceLookupFailureException(DATA_SOURCE_LOOKUP_FAILURE_EXCEPTION_MESSAGE));
        user.setRole(roleAdmin);
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::convertToDto).toList();
    }
}
