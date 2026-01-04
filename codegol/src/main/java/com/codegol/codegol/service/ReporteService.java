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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class ReporteService {

    @Autowired
    private MatriculaRespository matriculaRepository;

    @Autowired
    private PagoRespository pagoRepository;

    // Reporte PDF de Matrícula Individual
    public byte[] generarPDFMatriculaIndividual(int matriculaId) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        // Título y fuente
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Recuperamos la matrícula usando el id
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new Exception("Matrícula no encontrada"));

        // Agregar título
        Paragraph title = new Paragraph("Certificado de Matrícula", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        // Texto explicativo más largo
        Paragraph content = new Paragraph();
        content.add("Este documento certifica que el jugador " +
                (matricula.getId_jugador() != null ? matricula.getId_jugador().getNombre_completo() : "Desconocido") +
                ", identificado con el número de matrícula " + matricula.getId_matricula() +
                ", está oficialmente matriculado en la Escuela de Fútbol Codegol.");
        content.add("\n\nEl jugador ha sido asignado a la categoría " +
                (matricula.getCategoria() != null ? matricula.getCategoria() : "N/A") +
                " y al nivel " + (matricula.getNivel() != null ? matricula.getNivel().name() : "N/A") +
                ", que corresponde a su edad y habilidades. La matrícula fue realizada el " +
                (matricula.getFecha_matricula() != null ? matricula.getFecha_matricula().toString() : "N/A") +
                " y tiene una validez hasta el " +
                (matricula.getFecha_fin() != null ? matricula.getFecha_fin().toString() : "N/A") +
                ". Durante este periodo, el jugador podrá acceder a todas las instalaciones, entrenamientos y recursos de la escuela, siempre cumpliendo con las normas y políticas establecidas por la misma.");

        content.add("\n\nDetalles de la matrícula:");
        content.add("\n- **ID Matrícula:** " + matricula.getId_matricula());
        content.add("\n- **Fecha de Matrícula:** " + (matricula.getFecha_matricula() != null ? matricula.getFecha_matricula().toString() : "N/A"));
        content.add("\n- **Fecha de Inicio del Curso:** " + (matricula.getFecha_inicio() != null ? matricula.getFecha_inicio().toString() : "N/A"));
        content.add("\n- **Fecha de Fin del Curso:** " + (matricula.getFecha_fin() != null ? matricula.getFecha_fin().toString() : "N/A"));
        content.add("\n- **Categoría:** " + (matricula.getCategoria() != null ? matricula.getCategoria() : "N/A"));
        content.add("\n- **Nivel:** " + (matricula.getNivel() != null ? matricula.getNivel().name() : "N/A"));

        content.add("\n\n**Observaciones:** " + (matricula.getObservaciones() != null ? matricula.getObservaciones() : "N/A"));

        content.add("\n\nLa Escuela de Fútbol Codegol se compromete a brindar un ambiente de aprendizaje y desarrollo deportivo para cada uno de nuestros jugadores, con el objetivo de mejorar sus habilidades técnicas y tácticas, y fomentar el trabajo en equipo, la disciplina y el respeto dentro y fuera de la cancha.");

        content.add("\n\nEste certificado es válido para cualquier trámite relacionado con la matrícula y participación en las actividades y eventos de la escuela.");

        // Añadir el contenido al documento
        content.setFont(textFont);
        document.add(content);

        document.close();
        return out.toByteArray();
    }

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



    // Reporte PDF de Pago
    public byte[] generarPDFPagos() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        // Títulos
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph title = new Paragraph("Reporte de Pagos", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        Paragraph subtitle = new Paragraph("Este reporte incluye los pagos realizados hasta la fecha.", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);
        document.add(new Paragraph(" ")); // Espacio entre el título y la tabla

        // Tabla de pagos
        PdfPTable table = new PdfPTable(5); // 5 columnas: ID, Usuario, Matrícula, Fecha, Valor
        table.setWidths(new float[] { 1, 3, 1, 2, 1 }); // Define los anchos de las columnas
        table.addCell("ID");
        table.addCell("Usuario");
        table.addCell("Matrícula");
        table.addCell("Fecha Pago");
        table.addCell("Valor Total");

        // Formato de fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        double totalPagos = 0;

        List<Pago> lista = pagoRepository.findAll();
        for (Pago p : lista) {
            totalPagos += p.getValorTotal();

            // Verificación de fecha nula o incompatible
            String fechaPagoFormatted = "N/A"; // Valor predeterminado
            if (p.getFechaPago() != null) {
                // Si es un LocalDate, convertirlo a String con el formato adecuado
                fechaPagoFormatted = p.getFechaPago().format(formatter); // Usamos DateTimeFormatter para formatear LocalDate
            }

            // Agregar los datos a la tabla
            table.addCell(String.valueOf(p.getIdPago()));
            table.addCell(p.getUsuario() != null ? p.getUsuario().getNombre_completo() : "Sin usuario");
            table.addCell(p.getMatricula() != null ? String.valueOf(p.getMatricula().getId_matricula()) : "Sin matrícula");
            table.addCell(fechaPagoFormatted); // Fecha formateada
            table.addCell(String.valueOf(p.getValorTotal()));
        }

        // Fila de total acumulado
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("Total");
        table.addCell(String.valueOf(totalPagos));

        document.add(table);
        document.close();

        return out.toByteArray();
    }
    }


