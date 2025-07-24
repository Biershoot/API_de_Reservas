package com.barberbooking.barberreservationapi.util;

import com.barberbooking.barberreservationapi.entity.Reservation;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;

@Component
public class InvoicePdfGenerator {

    public byte[] generate(Reservation reservation) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Factura de Reserva"));
        document.add(new Paragraph("Cliente: " + reservation.getClient().getName()));
        document.add(new Paragraph("Barbero: " + reservation.getBarber().getUser().getName()));
        document.add(new Paragraph("Servicio: " + reservation.getService().getName()));
        document.add(new Paragraph("Fecha: " + reservation.getDate()));
        document.add(new Paragraph("Hora: " + reservation.getTime()));
        document.add(new Paragraph("Precio: $" + reservation.getService().getPrice()));

        document.close();
        return out.toByteArray();
    }
} 