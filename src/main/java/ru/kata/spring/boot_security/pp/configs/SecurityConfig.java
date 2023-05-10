package ru.kata.spring.boot_security.pp.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kata.spring.boot_security.pp.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //    @Bean
//    public static BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Resource
//    public UserDetailsService userDetailsService;
//
//
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").hasAnyRole("ADMIN", "USER")
                )
                .formLogin()
                .defaultSuccessUrl("/users")
                .and()
                .logout().logoutSuccessUrl("/");
        return httpSecurity.build();
    }


//inmemory
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("shamil")
//                .password("{bcrypt}$2a$12$gfmMgOy3UbCSbQcjeyTNTeqC0Z2xd9XsdF/0c.eYSBHqOc6D2/jGe")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("timur")
//                .password("{bcrypt}$2a$12$gfmMgOy3UbCSbQcjeyTNTeqC0Z2xd9XsdF/0c.eYSBHqOc6D2/jGe")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }


// jdbc
//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("shamil")
//                .password("{bcrypt}$2a$12$gfmMgOy3UbCSbQcjeyTNTeqC0Z2xd9XsdF/0c.eYSBHqOc6D2/jGe")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("timur")
//                .password("{bcrypt}$2a$12$gfmMgOy3UbCSbQcjeyTNTeqC0Z2xd9XsdF/0c.eYSBHqOc6D2/jGe")
//                .roles("ADMIN", "USER")
//                .build();
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        if(jdbcUserDetailsManager.userExists((user.getUsername()))){
//            jdbcUserDetailsManager.deleteUser((user.getUsername()));
//        }
//        if(jdbcUserDetailsManager.userExists((admin.getUsername()))){
//            jdbcUserDetailsManager.deleteUser((admin.getUsername()));
//        }
//        jdbcUserDetailsManager.createUser(user);
//        jdbcUserDetailsManager.createUser(admin);
//        return jdbcUserDetailsManager;


    //dao
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}
