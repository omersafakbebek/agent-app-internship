package com.example.softwareTracker.service;

import com.example.softwareTracker.enumeration.ThreadStatus;
import com.example.softwareTracker.model.Config;
import com.example.softwareTracker.model.Machine;
import com.example.softwareTracker.model.Task;
import com.example.softwareTracker.repository.ConfigRepository;
import com.example.softwareTracker.repository.MachineRepository;
import com.example.softwareTracker.repository.TaskRepository;
import com.example.softwareTracker.util.JwtUtilForAdmin;
import com.example.softwareTracker.util.JwtUtilForMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final JwtUtilForAdmin jwtUtilForAdmin;

    private final MachineRepository machineRepository;

    private final ConfigRepository configRepository;
    private final JwtUtilForMachine jwtUtilForMachine;
    @Autowired
    public TaskService(TaskRepository taskRepository, JwtUtilForAdmin jwtUtilForAdmin, MachineRepository machineRepository, ConfigRepository configRepository, JwtUtilForMachine jwtUtilForMachine) {
        this.taskRepository = taskRepository;
        this.jwtUtilForAdmin = jwtUtilForAdmin;
        this.machineRepository = machineRepository;
        this.configRepository = configRepository;
        this.jwtUtilForMachine = jwtUtilForMachine;
    }

    public Task getTask(long id, String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);
        return this.taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("Task not found!"));
    }
    public List<Task> getAllTasks(String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);

        return this.taskRepository.findAllByOrderByIdAsc();
    }

    public List<Task> getTasksByMachine(String hostname, String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);

        return this.taskRepository.getTasksByMachine_HostnameOrderByIdAsc(hostname);
    }

    public Task createTask(Task task, String token) {
        String hostname = this.jwtUtilForMachine.validateToken(token);
        Machine machine = this.machineRepository.getMachineByHostname(task.getMachine().getHostname()).orElseThrow(()->new IllegalStateException("Machine not found!"));
        task.setMachine(machine);
        Config config = this.configRepository.findById(task.getConfig().getId()).orElseThrow(() -> new IllegalStateException("Config not found!"));
        task.setConfig(config);
        return this.taskRepository.save(task);
    }

    public void updateTask(long id, ThreadStatus threadStatus, Date date) {
        Task task = this.taskRepository.findById(id).orElseThrow(() ->new IllegalStateException("Task not found!"));
        task.setThreadStatus(threadStatus);
        task.setDate(date);
        this.taskRepository.save(task);
    }
}
