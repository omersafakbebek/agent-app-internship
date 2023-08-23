package com.example.softwareTracker.service;

import com.example.softwareTracker.model.Log;
import com.example.softwareTracker.model.Task;
import com.example.softwareTracker.repository.LogRepository;
import com.example.softwareTracker.repository.TaskRepository;
import com.example.softwareTracker.util.JwtUtilForAdmin;
import com.example.softwareTracker.util.JwtUtilForMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    private final TaskRepository taskRepository;

    private final JwtUtilForMachine jwtUtilForMachine;

    private final JwtUtilForAdmin jwtUtilForAdmin;

    @Autowired
    public LogService(LogRepository logRepository, TaskRepository taskRepository, JwtUtilForMachine jwtUtilForMachine, JwtUtilForAdmin jwtUtilForAdmin) {
        this.logRepository = logRepository;
        this.taskRepository = taskRepository;
        this.jwtUtilForMachine = jwtUtilForMachine;
        this.jwtUtilForAdmin = jwtUtilForAdmin;
    }

    public List<Log> getAllLogs(String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);
        return this.logRepository.getLogsByOrderByDateDesc();
    }
    public List<Log> getLogsByTaskId(long taskId, String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);
        return this.logRepository.getLogsByTask_IdOrderByDateDesc(taskId);
    }

    public List<Log> getLogsByHostname(String hostname, String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);
        return this.logRepository.getLogsByTask_Machine_HostnameOrderByDateDesc(hostname);
    }

    public Log createLog(Log log, String token) {
        String hostname = this.jwtUtilForMachine.validateToken(token);
        Task task = this.taskRepository.findById(log.getTask().getId()).orElseThrow(() -> new IllegalStateException("Task not found!"));
        log.setTask(task);
        return this.logRepository.save(log);
    }
}
