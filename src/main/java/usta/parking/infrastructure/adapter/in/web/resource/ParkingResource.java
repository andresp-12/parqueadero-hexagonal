package usta.parking.infrastructure.adapter.in.web.resource;

import usta.parking.domain.exception.BusinessException;
import usta.parking.domain.model.*;
import usta.parking.application.port.in.*;
import usta.parking.infrastructure.adapter.in.web.report.PdfReport;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/")
@Tag(name = "Parqueadero", description = "Vistas HTML del control del parqueadero")
public class ParkingResource {
    // El nombre de cada campo coincide con el archivo de templates/ (index.html, entry.html, report.html)
    @Inject RegisterEntryUseCase registerEntry;
    @Inject RegisterExitUseCase registerExit;
    @Inject QueryParkingUseCase query;
    @Inject Template index;
    @Inject Template entry;
    @Inject Template report;

    @GET @Produces(MediaType.TEXT_HTML)
    @Operation(summary = "Página principal: cupos y tickets")
    public TemplateInstance home(@QueryParam("ok") String ok, @QueryParam("error") String error) {
        return index.data("tickets", query.listTickets()).data("spots", query.listSpots())
                .data("summary", query.summary()).data("ok", ok).data("error", error);
    }

    @GET @Path("/entry") @Produces(MediaType.TEXT_HTML)
    @Operation(summary = "Formulario de ingreso de un vehículo")
    public TemplateInstance entryForm(@QueryParam("error") String error) {
        return entry.data("types", VehicleType.values()).data("summary", query.summary())
                .data("ok", null).data("error", error);
    }

    @POST @Path("/tickets")
    @Operation(summary = "Registrar el ingreso de un vehículo")
    public Response create(@FormParam("plate") String plate, @FormParam("type") VehicleType type) {
        try {
            ParkingTicket t = registerEntry.execute(plate, type);
            return redirect("/", "ok", "Ingreso registrado: " + t.plate() + " en el cupo " + t.spot().code());
        } catch (BusinessException e) {
            return redirect("/entry", "error", e.getMessage());
        }
    }

    @POST @Path("/tickets/{id}/exit")
    @Operation(summary = "Registrar la salida y calcular el cobro")
    public Response exit(@PathParam("id") Long id) {
        try {
            ParkingTicket t = registerExit.execute(id);
            return redirect("/", "ok", "Salida de " + t.plate() + ". Total a cobrar: " + String.format("$ %,d", t.amount()));
        } catch (BusinessException e) {
            return redirect("/", "error", e.getMessage());
        }
    }

    @GET @Path("/tickets/{id}/receipt") @Produces("application/pdf")
    @Operation(summary = "Descargar el comprobante PDF de un ticket cobrado")
    public Response receipt(@PathParam("id") Long id) {
        ParkingTicket t = query.findTicket(id);
        if (t.status() != ParkingTicket.Status.CLOSED) return redirect("/", "error", "El ticket aún no ha sido cobrado");
        return Response.ok(PdfReport.receipt(t))
                .header("Content-Disposition", "attachment; filename=\"ticket-" + id + ".pdf\"").build();
    }

    @GET @Path("/report") @Produces(MediaType.TEXT_HTML)
    @Operation(summary = "Resumen y tarifas del parqueadero")
    public TemplateInstance reportPage() {
        return report.data("summary", query.summary()).data("active", query.listActiveTickets())
                .data("types", VehicleType.values()).data("ok", null).data("error", null);
    }

    @GET @Path("/report/pdf") @Produces("application/pdf")
    @Operation(summary = "Descargar el reporte PDF de tickets")
    public Response pdf() {
        return Response.ok(PdfReport.tickets(query.listTickets(), query.summary()))
                .header("Content-Disposition", "attachment; filename=\"parqueadero.pdf\"").build();
    }

    private Response redirect(String path, String key, String msg) {
        return Response.seeOther(UriBuilder.fromPath(path).queryParam(key, msg).build()).build();
    }
}
