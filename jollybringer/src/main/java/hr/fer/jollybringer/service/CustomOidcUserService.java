package hr.fer.jollybringer.service;

import hr.fer.jollybringer.dao.UserRepository;
import hr.fer.jollybringer.dao.GroupRepository;
import hr.fer.jollybringer.dao.RoleRepository;
import hr.fer.jollybringer.dao.MembershipRepository;
import hr.fer.jollybringer.domain.User;
import hr.fer.jollybringer.domain.Group;
import hr.fer.jollybringer.domain.Role;
import hr.fer.jollybringer.domain.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        Optional<User> existingUser = userRepository.findByEmail(oidcUser.getEmail());
        User user;
        if (existingUser.isEmpty()) {
            user = new User();
            user.setName(oidcUser.getFullName());
            user.setEmail(oidcUser.getEmail());
            userRepository.save(user);
        } else {
            user = existingUser.get();
        }

        // Ensure the user is a member of the "NO_GROUP" group with the "Participant" role
        Optional<Group> noGroup = groupRepository.findByName("NO_GROUP");
        Optional<Role> participantRole = roleRepository.findByName("Participant");

        if (noGroup.isPresent() && participantRole.isPresent()) {
            Optional<Membership> membership = membershipRepository.findByUserIdAndGroupId(user.getId(), noGroup.get().getId());
            if (membership.isEmpty()) {
                Membership newMembership = new Membership();
                newMembership.setUserId(user.getId());
                newMembership.setGroupId(noGroup.get().getId());
                newMembership.setRoleId(participantRole.get().getId());
                membershipRepository.save(newMembership);
            }
        }

        return oidcUser;
    }
}