package ru.kata.spring.boot_security.pp.configs;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kata.spring.boot_security.pp.services.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    add user in database
//    insert into roles (name) values ('ROLE_USER'), ('ROLE_ADMIN');
//    insert into users (username, password, email) values ('sklopt', '$2a$12$gfmMgOy3UbCSbQcjeyTNTeqC0Z2xd9XsdF/0c.eYSBHqOc6D2/jGe', 'sklopt@rambler.ru');
//    insert into users_roles (user_id, role_id) VALUES (1, 2);
//    insert into users_roles (user_id, role_id) VALUES (1, 1);
//    insert into users (username, password, email) values ('crazyman', '$2a$12$gfmMgOy3UbCSbQcjeyTNTeqC0Z2xd9XsdF/0c.eYSBHqOc6D2/jGe', 'crazyman@mail.ru');
//    insert into users_roles (user_id, role_id) VALUES (2, 1);

//    private UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
    private final UserService userDetailsService;

    private final SuccessUserHandler successUserHandler;

    @Autowired
    public SecurityConfig(SuccessUserHandler successUserHandler,UserService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("user").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/users").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                )
                .formLogin()
                .successHandler(successUserHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
        return httpSecurity.build();
    }

//    @Bean
//    CustomUserDetailsService customUserDetailsService() {
//        return new CustomUserDetailsService();
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
//        builder.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }


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
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setUserDetailsService(userService);
//        return authenticationProvider;
//    }
}
