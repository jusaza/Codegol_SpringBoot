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

    // PDF de matrículas
    @GetMapping("/reporte/matriculas/pdf")
    public ResponseEntity<byte[]> generarReporteMatriculas() throws Exception {
        byte[] pdf = reporteService.generarPDFMatriculas();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=matriculas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


    // PDF de pagos
    @GetMapping("/reporte/pago/pdf")
    public ResponseEntity<byte[]> generarReportePagos() throws Exception {
        byte[] pdf = reporteService.generarPDFPagos();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pagos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}


