package com.example.Musicalistauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class ConfiguracionSeguridad {

    private JWTFiltroAutorizacion jwtFiltroAutorizacion;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().addFilterAfter(jwtFiltroAutorizacion, UsernamePasswordAuthenticationFilter.class).authorizeRequests(authorize -> authorize
                .antMatchers(HttpMethod.POST, "/public/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/info").permitAll()
                .anyRequest().authenticated());//.csrf(csrf -> csrf.ignoringRequestMatchers(ignoreSpecificRequests()));
        return http.build();
    }

    private RequestMatcher ignoreSpecificRequests() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/public/login"),
                new AntPathRequestMatcher("/public/signup")
        );
    }
}
