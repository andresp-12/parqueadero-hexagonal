package usta.parking.infrastructure.adapter.in.web;

import usta.parking.domain.model.*;
import io.quarkus.qute.TemplateData;

/** Permite que Qute lea los objetos del dominio sin ensuciar el dominio con anotaciones. */
@TemplateData(target = ParkingSpot.class)
@TemplateData(target = ParkingTicket.class)
@TemplateData(target = ParkingTicket.Status.class)
@TemplateData(target = ParkingSummary.class)
@TemplateData(target = VehicleType.class)
public class TemplateConfig {}
