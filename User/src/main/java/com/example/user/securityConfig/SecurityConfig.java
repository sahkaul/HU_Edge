package com.example.user.securityConfig;

import com.example.user.filter.JWTTokenGeneratorFilter;
import com.example.user.filter.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig
{
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class) // generate jwt token after authentication filter
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class) // execute jwt validation before authentication filter
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user/register").permitAll()
                        .requestMatchers("/user/getUser").hasAuthority("ADMIN")
                        .requestMatchers("/user/login").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/user/resetPassword").hasAnyAuthority("ADMIN", "USER"));

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
