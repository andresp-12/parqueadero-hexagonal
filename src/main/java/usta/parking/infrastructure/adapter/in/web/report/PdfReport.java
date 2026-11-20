package usta.parking.infrastructure.adapter.in.web.report;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import usta.parking.domain.model.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class PdfReport {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private PdfReport() {}

    private static String fmt(LocalDateTime d) { return d == null ? "-" : d.format(FMT); }
    private static String money(long v) { return String.format("$ %,d", v); }

    /** Listado general de tickets con total recaudado. */
    public static byte[] tickets(List<ParkingTicket> tickets, ParkingSummary summary) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(doc, out);
        doc.open();
        doc.add(new Paragraph("Reporte del parqueadero", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        long total = tickets.stream().mapToLong(ParkingTicket::amount).sum();
        doc.add(new Paragraph("Vehículos dentro: " + summary.activeTickets()
                + "   |   Cupos libres (carros/motos): " + summary.availableCars() + "/" + summary.availableMotorcycles()
                + "   |   Recaudo de hoy: " + money(summary.revenueToday())
                + "   |   Recaudo total: " + money(total) + "\n\n"));
        PdfPTable t = new PdfPTable(new float[]{1, 2, 1.5f, 1.5f, 3, 3, 2, 2});
        t.setWidthPercentage(100);
        for (String h : new String[]{"ID", "Placa", "Cupo", "Tipo", "Entrada", "Salida", "Total", "Estado"}) {
            PdfPCell c = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            c.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
            t.addCell(c);
        }
        for (ParkingTicket k : tickets) {
            t.addCell(String.valueOf(k.id())); t.addCell(k.plate()); t.addCell(k.spot().code());
            t.addCell(k.spot().type().label()); t.addCell(fmt(k.entryTime())); t.addCell(fmt(k.exitTime()));
            t.addCell(money(k.amount())); t.addCell(k.status() == ParkingTicket.Status.ACTIVE ? "Dentro" : "Cobrado");
        }
        doc.add(t);
        doc.close();
        return out.toByteArray();
    }

    /** Comprobante de pago de un ticket. */
    public static byte[] receipt(ParkingTicket k) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A5);
        PdfWriter.getInstance(doc, out);
        doc.open();
        doc.add(new Paragraph("Comprobante de parqueadero", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        doc.add(new Paragraph("\nTicket No. " + k.id() + "\nPlaca: " + k.plate()
                + "\nTipo: " + k.spot().type().label() + "   Cupo: " + k.spot().code()
                + "\nEntrada: " + fmt(k.entryTime()) + "\nSalida: " + fmt(k.exitTime())
                + "\nTarifa por hora: " + money(k.spot().type().hourlyRate()) + "\n"));
        doc.add(new Paragraph("TOTAL PAGADO: " + money(k.amount()), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        doc.close();
        return out.toByteArray();
    }
}
