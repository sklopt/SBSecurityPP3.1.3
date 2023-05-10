package ru.kata.spring.boot_security.pp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.pp.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String name);
}
