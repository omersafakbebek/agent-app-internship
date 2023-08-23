package com.example.softwareTracker.controller;

import com.example.softwareTracker.model.Machine;
import com.example.softwareTracker.model.Task;
import com.example.softwareTracker.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/machine")
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
public class MachineController {
    private final MachineService machineService;

    @Autowired
    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }
    @GetMapping("")
    public List<Machine> getMachines(@RequestHeader("token") String token) {
        return this.machineService.getMachines(token);
    }

    @GetMapping("/{hostname}")
    public Machine getMachine(@PathVariable String hostname, @RequestHeader("token") String token) {
        return this.machineService.getMachine(hostname, token);
    }

    @PostMapping("")
    public Machine createMachine(@RequestBody Machine machine, @RequestHeader("token") String token) {
        return this.machineService.createMachine(machine, token);
    }

    @PatchMapping("")
    public void updateMachine(@RequestHeader("token") String token, @RequestBody List<Task> tasks) {
        this.machineService.updateMachine(token, tasks);
    }

    @GetMapping("/search/{searchKey}")
    public List<Machine> searchMachines(@PathVariable String searchKey, @RequestHeader("token") String token) {
        return this.machineService.searchMachines(searchKey, token);
    }
}
