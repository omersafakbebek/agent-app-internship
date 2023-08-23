package com.example.softwareTracker.service;

import com.example.softwareTracker.dto.LoginRequestDTO;
import com.example.softwareTracker.dto.LoginResponseDTO;
import com.example.softwareTracker.model.Admin;
import com.example.softwareTracker.repository.AdminRepository;
import com.example.softwareTracker.util.JwtUtilForAdmin;
import com.example.softwareTracker.util.LdapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    private final LdapUtil ldapUtil;
    private final JwtUtilForAdmin jwtUtilForAdmin;
    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, LdapUtil ldapUtil, JwtUtilForAdmin jwtUtilForAdmin) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.ldapUtil = ldapUtil;
        this.jwtUtilForAdmin = jwtUtilForAdmin;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        if (!ldapUtil.validateCredentials(loginRequestDTO)) {
            throw new IllegalStateException("Bad Credentials!");
        }
        return new LoginResponseDTO(jwtUtilForAdmin.createToken(loginRequestDTO.getUsername()));
    }
}
