package com.example.softwareTracker.config;

import com.example.softwareTracker.enumeration.LogType;
import com.example.softwareTracker.model.Log;
import com.example.softwareTracker.model.Task;
import com.example.softwareTracker.repository.LogRepository;
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
@Order(4)
@ConditionalOnProperty(
        value="config.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class LogConfig implements CommandLineRunner {
    private final LogRepository logRepository;

    private final TaskRepository taskRepository;
    @Autowired
    public LogConfig(LogRepository logRepository, TaskRepository taskRepository) {
        this.logRepository = logRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Log> logs = new ArrayList<>();
        for (int i = 0; i<100; i++) {
            Task task = this.taskRepository.findById((long) (i+1)).get();
            for (int j = 0; j<50; j++ ) {
                Log log = new Log();
                log.setTask(task);
                log.setDate(new Date());
                log.setMessage("Test message"+(i*10 +j));
                if (j%3==0) {
                    log.setLogType(LogType.INFO);
                }
                else if (j%3==1) {
                    log.setLogType(LogType.WARNING);
                } else {
                    log.setLogType(LogType.ERROR);
                }
                logs.add(log);
            }
        }
        this.logRepository.saveAll(logs);
    }
}
