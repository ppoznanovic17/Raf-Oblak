package com.raf.raf_cloud_back.controller;

import com.raf.raf_cloud_back.dto.ErrorLogCreateRequest;
import com.raf.raf_cloud_back.dto.ErrorLogResponse;
import com.raf.raf_cloud_back.model.ErrorLog;
import com.raf.raf_cloud_back.security.RequiresPermission;
import com.raf.raf_cloud_back.service.ErrorLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/errors")
@CrossOrigin(origins = "http://localhost:4200")
public class ErrorLogController {
    private  final ErrorLogService errorLogService;

    public ErrorLogController (ErrorLogService errorLogService){
        this.errorLogService = errorLogService;
    }
    @GetMapping
    @RequiresPermission("can_search_machines") // Bilo koja machine permission
    public ResponseEntity<List<ErrorLogResponse>> getErrorLogs(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) request.getAttribute("permissions");

        return ResponseEntity.ok(errorLogService.getErrorLogs(userId, permissions));
    }

    @PostMapping
    @RequiresPermission("can_search_machines") // Bilo koja machine permission
    public ResponseEntity<ErrorLog> createError(
            @RequestBody ErrorLogCreateRequest errorLogCreateRequest,
            HttpServletRequest request) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setMachineId(errorLogCreateRequest.getMachineId());
        errorLog.setOperation(errorLogCreateRequest.getOperationType());
        errorLog.setErrorMessage(errorLogCreateRequest.getErrorMessage());
        System.out.println(errorLog);
        return ResponseEntity.ok(errorLogService.createError(errorLog));
    }
}
