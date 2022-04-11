package me.minseok.ezfarmuser.service;

import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.minseok.ezfarmuser.domain.User;
import me.minseok.ezfarmuser.domain.dto.UserDto;
import me.minseok.ezfarmuser.repository.UserRepository;
import me.minseok.ezfarmuser.vo.request.RequestUser;
import me.minseok.ezfarmuser.vo.response.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    }
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPwd(), true,
        true, true, true, new ArrayList<>());
  }

  public UserDto getUserDetailsByEmail(String email) {
    User userEntity = userRepository.findByEmail(email);

    if (userEntity == null) {
      throw new UsernameNotFoundException(email);
    }

    return modelMapper.map(userEntity, UserDto.class);
  }

  public ResponseUser createUser(RequestUser requestUser) {
    if (userRepository.existsByEmail(requestUser.getEmail())) {
      throw new RuntimeException(requestUser.getEmail());
    }
    User user = User.builder()
        .userId(UUID.randomUUID().toString())
        .encryptedPwd(passwordEncoder.encode(requestUser.getPwd()))
        .email(requestUser.getEmail())
        .name(requestUser.getName())
        .build();

    userRepository.save(user);

    return modelMapper.map(user, ResponseUser.class);
  }

  public ResponseUser getUserById(String userId) {
    User user = userRepository.findByUserId(userId);

    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return modelMapper.map(user, ResponseUser.class);
  }
}
