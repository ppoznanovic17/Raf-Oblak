package com.raf.raf_cloud_back.dto;

import com.raf.raf_cloud_back.model.Machine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineSearchRequest {
    private String name;
    private List<Machine.MachineStatus> statuses;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}


