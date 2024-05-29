package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.example.backend.Configurations.IntegrationsProperties;
import org.example.backend.Security.Role;
import org.example.backend.Security.RoleRepository;
import org.example.backend.Security.User;
import org.example.backend.Security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@ComponentScan
@RequiredArgsConstructor
public class UserDataSeeder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    IntegrationsProperties integrations;

    private String adminUsername, adminPassword, receptionistUsername, receptionistPassword;

    @Override
    public void run(String... args) throws Exception {

        adminUsername = integrations.getUsers().getAdminUsername();
        adminPassword = integrations.getUsers().getAdminPassword();
        receptionistUsername = integrations.getUsers().getReceptionistUsername();
        receptionistPassword = integrations.getUsers().getReceptionistPassword();

        if (roleRepository.findByName("Admin") == null) {
            addRole("Admin");
        }
        if (roleRepository.findByName("Receptionist") == null) {
            addRole("Receptionist");
        }
        if(userRepository.getUserByUsername(adminUsername) == null){
            addUser(adminUsername,"Admin", adminPassword);
        }
        if(userRepository.getUserByUsername(receptionistUsername) == null){
            addUser(receptionistUsername,"Receptionist", receptionistPassword);
        }
    }

    private void addUser(String mail, String role, String password) {
        ArrayList<Role> roles = new ArrayList<>();
        addRole(role);
        Role r = roleRepository.findByName(role);
        roles.add(r);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password);
        User u = User.builder().username(mail).password(hash).enabled(true).roles(roles).build();
        System.out.println(u.toString());
        userRepository.save(u);
    }

    private void addRole(String role) {
        if (roleRepository.findByName(role)==null){
            Role r = Role.builder().name(role).build();
            roleRepository.save(r);
        }
    }
}
