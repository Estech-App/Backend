package com.estech.EstechAppBackend.security.filters;

import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Method that controls when a user is trying to autheticate in the app.
     * @param request -> Http request.
     * @param response -> Http response.
     * @return Authentication Object from Spring Security library.
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserEntity user = null;
        String email = "";
        String password = "";

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            email = user.getEmail();
            password = user.getPassword();
        } catch (Exception e) {
            logger.error("Error en la lectura de bytes del objeto UserEntity " + e.getCause().getMessage());
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        return getAuthenticationManager().authenticate(token);
    }

    /**
     * Method that informs that the authentication has been done successfully.
     * @param request -> Http request
     * @param response -> Http response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User)authResult.getPrincipal();
        String token = jwtUtils.generateAccessToken(user.getUsername());

        Map<String, Object> mapa = new HashMap<>();

        response.addHeader("Authorization", token);

        mapa.put("token", token);
        mapa.put("message", "authenticated user");
        mapa.put("username", user.getUsername());
        mapa.put("roles", user.getAuthorities());

        response.getWriter().write(new ObjectMapper().writeValueAsString(mapa));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
