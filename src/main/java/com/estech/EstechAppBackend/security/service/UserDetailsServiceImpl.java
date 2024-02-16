package com.estech.EstechAppBackend.security.service;

import com.estech.EstechAppBackend.model.Role;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Builds a User Object (Spring Security library) taking the email and password attributes of a UserEntity Object.
     * @param email -> The username will be considered the email of a UserEntity Object
     * @return a new User from Spring Security library
     * @throws UsernameNotFoundException -> Throws the exception when the email is not found in DB
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException("El usuario con email " + email + " no existe"));

        Set<Role> tmpRoleSet = new HashSet<>();
        tmpRoleSet.add(user.getRole());

        Collection<? extends GrantedAuthority> authorities = tmpRoleSet
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRolName().name()))).collect(Collectors.toSet());

        return new User(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
    }

}
