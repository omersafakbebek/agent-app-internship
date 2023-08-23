package com.example.softwareTracker.controller;

import com.example.softwareTracker.model.Task;
import com.example.softwareTracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
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
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/{id}")
    public Task getTask(@PathVariable long id, @RequestHeader("token") String token) {
        return this.taskService.getTask(id, token);
    }
    @GetMapping("")
    public List<Task> getAllTasks(@RequestHeader("token") String token) {
        return this.taskService.getAllTasks(token);
    }

    @GetMapping("/byMachine/{hostname}")
    public List<Task> getTasksByHostname(@PathVariable String hostname, @RequestHeader("token") String token) {
        return this.taskService.getTasksByMachine(hostname, token);
    }

    @PostMapping("")
    public Task createTask(@RequestBody Task task, @RequestHeader("token") String token) {
        return this.taskService.createTask(task, token);
    }
}
