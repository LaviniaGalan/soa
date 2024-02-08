package com.soa.usermanager.service;

import com.soa.usermanager.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User findByEmail(String email);
    List<String> findUsersById(List<Long> userIds);
}
