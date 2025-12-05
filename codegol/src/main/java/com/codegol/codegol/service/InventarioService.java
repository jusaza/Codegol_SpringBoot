package com.codegol.codegol.service;

import com.codegol.codegol.model.Inventario;
import com.codegol.codegol.repository.InventarioRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private InventarioRespository inventarioRepository;

    // Listar solo inventarios activos
    public List<Inventario> listarActivos() {
        return inventarioRepository.findByEstado(true);
    }

    // Búsqueda flexible por nombre del artículo
    public List<Inventario> buscarFlexible(String q) {
        return inventarioRepository.buscarFlexible(q);
    }

    // Guardar o actualizar
    public Inventario guardar(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    // Buscar por ID
    public Inventario obtenerPorId(int id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    // Eliminación lógica
    public void eliminar(int id) {
        Inventario inventario = inventarioRepository.findById(id).orElse(null);
        if (inventario != null) {
            inventario.setEstado(false);
            inventarioRepository.save(inventario);
        }
    }
}
