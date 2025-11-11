package com.raf.raf_cloud_back.repository;

import com.raf.raf_cloud_back.model.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
    List<ErrorLog> findByMachineIdIn(List<Long> machineIds);
}
