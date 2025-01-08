package hr.fer.jollybringer.controller;

import hr.fer.jollybringer.dao.*;
import hr.fer.jollybringer.domain.*;
import hr.fer.jollybringer.dto.PresidentApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private PresidentApplicationRepository presidentApplicationRepository;

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

    @PostMapping("/apply-president")
    public String applyForPresident(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            Optional<User> userOptional = userRepository.findByEmail(oidcUser.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Optional<PresidentApplication> existingApplication = presidentApplicationRepository.findByUserId(user.getId());
                if (existingApplication.isPresent()) {
                    return "You have already applied for president.";
                }
                PresidentApplication application = new PresidentApplication();
                application.setUserId(user.getId());
                application.setStatus("Pending");
                application.setCreatedAt(LocalDateTime.now());
                presidentApplicationRepository.save(application);
                return "Application submitted successfully!";
            }
        }
        return "User not found!";
    }

    @GetMapping("/president-applications")
    public List<PresidentApplicationDTO> getAllPresidentApplications() {
        List<PresidentApplication> applications = presidentApplicationRepository.findAll();
        List<PresidentApplicationDTO> applicationDTOs = new ArrayList<>();

        for (PresidentApplication application : applications) {
            Optional<User> userOptional = userRepository.findById(application.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                PresidentApplicationDTO dto = new PresidentApplicationDTO();
                dto.setId(application.getId());
                dto.setUserId(application.getUserId());
                dto.setUserName(user.getName());
                dto.setUserEmail(user.getEmail());
                dto.setStatus(application.getStatus());
                dto.setCreatedAt(application.getCreatedAt());
                applicationDTOs.add(dto);
            }
        }
        return applicationDTOs;
    }
}