package com.raf.raf_cloud_back.dto;

import com.raf.raf_cloud_back.model.Machine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineResponse {
    private Long id;
    private String name;
    private String type;
    private String description;
    private String uuid;
    private Machine.MachineStatus status;
    private Boolean active;
    private LocalDateTime createdAt;
    private Long createdByUserId;
    private String createdByUserEmail;
    private Boolean busy;
}

