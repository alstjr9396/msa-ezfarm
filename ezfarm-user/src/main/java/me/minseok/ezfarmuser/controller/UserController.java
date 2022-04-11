package me.minseok.ezfarmuser.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.minseok.ezfarmuser.domain.User;
import me.minseok.ezfarmuser.domain.dto.UserDto;
import me.minseok.ezfarmuser.service.UserService;
import me.minseok.ezfarmuser.vo.request.RequestUser;
import me.minseok.ezfarmuser.vo.response.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  @PostMapping("/users")
  public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser) {
    ResponseUser responseUser = userService.createUser(requestUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<ResponseUser> getUser(@PathVariable String userId) {
    ResponseUser responseUser = userService.getUserById(userId);

    return ResponseEntity.status(HttpStatus.OK).body(responseUser);
  }
}
