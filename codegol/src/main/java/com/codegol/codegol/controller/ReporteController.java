package com.codegol.codegol.controller;

import com.codegol.codegol.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> generarReporte() throws Exception {
        byte[] pdf = reporteService.generarPDF();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=matriculas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}

