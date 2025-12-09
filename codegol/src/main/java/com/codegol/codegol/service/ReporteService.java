package com.codegol.codegol.service;

import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.model.Pago;
import com.codegol.codegol.repository.MatriculaRespository;
import com.codegol.codegol.repository.PagoRespository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private MatriculaRespository matriculaRepository;

    @Autowired
    private PagoRespository pagoRepository;

    // Reporte PDF de matrículas
    public byte[] generarPDFMatriculas() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Reporte de Matrículas", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.addCell("ID");
        table.addCell("Fecha matrícula");
        table.addCell("Categoría");
        table.addCell("Nivel");

        List<Matricula> lista = matriculaRepository.findAll();

        if (lista.isEmpty()) {
            table.addCell("-");
            table.addCell("-");
            table.addCell("-");
            table.addCell("-");
        } else {
            for (Matricula m : lista) {
                table.addCell(String.valueOf(m.getId_matricula()));
                table.addCell(m.getFecha_matricula() != null ? m.getFecha_matricula().toString() : "N/A");
                table.addCell(m.getCategoria() != null ? m.getCategoria() : "N/A");
                table.addCell(m.getNivel() != null ? m.getNivel().name() : "N/A");
            }
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }


    // Reporte PDF de pagos
    public byte[] generarPDFPagos() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Reporte de Pagos", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5); // columnas: ID, Usuario, Matricula, Fecha, Valor
        table.addCell("ID");
        table.addCell("Usuario");
        table.addCell("Matrícula");
        table.addCell("Fecha Pago");
        table.addCell("Valor Total");

        List<Pago> lista = pagoRepository.findAll();
        for (Pago p : lista) {
            table.addCell(String.valueOf(p.getIdPago()));
            table.addCell(p.getUsuario() != null ? p.getUsuario().getNombre_completo() : "Sin usuario");
            table.addCell(p.getMatricula() != null ? String.valueOf(p.getMatricula().getId_matricula()) : "Sin matrícula");
            table.addCell(p.getFechaPago().toString());
            table.addCell(String.valueOf(p.getValorTotal()));
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}


