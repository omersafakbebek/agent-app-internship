package com.example.softwareTracker.repository;

import com.example.softwareTracker.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    List<Machine> findAllByOrderByHostnameAsc();

    Optional<Machine> getMachineByHostname(String hostname);

    List<Machine> findByHostnameIsContainingOrNameIsContaining(String searchKey, String searchKey2);
}
