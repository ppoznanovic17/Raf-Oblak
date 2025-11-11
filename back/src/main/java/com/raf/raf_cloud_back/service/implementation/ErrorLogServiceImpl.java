package com.raf.raf_cloud_back.service.implementation;

import com.raf.raf_cloud_back.dto.ErrorLogResponse;
import com.raf.raf_cloud_back.model.ErrorLog;
import com.raf.raf_cloud_back.model.Machine;
import com.raf.raf_cloud_back.repository.ErrorLogRepository;
import com.raf.raf_cloud_back.repository.MachineRepository;
import com.raf.raf_cloud_back.service.ErrorLogService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErrorLogServiceImpl implements ErrorLogService {

    private final ErrorLogRepository errorLogRepository;

    private final MachineRepository machineRepository;

    public  ErrorLogServiceImpl(ErrorLogRepository errorLogRepository, MachineRepository machineRepository){
        this.errorLogRepository = errorLogRepository;
        this.machineRepository = machineRepository;
    }

    public List<ErrorLogResponse> getErrorLogs(Long userId, List<String> permissions) {
        boolean isAdmin = permissions.containsAll(Arrays.asList(
                "can_create_users", "can_read_users", "can_update_users", "can_delete_users"
        ));

        List<ErrorLog> errorLogs;

        if (isAdmin) {

            errorLogs = errorLogRepository.findAll();
        } else {

            List<Long> userMachineIds = machineRepository.findByCreatedByIdAndActiveTrue(userId)
                    .stream()
                    .map(Machine::getId)
                    .collect(Collectors.toList());

            if (userMachineIds.isEmpty()) {
                return Collections.emptyList();
            }

            errorLogs = errorLogRepository.findByMachineIdIn(userMachineIds);
        }

        return errorLogs.stream()
                .sorted(Comparator.comparing(ErrorLog::getTimestamp).reversed())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ErrorLog createError(ErrorLog errorLog) {
        return errorLogRepository.save(errorLog);
    }

    private ErrorLogResponse mapToResponse(ErrorLog errorLog) {
        String machineName = machineRepository.findById(errorLog.getMachineId())
                .map(Machine::getName)

                .orElse("Nepoznata masina");

        return new ErrorLogResponse(
                errorLog.getId(),
                errorLog.getTimestamp(),
                errorLog.getMachineId(),
                errorLog.getOperation(),
                machineName,
                errorLog.getErrorMessage()
        );
    }
}
