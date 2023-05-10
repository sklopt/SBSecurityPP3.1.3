package ru.kata.spring.boot_security.pp.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.pp.entities.ListOfUsers;
import ru.kata.spring.boot_security.pp.repositories.ListOfUsersRepository;

import java.util.List;

@Service
public class ListOfUsersService {
    private final ListOfUsersRepository usersRepository;

    public ListOfUsersService(ListOfUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<ListOfUsers> getAllUsers() {
        return usersRepository.findAll();
    }

    public void saveUser(ListOfUsers user) {
        usersRepository.save(user);
    }

    public void removeUserById(long id) {
        usersRepository.delete(getUserById(id));
    }

    public void changeUser(ListOfUsers user) {
        usersRepository.save(user);
    }

    public ListOfUsers getUserById(long id) {
        return usersRepository.findById(id).orElse(null);
    }
}
