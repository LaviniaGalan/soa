package com.soa.usermanager.controller;

import com.soa.usermanager.model.User;
import com.soa.usermanager.security.LoginRequest;
import com.soa.usermanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/health")
    public ResponseEntity<?> healthCheck(){
        return new ResponseEntity<>("Hello!", HttpStatus.OK);
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances(){
        return new ResponseEntity<>(discoveryClient.getInstances(serviceId), HttpStatus.OK);
    }

    @GetMapping("/service/services")
    public ResponseEntity<?> getServices(){
        return new ResponseEntity<>(discoveryClient.getServices(), HttpStatus.OK);
    }

    @PostMapping("/service/login")
    public ResponseEntity<?> getUser(@RequestBody LoginRequest loginData){
        if(loginData == null || loginData.getEmail() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userService.findByEmail(loginData.getEmail()));
    }

    @PostMapping("/service/signup")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        if(userService.findByEmail(user.getEmail()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setType("client");
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/service/emails")
    public ResponseEntity<?> getEmailsOfUsers(@RequestBody List<Long> idList){
        return ResponseEntity.ok(userService.findUsersById(idList));
    }
}
