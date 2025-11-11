package com.raf.raf_cloud_back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLogResponse {
    private Long id;
    private LocalDateTime timestamp;
    private Long machineId;
    private String operation;
    private String machineName;
    private String errorMessage;

}


