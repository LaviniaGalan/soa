package com.soa.authmanager.intercomm;

import com.soa.authmanager.security.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@FeignClient("user-service")
public interface UserClient {
    @RequestMapping(method = RequestMethod.POST, value = "/service/login", consumes = "application/json")
    ResponseEntity<Map<String, String>> findUser(@RequestBody LoginRequest loginData);
}
