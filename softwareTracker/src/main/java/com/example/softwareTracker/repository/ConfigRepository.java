package com.example.softwareTracker.repository;

import com.example.softwareTracker.enumeration.ConfigType;
import com.example.softwareTracker.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, Long> {
    List<Config> getConfigsByUsernameAndConfigType(String username, ConfigType configType);

    List<Config> findByMachine_HostnameAndConfigType(String hostname, ConfigType configType);

    List<Config> getConfigsByConfigType(ConfigType configType);
}
