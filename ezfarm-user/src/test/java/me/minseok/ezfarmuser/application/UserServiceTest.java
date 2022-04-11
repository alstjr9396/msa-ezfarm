package me.minseok.ezfarmuser.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import me.minseok.ezfarmuser.domain.User;
import me.minseok.ezfarmuser.repository.UserRepository;
import me.minseok.ezfarmuser.service.UserService;
import me.minseok.ezfarmuser.vo.request.RequestUser;
import me.minseok.ezfarmuser.vo.response.ResponseUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("유저 서비스 테스트")
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private ModelMapper modelMapper;

  private UserService userService;

  private User user;

  @BeforeEach
  void before() {
    userService = new UserService(userRepository, passwordEncoder, modelMapper);
    user = User.builder()
        .userId("uuid-123")
        .name("minseok kang")
        .email("email@google.com")
        .encryptedPwd("test1234")
        .build();
  }

  @DisplayName("회원가입 한다.")
  @Test
  void createUser() {
    RequestUser requestUser = RequestUser.builder()
        .email("email@google.com")
        .name("minseok kang")
        .pwd("test1234")
        .build();

    ResponseUser responseUser = ResponseUser.builder()
        .email("email@google.com")
        .name("minseok kang")
        .userId("uuid-123")
        .build();

    given(userRepository.existsByEmail(any())).willReturn(false);
    given(passwordEncoder.encode(any())).willReturn("test1234");
    given(userRepository.save(any())).willReturn(user);
    given(modelMapper.map(any(), any())).willReturn(responseUser);

    ResponseUser returnValue = userService.createUser(requestUser);

    Assertions.assertAll(
        () -> assertThat(returnValue).isNotNull(),
        () -> assertThat(returnValue.getEmail()).isEqualTo(requestUser.getEmail()),
        () -> assertThat(returnValue.getName()).isEqualTo(requestUser.getName())
    );
  }

  @DisplayName("중복된 이메일로 회원가입 하면 예외가 발생한다.")
  @Test
  void exception_createUser() {
    RequestUser requestUser = RequestUser.builder()
        .email("email@google.com")
        .name("minseok kang")
        .pwd("test1234")
        .build();

    given(userRepository.existsByEmail(any())).willReturn(true);

    assertThatThrownBy(() -> userService.createUser(requestUser))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("email@google.com");
  }
}