package com.raf.raf_cloud_back.service;

import com.raf.raf_cloud_back.dto.MachineCreateRequest;
import com.raf.raf_cloud_back.dto.MachineResponse;
import com.raf.raf_cloud_back.dto.MachineSearchRequest;
import com.raf.raf_cloud_back.model.Machine;

import java.util.List;

public interface MachineService {

    public List<MachineResponse> searchMachines(Long userId, List<String> permissions, MachineSearchRequest searchRequest);

    public MachineResponse createMachine(MachineCreateRequest request, Long userId);

    public Machine getMachineById(Long id);
}
