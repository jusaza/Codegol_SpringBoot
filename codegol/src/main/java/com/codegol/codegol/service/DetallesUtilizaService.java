package com.codegol.codegol.service;

import com.codegol.codegol.model.DetallesUtiliza;
import com.codegol.codegol.model.Inventario;
import com.codegol.codegol.repository.DetallesUtilizaRespository;
import com.codegol.codegol.repository.InventarioRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Service
public class DetallesUtilizaService {

    private final DetallesUtilizaRespository repository;
    private final InventarioRespository inventarioRepository;

    public DetallesUtilizaService(DetallesUtilizaRespository repository,
                                  InventarioRespository inventarioRepository) {
        this.repository = repository;
        this.inventarioRepository = inventarioRepository;
    }

    // ============================================================
    // LISTAR
    // ============================================================
    public List<DetallesUtiliza> listar() {
        return repository.findAll();
    }

    public DetallesUtiliza obtenerPorId(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<DetallesUtiliza> listarPorEntrenamiento(int idEntrenamiento) {
        return repository.findByEntrenamiento(idEntrenamiento);
    }

    // ============================================================
    // REGISTRAR USO
    // ============================================================
    public DetallesUtiliza registrarUso(DetallesUtiliza detalles) {

        Inventario inv = detalles.getInventario();

        // Guardar cantidad inicial ANTES de usar
        detalles.setCantidad_inicial(inv.getCantidad_total());

        // Validar disponibilidad
        if (detalles.getCantidad_usada() > inv.getCantidad_total()) {
            throw new IllegalArgumentException("La cantidad usada supera el inventario disponible.");
        }

        // Restar del inventario
        inv.setCantidad_total(inv.getCantidad_total() - detalles.getCantidad_usada());
        inventarioRepository.save(inv);

        detalles.setDevuelto(false);
        detalles.setCantidad_devuelta(0);

        return repository.save(detalles);
    }

    // ============================================================
    // ACTUALIZAR USO (si se equivocó)
    // ============================================================
    public void guardarMasivo(int idEntrenamiento, Map<String, String> params) {

        List<DetallesUtiliza> usos = listarPorEntrenamiento(idEntrenamiento);

        for (DetallesUtiliza u : usos) {

            // SI YA FUE DEVUELTO, NO SE TOCA
            if (u.isDevuelto()) continue;

            String keyUsado = "usado_" + u.getId_utiliza();
            String keyDevuelto = "devuelto_" + u.getId_utiliza();
            String keyObs = "obs_" + u.getId_utiliza();

            int nuevoUsado = Integer.parseInt(params.get(keyUsado));
            int nuevoDevuelto = Integer.parseInt(params.get(keyDevuelto));
            String nuevaObs = params.get(keyObs);

            // Recalcular inventario
            Inventario inv = u.getInventario();

            int nueva_existencia = u.getCantidad_inicial()
                    - nuevoUsado
                    + nuevoDevuelto;

            if (nueva_existencia < 0) {
                throw new IllegalArgumentException("Error en registro ID " + u.getId_utiliza()
                        + ": cantidades inválidas.");
            }

            inv.setCantidad_total(nueva_existencia);
            inventarioRepository.save(inv);

            // Aplicar cambios
            u.setCantidad_usada(nuevoUsado);
            u.setCantidad_devuelta(nuevoDevuelto);
            u.setObservaciones(nuevaObs);

            repository.save(u);
        }
    }


    // -------------------------------------------------------------
    // MARCAR DEVUELTO
    // -------------------------------------------------------------
        public void marcarDevuelto(int id) {
            DetallesUtiliza d = obtenerPorId(id);
            if (d == null) return;

            d.setDevuelto(true);
            repository.save(d);
        }

        // -------------------------------------------------------------
    // DESMARCAR DEVUELTO (permite editar)
    // -------------------------------------------------------------
        public void desmarcarDevuelto(int id) {
            DetallesUtiliza d = obtenerPorId(id);
            if (d == null) return;

            d.setDevuelto(false);
            repository.save(d);
        }



}

