package com.example.softwareTracker.config;

import com.example.softwareTracker.enumeration.ThreadStatus;
import com.example.softwareTracker.model.Config;
import com.example.softwareTracker.model.Machine;
import com.example.softwareTracker.model.Task;
import com.example.softwareTracker.repository.ConfigRepository;
import com.example.softwareTracker.repository.MachineRepository;
import com.example.softwareTracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Order(3)
@ConditionalOnProperty(
        value="config.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class TaskConfig implements CommandLineRunner {
    private final ConfigRepository configRepository;
    private final MachineRepository machineRepository;

    private final TaskRepository taskRepository;
    @Autowired
    public TaskConfig(ConfigRepository configRepository, MachineRepository machineRepository, TaskRepository taskRepository) {
        this.configRepository = configRepository;
        this.machineRepository = machineRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Task task1 = new Task();
        task1.setThreadStatus(ThreadStatus.RUNNING);
        Config config1 = configRepository.findById(1L).get();
        Machine machine1 = machineRepository.findById(1L).get();
        task1.setConfig(config1);
        task1.setUsername("user1");
        task1.setMachine(machine1);
        task1.setDate(new Date());

        Task task2 = new Task();
        task2.setThreadStatus(ThreadStatus.COMPLETED);
        Config config2 = configRepository.findById(2L).get();
        Machine machine2 = machineRepository.findById(2L).get();
        task2.setConfig(config2);
        task2.setUsername(config2.getUsername());
        task2.setMachine(machine2);
        task2.setDate(new Date());

        this.taskRepository.saveAll(List.of(task1,task2));

        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i<100; i++) {
            Config config = configRepository.findById((long) (i+3)).get();
            Task task = new Task();
            task.setConfig(config);
            task.setMachine(machine1);
            task.setThreadStatus(ThreadStatus.FAILED);
            task.setUsername("user1");
            task.setDate(new Date());
            tasks.add(task);
        }
        this.taskRepository.saveAll(tasks);
    }
}
