//package ru.kata.spring.boot_security.demo.configs;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Set;
//
//@Component
//public class SuccessUserHandler implements AuthenticationSuccessHandler {
//    private final jakarta.servlet.http.HttpServletResponse httpServletResponse;
//
//    public SuccessUserHandler(jakarta.servlet.http.HttpServletResponse httpServletResponse) {
//        this.httpServletResponse = httpServletResponse;
//    }
//
//    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
//
//    @Override
//    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
//    }
//
//    @Override
//    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        if (roles.contains("ROLE_USER")) {
//            httpServletResponse.sendRedirect("/user");
//        } else {
//            httpServletResponse.sendRedirect("/");
//        }
//    }
//}