package com.raf.raf_cloud_back.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineCreateRequest {

    @NotBlank(message = "Naziv masine je obavezan")
    private String name;

    private String type;

    private String description;
}

