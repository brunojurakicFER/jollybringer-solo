package hr.fer.jollybringer.controller;

import hr.fer.jollybringer.dao.UserRepository;
import hr.fer.jollybringer.dao.MembershipRepository;
import hr.fer.jollybringer.dao.GroupRepository;
import hr.fer.jollybringer.dao.RoleRepository;
import hr.fer.jollybringer.domain.User;
import hr.fer.jollybringer.domain.Membership;
import hr.fer.jollybringer.domain.Group;
import hr.fer.jollybringer.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/user")
    public Map<String, Object> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
        Map<String, Object> response = new HashMap<>();
        if (oidcUser != null) {
            Optional<User> userOptional = userRepository.findByEmail(oidcUser.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                response.put("user", user);

                List<Map<String, Object>> groups = new ArrayList<>();
                List<Membership> memberships = membershipRepository.findByUserId(user.getId());
                for (Membership membership : memberships) {
                    Map<String, Object> groupInfo = new HashMap<>();
                    Optional<Group> groupOptional = groupRepository.findById(membership.getGroupId());
                    Optional<Role> roleOptional = roleRepository.findById(membership.getRoleId());
                    groupOptional.ifPresent(group -> groupInfo.put("group", group));
                    roleOptional.ifPresent(role -> groupInfo.put("role", role));
                    groups.add(groupInfo);
                }
                response.put("groups", groups);
            }
        }
        return response;
    }
}