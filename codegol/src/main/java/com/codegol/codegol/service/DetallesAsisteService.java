package com.codegol.codegol.service;

import com.codegol.codegol.model.DetallesAsiste;
import com.codegol.codegol.repository.DetallesAsisteRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallesAsisteService {

    private final DetallesAsisteRespository repository;

    public DetallesAsisteService(DetallesAsisteRespository repository) {
        this.repository = repository;
    }

    public DetallesAsiste guardar(DetallesAsiste detalles) {
        return repository.save(detalles);
    }

    public List<DetallesAsiste> listar() {
        return repository.findAll();
    }
}
