package ru.kata.spring.boot_security.pp.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.pp.entities.Role;
import ru.kata.spring.boot_security.pp.entities.User;
import ru.kata.spring.boot_security.pp.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    @Autowired
    @Lazy
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userPrimary = Optional.ofNullable(userRepository.findUserByEmail(username));
        if (!userPrimary.isPresent()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return userPrimary.get();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Optional<User> userPrimary = Optional.ofNullable(userRepository.findUserByUsername(username));
//        if (!userPrimary.isPresent()) {
//            throw new UsernameNotFoundException(username + " not found");
//        }
//        return (UserDetails) userPrimary.get();
//    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        if (!user.getPassword().equals(userRepository.findUserByEmail(user.getEmail()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public void removeUserById(long id) {
        userRepository.delete(getUserById(id));
    }

    public void changeUser(User user) {
        if (!user.getPassword().equals(userRepository.findUserByEmail(user.getEmail()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }
}
