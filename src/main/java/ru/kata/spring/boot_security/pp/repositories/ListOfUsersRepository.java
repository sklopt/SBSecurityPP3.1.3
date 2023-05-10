package ru.kata.spring.boot_security.pp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.pp.entities.ListOfUsers;

public interface ListOfUsersRepository extends JpaRepository<ListOfUsers, Long> {
}
