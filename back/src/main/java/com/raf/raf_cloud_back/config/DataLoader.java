package com.raf.raf_cloud_back.config;


import com.raf.raf_cloud_back.model.User;
import com.raf.raf_cloud_back.repository.UserRepository;
import com.raf.raf_cloud_back.security.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByEmail("admin@raf.rs").isPresent()) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("Adminovic");
            admin.setEmail("admin@raf.rs");
            admin.setPassword(passwordUtil.hashPassword("admin123"));
            
            Set<String> adminPermissions = new HashSet<>();
            adminPermissions.add("can_create_users");
            adminPermissions.add("can_read_users");
            adminPermissions.add("can_update_users");
            adminPermissions.add("can_delete_users");
            adminPermissions.add("can_search_machines");
            adminPermissions.add("can_start_machines");
            adminPermissions.add("can_stop_machines");
            adminPermissions.add("can_restart_machines");
            adminPermissions.add("can_create_machines");
            adminPermissions.add("can_destroy_machines");
            admin.setPermissions(adminPermissions);

            userRepository.save(admin);
            System.out.println("Admin korisnik je kreiran!");
            System.out.println("Email: admin@raf.rs");
        }

        if (!userRepository.findByEmail("user@raf.rs").isPresent()) {
            User regularUser = new User();
            regularUser.setFirstName("Petar");
            regularUser.setLastName("Poznanovic");
            regularUser.setEmail("user@raf.rs");
            regularUser.setPassword(passwordUtil.hashPassword("user123"));
            
            Set<String> userPermissions = new HashSet<>();
            userPermissions.add("can_search_machines");
            userPermissions.add("can_start_machines");
            userPermissions.add("can_stop_machines");
            userPermissions.add("can_restart_machines");
            userPermissions.add("can_create_machines");
            userPermissions.add("can_destroy_machines");
            regularUser.setPermissions(userPermissions);

            userRepository.save(regularUser);
            System.out.println("Test korisnik je kreiran!");
            System.out.println("Email: user@raf.rs");
        }
    }
}

