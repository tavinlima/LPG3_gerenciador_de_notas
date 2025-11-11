package com.personal.sistema_notas.service;

import com.personal.sistema_notas.domain.Status;
import com.personal.sistema_notas.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status getById(Integer id) {
        return statusRepository.findById(id).orElseThrow(() -> new RuntimeException("Status não encontrado"));
    }
}
