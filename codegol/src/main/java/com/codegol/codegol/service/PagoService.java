package com.codegol.codegol.service;

import com.codegol.codegol.model.Pago;
import com.codegol.codegol.repository.PagoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRespository pagoRespository;

    public List<Pago> listarTodos() {
        return pagoRespository.findAll();
    }

    public Pago buscarPorId(Integer id) {
        return pagoRespository.findById(id).orElse(null);
    }

    public void guardar(Pago pago) {
        pagoRespository.save(pago);
    }

    public void eliminar(Integer id) {
        pagoRespository.deleteById(id);
    }
}
