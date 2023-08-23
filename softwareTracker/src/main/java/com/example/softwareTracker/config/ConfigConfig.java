package com.example.softwareTracker.config;

import com.example.softwareTracker.enumeration.ConfigType;
import com.example.softwareTracker.enumeration.TaskType;
import com.example.softwareTracker.model.Config;
import com.example.softwareTracker.model.Parameter;
import com.example.softwareTracker.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
@ConditionalOnProperty(
        value="config.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class ConfigConfig implements CommandLineRunner {
    private final ConfigRepository configRepository;

    @Autowired
    public ConfigConfig(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    public void run(String...args) throws Exception {
        Config config1 = new Config();
        config1.setConfigType(ConfigType.GENERAL);
        config1.setTaskType(TaskType.ALERT);
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter();
        parameter.setConfig(config1);
        parameter.setName("software");
        parameter.setValue("notepad");
        parameters.add(parameter);
        config1.setParameters(parameters);

        Config config2 = new Config();
        config2.setConfigType(ConfigType.USER);
        config2.setTaskType(TaskType.TRACK);
        config2.setUsername("username");
        List<Parameter> parameters2 = new ArrayList<>();
        Parameter parameter2 = new Parameter();
        parameter2.setConfig(config2);
        parameter2.setName("software");
        parameter2.setValue("word");
        parameters2.add(parameter2);
        config2.setParameters(parameters2);

        configRepository.saveAll(List.of(config1, config2));

        List<Config> configs = new ArrayList<>();

        for(int i = 0;i<100; i++) {
            Config config = new Config();
            config.setConfigType(ConfigType.GENERAL);
            config.setTaskType(TaskType.ALERT);
            configs.add(config);
        }
        configRepository.saveAll(configs);
    }
}
