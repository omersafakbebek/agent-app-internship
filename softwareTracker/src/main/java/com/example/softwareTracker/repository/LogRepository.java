package com.example.softwareTracker.repository;

import com.example.softwareTracker.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> getLogsByTask_IdOrderByDateDesc(long taskId);

    List<Log> getLogsByTask_Machine_HostnameOrderByDateDesc(String hostname);

    List<Log> getLogsByOrderByDateDesc();
}
