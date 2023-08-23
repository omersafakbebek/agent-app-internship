package com.example.softwareTracker.config;

import com.example.softwareTracker.model.Admin;
import com.example.softwareTracker.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value="config.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class AdminConfig implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;

    @Autowired
    public AdminConfig(PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String password = "password";
        Admin admin = new Admin("username", passwordEncoder.encode(password));
        adminRepository.save(admin);
    }
}
