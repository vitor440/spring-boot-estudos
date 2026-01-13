package com.libraryapi.libraryapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http
                   .csrf(csrf -> csrf.disable())
                   .httpBasic(Customizer.withDefaults())
                   .formLogin(Customizer.withDefaults())
                   .authorizeHttpRequests(authorize ->{

                           authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                           authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");
                           authorize.anyRequest().authenticated();
                   })
                   .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        UserDetails usuario1 = User.builder()
                .username("Usuario")
                .password(passwordEncoder().encode("usuario123"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(usuario1, admin);
    }
}
