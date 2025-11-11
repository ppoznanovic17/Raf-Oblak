package com.raf.raf_cloud_back.repository;

import com.raf.raf_cloud_back.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, Long>, JpaSpecificationExecutor<Machine> {
        List<Machine> findByCreatedByIdAndActiveTrue(Long userId);
        List<Machine> findByActiveTrue();
        Optional<Machine> findByIdAndActiveTrue(Long id);

}
