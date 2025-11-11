package com.raf.raf_cloud_back.controller;

import com.raf.raf_cloud_back.dto.MachineCreateRequest;
import com.raf.raf_cloud_back.dto.MachineResponse;
import com.raf.raf_cloud_back.dto.MachineSearchRequest;
import com.raf.raf_cloud_back.security.RequiresPermission;
import com.raf.raf_cloud_back.service.MachineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@CrossOrigin(origins = "http://localhost:4200")
public class MachineController {

    private final MachineService machineService;

    public MachineController (MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping("/search")
    @RequiresPermission("can_search_machines")
    public ResponseEntity<List<MachineResponse>> searchMachines(
            @RequestBody(required = false) MachineSearchRequest searchRequest,
            HttpServletRequest request) {

        if (searchRequest == null) {
            searchRequest = new MachineSearchRequest();
        }

        Long userId = (Long) request.getAttribute("userId");

        List<String> permissions = (List<String>) request.getAttribute("permissions");

        return ResponseEntity.ok(machineService.searchMachines(userId, permissions, searchRequest));
    }

    @PostMapping
    @RequiresPermission("can_create_machines")
    public ResponseEntity<MachineResponse> createMachine(
            @Valid @RequestBody MachineCreateRequest createRequest,
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(machineService.createMachine(createRequest, userId));
    }
}
