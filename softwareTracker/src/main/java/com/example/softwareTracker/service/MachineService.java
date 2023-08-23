package com.example.softwareTracker.service;

import com.example.softwareTracker.model.Machine;
import com.example.softwareTracker.model.Task;
import com.example.softwareTracker.repository.MachineRepository;
import com.example.softwareTracker.util.JwtUtilForAdmin;
import com.example.softwareTracker.util.JwtUtilForMachine;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Objects;

@Service
public class MachineService {
    private final MachineRepository machineRepository;

    private final TaskService taskService;
    private final JwtUtilForMachine jwtUtilForMachine;

    private final JwtUtilForAdmin jwtUtilForAdmin;

    @Autowired
    public MachineService(MachineRepository machineRepository, TaskService taskService, JwtUtilForMachine jwtUtilForMachine, JwtUtilForAdmin jwtUtilForAdmin) {
        this.machineRepository = machineRepository;
        this.taskService = taskService;
        this.jwtUtilForMachine = jwtUtilForMachine;
        this.jwtUtilForAdmin = jwtUtilForAdmin;
    }

    public List<Machine> getMachines(String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);

        return this.machineRepository.findAllByOrderByHostnameAsc();
    }

    public Machine getMachine(String hostname, String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);
        return this.machineRepository.getMachineByHostname(hostname).orElseThrow(()-> new MissingResourceException("Machine not found", Machine.class.getName(), hostname));
    }

    public Machine createMachine(Machine machine, String token) {
        String hostname = this.jwtUtilForMachine.validateToken(token);
        if (!Objects.equals(hostname, machine.getHostname())) {
            throw new IllegalStateException("Invalid token!");
        }
        machine.setLastSeen(new Date());
        return this.machineRepository.save(machine);
    }

    @Transactional
    public void updateMachine(String token, List<Task> tasks) {
        String hostname = this.jwtUtilForMachine.validateToken(token);
        Machine machine = this.machineRepository.getMachineByHostname(hostname).orElseThrow(()-> new MissingResourceException("Machine not found", Machine.class.getName(), hostname));
        machine.setLastSeen(new Date());
        for (Task task : tasks) {
            this.taskService.updateTask(task.getId(), task.getThreadStatus(), task.getDate());
        }
        this.machineRepository.save(machine);
    }

    public List<Machine> searchMachines(String searchKey, String token) {
        String username = this.jwtUtilForAdmin.validateToken(token);
        return this.machineRepository.findByHostnameIsContainingOrNameIsContaining(searchKey, searchKey);
    }
}
