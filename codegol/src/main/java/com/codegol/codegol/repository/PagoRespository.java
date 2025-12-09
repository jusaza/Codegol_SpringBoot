package com.codegol.codegol.repository;

import com.codegol.codegol.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRespository extends JpaRepository<Pago, Integer> {

}
