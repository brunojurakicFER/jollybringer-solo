package hr.fer.jollybringer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(authorizeRequests ->
              authorizeRequests
                  .requestMatchers("/", "/index.html", "/assets/**", "/vite.svg").permitAll()
                  .requestMatchers("/login").permitAll()
                  .anyRequest().authenticated()
          )
          .oauth2Login(oauth2Login ->
              oauth2Login
                  .defaultSuccessUrl("/dashboard")
                  .userInfoEndpoint(userInfoEndpoint ->
                      userInfoEndpoint.oidcUserService(new OidcUserService())
                  )
          );
      return http.build();
  }
}