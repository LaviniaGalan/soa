package com.soa.tripmanager.intercomm;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("user-service")
public interface UserClient {
    @RequestMapping(method = RequestMethod.POST, value = "/service/emails", consumes = "application/json")
    List<String> getUserEmails(@RequestBody List<Long> userIdList);
}
