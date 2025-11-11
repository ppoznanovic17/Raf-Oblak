package com.raf.raf_cloud_back.service.implementation;

import com.raf.raf_cloud_back.dto.MachineCreateRequest;
import com.raf.raf_cloud_back.dto.MachineResponse;
import com.raf.raf_cloud_back.dto.MachineSearchRequest;
import com.raf.raf_cloud_back.model.Machine;
import com.raf.raf_cloud_back.model.User;
import com.raf.raf_cloud_back.repository.MachineRepository;
import com.raf.raf_cloud_back.repository.UserRepository;
import com.raf.raf_cloud_back.service.MachineService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MachineServiceImpl implements MachineService {

    private final UserRepository userRepository;
    private final MachineRepository machineRepository;

    public MachineServiceImpl (UserRepository userRepository, MachineRepository machineRepository) {
        this.userRepository = userRepository;
        this.machineRepository = machineRepository;
    }

    public List<MachineResponse> searchMachines(Long userId, List<String> permissions, MachineSearchRequest searchRequest) {
        boolean isAdmin = permissions.containsAll(Arrays.asList(
                "can_create_users", "can_read_users", "can_update_users", "can_delete_users"
        ));

        Specification<Machine> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isTrue(root.get("active")));

            if (!isAdmin) {
                predicates.add(criteriaBuilder.equal(root.get("createdBy").get("id"), userId));
            }

            //  po imenu
            if (searchRequest.getName() != null && !searchRequest.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + searchRequest.getName().toLowerCase() + "%"
                ));
            }

            // po statusima
            if (searchRequest.getStatuses() != null && !searchRequest.getStatuses().isEmpty()) {
                predicates.add(root.get("status").in(searchRequest.getStatuses()));
            }

            // po datumu
            if (searchRequest.getDateFrom() != null && searchRequest.getDateTo() != null) {
                predicates.add(criteriaBuilder.between(
                        root.get("createdAt"),
                        searchRequest.getDateFrom().atStartOfDay(),
                        searchRequest.getDateTo().atTime(23, 59, 59)
                ));
            } else if (searchRequest.getDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("createdAt"),
                        searchRequest.getDateFrom().atStartOfDay()
                ));
            } else if (searchRequest.getDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("createdAt"),
                        searchRequest.getDateTo().atTime(23, 59, 59)
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return machineRepository.findAll(spec).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MachineResponse createMachine(MachineCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronadjen"));

        Machine machine = new Machine();
        machine.setName(request.getName());
        machine.setType(request.getType());
        machine.setDescription(request.getDescription());
        machine.setStatus(Machine.MachineStatus.STOPPED);
        machine.setCreatedBy(user);

        machine = machineRepository.save(machine);
        return mapToResponse(machine);
    }

    public Machine getMachineById(Long id) {
        return machineRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Masina nije pronadjena"));
    }

    private MachineResponse mapToResponse(Machine machine) {
        return new MachineResponse(
                machine.getId(),
                machine.getName(),
                machine.getType(),
                machine.getDescription(),
                machine.getUuid(),
                machine.getStatus(),
                machine.getActive(),
                machine.getCreatedAt(),
                machine.getCreatedBy().getId(),
                machine.getCreatedBy().getEmail(),
                machine.getBusy()
        );
    }
}
