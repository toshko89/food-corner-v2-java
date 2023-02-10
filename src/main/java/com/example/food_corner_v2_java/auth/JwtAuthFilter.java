package com.example.food_corner_v2_java.auth;

import com.example.food_corner_v2_java.model.AppUser;
import com.example.food_corner_v2_java.service.AppUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AppUserService appUserService;

    public JwtAuthFilter(JwtService jwtService, AppUserService appUserService) {
        this.jwtService = jwtService;
        this.appUserService = appUserService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String JWT;
        final String userEmail;
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            JWT = authorizationHeader.substring(7);
            userEmail = jwtService.extractUserEmail(JWT);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AppUser appUser = this.appUserService.getUserByEmail(userEmail);
                if (jwtService.validateToken(JWT, appUser)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            appUser, null, appUser.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("error", e.getMessage());
        }
    }
}
