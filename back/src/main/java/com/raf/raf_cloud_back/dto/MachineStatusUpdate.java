package com.raf.raf_cloud_back.dto;

import com.raf.raf_cloud_back.model.Machine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineStatusUpdate {
    private Long machineId;
    private Machine.MachineStatus status;
    private Boolean busy;
}


