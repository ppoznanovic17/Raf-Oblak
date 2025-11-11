package com.raf.raf_cloud_back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLogCreateRequest {

    private Long machineId;
    private String operationType;
    private String errorMessage;

}
