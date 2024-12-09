package com.pizzaChain.customer.config;

import com.pizzaChain.customer.service.JwtService;
import com.pizzaChain.customer.service.TokenBlacklistService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {
    //now
//    private final JwtService jwtService;
//    private final TokenBlacklistService tokenBlacklistService;
//
//    public AuthConfig(JwtService jwtService, TokenBlacklistService tokenBlacklistService) {
//        this.jwtService = jwtService;
//        this.tokenBlacklistService = tokenBlacklistService;
//    }
    //now


    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .authorizeHttpRequests()
//       //         .requestMatchers("/auth/register", "/auth/token", "/auth/validate").permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection (consider enabling it for non-API applications)
//            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll() // Allow all requests (configure specific permissions as needed)
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless session management
            );

    // Uncomment and configure if using JWT authentication
    // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // finds the appropriate provide to authentiate
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){  //authenticate the user
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
