package com.example.softwareTracker.controller;

import com.example.softwareTracker.model.Log;
import com.example.softwareTracker.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log")
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
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("")
    public List<Log> getLogsByTaskId(@RequestHeader("token") String token) {
        return this.logService.getAllLogs(token);
    }
    @GetMapping("/byTask/{taskId}")
    public List<Log> getLogsByTaskId(@PathVariable long taskId, @RequestHeader("token") String token) {
        return this.logService.getLogsByTaskId(taskId, token);
    }

    @GetMapping("/byMachine/{hostname}")
    public List<Log> getLogsByMachine(@PathVariable String hostname, @RequestHeader("token") String token) {
        return this.logService.getLogsByHostname(hostname, token);
    }

    @PostMapping("")
    public Log createLog(@RequestBody Log log, @RequestHeader("token") String token) {
        return this.logService.createLog(log, token);
    }
}
