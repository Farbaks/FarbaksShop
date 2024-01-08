package com.faroukbakre.farbaksshop.config;

import com.faroukbakre.farbaksshop.exceptions.CustomAccessDeniedHandler;
import com.faroukbakre.farbaksshop.filters.TokenAuthenticationFilter;
import com.faroukbakre.farbaksshop.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(jwtUtil);
    }
    
    private static final String[] WHITE_LIST_URL = {
            "/users/hello",
            "/users/login",
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(WHITE_LIST_URL)
                .mvcMatchers(HttpMethod.POST, "/users");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().disable().cors().disable()
                .authorizeHttpRequests(req ->
                            req
                                // User endpoints
                                .mvcMatchers(HttpMethod.GET, "/users").hasAnyAuthority("admin")
                                // Product endpoints
                                .mvcMatchers(HttpMethod.POST, "/products").hasAnyAuthority("admin")
                                .mvcMatchers(HttpMethod.GET, "/products").hasAnyAuthority("admin", "customer")
                                .mvcMatchers(HttpMethod.GET, "/products/{id}").hasAnyAuthority("admin", "customer")
                                .mvcMatchers(HttpMethod.PUT, "/products/{id}").hasAnyAuthority("admin")
                                .mvcMatchers(HttpMethod.DELETE, "/products/{id}").hasAnyAuthority("admin")
                                // Order endpoints
                                .mvcMatchers(HttpMethod.POST, "/orders").hasAnyAuthority("customer")
                                .mvcMatchers(HttpMethod.GET, "/orders").hasAnyAuthority("customer")
                                .mvcMatchers(HttpMethod.GET, "/orders/all").hasAnyAuthority("admin")
                                .mvcMatchers(HttpMethod.GET, "/orders/driver").hasAnyAuthority("driver")
                                .mvcMatchers(HttpMethod.GET, "/orders/{id}").hasAnyAuthority("admin", "customer")
                                .mvcMatchers(HttpMethod.PUT, "/orders/{id}/assign").hasAnyAuthority("admin")
                                .mvcMatchers(HttpMethod.PUT, "/orders/{id}").hasAnyAuthority("driver")
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler(objectMapper);
    }
}
