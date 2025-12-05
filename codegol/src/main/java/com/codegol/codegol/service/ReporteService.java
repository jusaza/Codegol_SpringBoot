package com.codegol.codegol.service;

import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.repository.MatriculaRespository;
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

    public byte[] generarPDF() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        // Título
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Reporte de Matrículas", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        // Tabla
        PdfPTable table = new PdfPTable(4); // número de columnas
        table.addCell("ID");
        table.addCell("Fecha matrícula");
        table.addCell("Categoría");
        table.addCell("Nivel");

        List<Matricula> lista = matriculaRepository.findAll();
        for (Matricula m : lista) {
            table.addCell(String.valueOf(m.getId_matricula()));
            table.addCell(m.getFecha_matricula().toString());
            table.addCell(m.getCategoria());
            table.addCell(m.getNivel().name());
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}

