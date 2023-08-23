package com.example.softwareTracker.controller;

import com.example.softwareTracker.model.Config;
import com.example.softwareTracker.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
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
public class ConfigController {
    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/{id}")
    public Config getConfig(@PathVariable long id) {
        return this.configService.getConfig(id);
    }
    @PostMapping("")
    public Config createConfig(@RequestBody Config config, @RequestHeader("token") String token) {
        return this.configService.createConfig(config, token);
    }

    @GetMapping("")
    public List<Config> getAllConfigs() {
        return this.configService.getAllConfigs();
    }

    @GetMapping("/user/{username}")
    public List<Config> getUserConfigs(@PathVariable String username) {
        return this.configService.getUserConfigs(username);
    }

    @GetMapping("/machine/{hostname}")
    public List<Config> getMachineConfigs(@PathVariable String hostname) {
        return this.configService.getMachineConfigs(hostname);
    }

    @GetMapping("/general")
    public List<Config> getGeneralConfigs() {
        return this.configService.getGeneralConfigs();
    }

    @DeleteMapping("/{id}")
    public void deleteConfig(@PathVariable long id, @RequestHeader("token") String token) {
        this.configService.deleteConfig(id, token);
    }

    @PutMapping("/{id}")
    public void updateConfig(@PathVariable long id, @RequestBody Config body, @RequestHeader("token") String token) {
        this.configService.updateConfig(id, body, token);
    }

}
