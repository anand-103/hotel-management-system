package com.example.hotelmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.hotelmanagement.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth", "/register", "/css/**").permitAll()
                .requestMatchers("/booking", "/submit-booking").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/auth")
                .loginProcessingUrl("/login")
                .usernameParameter("email") 
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/auth?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth?logout=true")
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF to allow form submission
            .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
