package me.minseok.ezfarmuser.repository;

import me.minseok.ezfarmuser.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByEmail(String email);

  User findByUserId(String userId);

  boolean existsByEmail(String email);
}
