//package ru.kata.spring.boot_security.pp.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import ru.kata.spring.boot_security.pp.repositories.UserRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//    @Autowired
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        ru.kata.spring.boot_security.pp.entities.User user= userRepository.findUserByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("Unknown user: "+username);
//        }
//        return User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(String.valueOf(user.getRoles()))
//                .build();
//    }
//}
