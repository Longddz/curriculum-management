// package com.example.config;

// import com.example.security.JwtAuthenticationFilter;
// import com.example.security.JwtTokenProvider;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Autowired
//     private JwtTokenProvider jwtTokenProvider;

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable())
//             .authorizeHttpRequests(authz -> authz
//                 .requestMatchers("/api/account/auth/register", "/api/account/auth/login").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                              UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }
// }

package com.example.config;

import com.example.security.JwtAuthenticationFilter;
import com.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Kích hoạt @PreAuthorize
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/account/auth/register", "/api/account/auth/login").permitAll()
                .requestMatchers("/api/branches/add", "/api/branches/update/**", "/api/branches/delete/**").authenticated() // Yêu cầu xác thực
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.disable()); // Stateless authentication

        return http.build();
    }
}