package hr.fer.jollybringer.config;

import hr.fer.jollybringer.service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOidcUserService customOidcUserService) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/", "/index.html", "/assets/**", "/vite.svg").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login ->
                oauth2Login
                    .defaultSuccessUrl("/dashboard")
                    .userInfoEndpoint(userInfoEndpoint ->
                        userInfoEndpoint.oidcUserService(customOidcUserService)
                    )
            )
            .exceptionHandling(exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/"))
            );
        return http.build();
    }
}