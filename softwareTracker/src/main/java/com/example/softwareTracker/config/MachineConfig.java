package com.example.softwareTracker.config;

import com.example.softwareTracker.model.Machine;
import com.example.softwareTracker.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Order(1)
@ConditionalOnProperty(
        value="config.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class MachineConfig implements CommandLineRunner {

    private final MachineRepository machineRepository;

    @Autowired
    public MachineConfig(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*Machine machine = new Machine();

        machine.setHostname("hostname");
        machine.setName("name");
        machine.setLastSeen(new Date());

        machineRepository.save(machine);*/

        List<Machine> machines = new ArrayList<>();

        for (int i = 0; i<100; i++) {
            Machine machine = new Machine();
            machine.setHostname("hostname"+i);
            machine.setName("machine"+i);
            machine.setLastSeen(new Date());

            machines.add(machine);
        }
        machineRepository.saveAll(machines);
    }
}
