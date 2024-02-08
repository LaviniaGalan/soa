package com.soa.usermanager.service;


import com.soa.usermanager.model.User;
import com.soa.usermanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<String> findUsersById(List<Long> userIds) {
        return userRepository.findEmailsByIdList(userIds);
    }
}
