package hr.fer.jollybringer.service;

import hr.fer.jollybringer.dao.UserRepository;
import hr.fer.jollybringer.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        Optional<User> existingUser = userRepository.findByEmail(oidcUser.getEmail());
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setName(oidcUser.getFullName());
            user.setEmail(oidcUser.getEmail());
            userRepository.save(user);
        }

        return oidcUser;
    }
}