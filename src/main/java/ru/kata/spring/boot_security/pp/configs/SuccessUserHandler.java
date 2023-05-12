package ru.kata.spring.boot_security.pp.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final HttpServletResponse httpServletResponse;

    public SuccessUserHandler(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
//    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, @org.jetbrains.annotations.NotNull Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user");
        } else {
            httpServletResponse.sendRedirect("/logout");
        }
    }
}