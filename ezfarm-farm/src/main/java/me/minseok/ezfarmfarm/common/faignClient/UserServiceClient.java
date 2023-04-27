package me.minseok.ezfarmfarm.common.faignClient;

import me.minseok.ezfarmfarm.vo.user.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${gateway.url}")
public interface UserServiceClient {

    @GetMapping("/users/{userId}")
    ResponseUser getUser(@RequestHeader("Authorization") String token, @PathVariable String userId);
}
