package com.example.softwareTracker.service;

import com.example.softwareTracker.enumeration.ConfigType;
import com.example.softwareTracker.model.*;
import com.example.softwareTracker.repository.ConfigRepository;
import com.example.softwareTracker.repository.MachineRepository;
import com.example.softwareTracker.util.JwtUtilForAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class ConfigService {

    private final ConfigRepository configRepository;

    private final MachineRepository machineRepository;
    private final JwtUtilForAdmin jwtUtilForAdmin;

    @Autowired
    public ConfigService(ConfigRepository configRepository, MachineRepository machineRepository, JwtUtilForAdmin jwtUtilForAdmin) {
        this.configRepository = configRepository;
        this.machineRepository = machineRepository;
        this.jwtUtilForAdmin = jwtUtilForAdmin;
    }

    public Config getConfig(long id) {
        return this.configRepository.findById(id).orElseThrow(() -> new IllegalStateException("Config not found!"));
    }
    public Config createConfig(Config config, String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);

        if (config.getConfigType() == ConfigType.USER && config.getUsername() == null) {
            throw new IllegalStateException("Username cannot be null for this config type!");
        }
        if (config.getConfigType() == ConfigType.MACHINE && (config.getMachine() == null || config.getMachine().getHostname() == null)) {
            throw new IllegalStateException("Machine cannot be null for this config type!");
        }
        if (config.getConfigType() == ConfigType.MACHINE) {
            Machine machine = this.machineRepository.getMachineByHostname(config.getMachine().getHostname()).orElseThrow(()-> new IllegalStateException("Machine not found!"));
            config.setMachine(machine);
        }
        List<Parameter> parameters = config.getParameters();
        Iterator<Parameter> itr = parameters.iterator();
        while (itr.hasNext()) {
            Parameter param = itr.next();
            if (Objects.equals(param.getName(), "") || Objects.equals(param.getValue(),"")) {
                itr.remove();
                continue;
            }
            param.setConfig(config);
        }
        return this.configRepository.save(config);
    }

    public List<Config> getUserConfigs(String username) {
        return this.configRepository.getConfigsByUsernameAndConfigType(username, ConfigType.USER);
    }
    public List<Config> getMachineConfigs(String hostname) {
        return this.configRepository.findByMachine_HostnameAndConfigType(hostname, ConfigType.MACHINE);
    }
    public List<Config> getGeneralConfigs() {
        return this.configRepository.getConfigsByConfigType(ConfigType.GENERAL);
    }

    public List<Config> getAllConfigs() {
        return this.configRepository.findAll();
    }

    public void updateConfig(long id, Config body, String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);
        Config config = this.configRepository.findById(id).orElseThrow(()-> new IllegalStateException("Config not found!"));
        if (body.getConfigType() == ConfigType.USER && body.getUsername() == null) {
            throw new IllegalStateException("Username cannot be null for this config type!");
        }
        if (body.getConfigType() == ConfigType.MACHINE && (body.getMachine() == null || body.getMachine().getHostname() == null)) {
            throw new IllegalStateException("Machine cannot be null for this config type!");
        }
        if (body.getConfigType() == ConfigType.MACHINE) {
            Machine machine = this.machineRepository.getMachineByHostname(body.getMachine().getHostname()).orElseThrow(()-> new IllegalStateException("Machine not found!"));
            config.setMachine(machine);
        }
        if (body.getConfigType()!= null) {
            config.setConfigType(body.getConfigType());
        }
        if (body.getUsername()!= null) {
            config.setUsername(body.getUsername());
        }
        if (body.getParameters()!= null) {
            config.setParameters(body.getParameters());
        }
        if (body.getTaskType()!=null) {
            config.setTaskType(body.getTaskType());
        }
        List<Parameter> parameters = config.getParameters();
        Iterator<Parameter> itr = parameters.iterator();
        while (itr.hasNext()) {
            Parameter param = itr.next();
            if (Objects.equals(param.getName(), "") || Objects.equals(param.getValue(),"")) {
                itr.remove();
                continue;
            }
            param.setConfig(config);
        }
        this.configRepository.save(config);
    }
    public void deleteConfig(long id, String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);
        this.configRepository.deleteById(id);
    }

}
