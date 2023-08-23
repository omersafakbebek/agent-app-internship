package com.example.softwareTracker.controller;

import com.example.softwareTracker.dto.LoginRequestDTO;
import com.example.softwareTracker.dto.LoginResponseDTO;
import com.example.softwareTracker.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(
        origins = {
                "http://localhost:3000",
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        }
)
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return this.adminService.login(loginRequestDTO);
    }
}
