package hr.fer.jollybringer.controller;

import hr.fer.jollybringer.dao.UserRepository;
import hr.fer.jollybringer.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public OidcUser getUser(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            Optional<User> existingUser = userRepository.findByEmail(oidcUser.getEmail());
            if (existingUser.isEmpty()) {
                User user = new User();
                user.setName(oidcUser.getFullName());
                user.setEmail(oidcUser.getEmail());
                userRepository.save(user);
            }
        }
        return oidcUser;
    }
}