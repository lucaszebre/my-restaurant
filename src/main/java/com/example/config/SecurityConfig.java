package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.entities.RestaurantConfig;
import com.example.repositories.RestaurantConfigRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RestaurantConfigRepository restaurantConfigRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/config", "/admin/config", "/admin/config/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                .requestMatchers("/admin/files/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        String adminPassword = "admin";
        try {
            var configs = restaurantConfigRepository.findAll();
            if (!configs.isEmpty() && configs.get(0).getPassword() != null) {
                adminPassword = configs.get(0).getPassword();
            } else {
                adminPassword = passwordEncoder().encode("admin");
            }
        } catch (Exception e) {
            adminPassword = passwordEncoder().encode("admin");
        }

        UserDetails admin = User.builder()
            .username("admin")
            .password(adminPassword)
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 