package com.soa.usermanager.repository;

import com.soa.usermanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("select u.email from User u where u.id in (:pIdList)")
    List<String> findEmailsByIdList(@Param("pIdList") List<Long> idList);
}
