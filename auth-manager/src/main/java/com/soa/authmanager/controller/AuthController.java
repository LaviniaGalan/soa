package com.soa.authmanager.controller;

import com.soa.authmanager.intercomm.UserClient;
import com.soa.authmanager.security.JwtUtils;
import com.soa.authmanager.security.LoginRequest;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/service/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginData) {
        // Logic to authenticate user and issue JWT token
        ResponseEntity<Map<String, String>> response = userClient.findUser(loginData);
        if(response.getStatusCode().is2xxSuccessful()){
            // generate Jwt Token
            String token = jwtUtils.generateJwtToken(loginData.getEmail());
            // Construct the JSON response body
            Map<String, String> responseBody = response.getBody();
            //Map<String, String> responseBody = new HashMap<>();
            if(responseBody != null) {
                responseBody.put("token", token);
            }
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PostMapping("/service/logout/{email}")
    public ResponseEntity<?> logout(@PathVariable String email) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/service/health")
    public ResponseEntity<?> healthCheck(){
        return new ResponseEntity<>("Hello Auth!", HttpStatus.OK);
    }
}
