//package ru.kata.spring.boot_security.demo.services;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import ru.kata.spring.boot_security.demo.repositories.UserRepository;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user1 = userRepository.findByEmail(username);
//        if (user1 == null) {
//            throw new UsernameNotFoundException(username);
//        }
//
//        UserDetails user = User.withUsername(user1.getUsername()).password(user1.getPassword()).roles("AUTHOR").username(user1.getUsername()).build();
//
//
//        return user;
//
//    }
//}
