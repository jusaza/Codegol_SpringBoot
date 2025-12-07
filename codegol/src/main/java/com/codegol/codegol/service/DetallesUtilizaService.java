package com.codegol.codegol.service;

import com.codegol.codegol.model.DetallesUtiliza;
import com.codegol.codegol.repository.DetallesUtilizaRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallesUtilizaService {

    private final DetallesUtilizaRespository repository;

    public DetallesUtilizaService(DetallesUtilizaRespository repository) {
        this.repository = repository;
    }

    public DetallesUtiliza guardar(DetallesUtiliza detalles) {
        return repository.save(detalles);
    }

    public List<DetallesUtiliza> listar() {
        return repository.findAll();
    }
}
