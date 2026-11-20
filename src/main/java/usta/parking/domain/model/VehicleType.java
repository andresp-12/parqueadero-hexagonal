package usta.parking.domain.model;

/** Tipo de vehículo y su tarifa por hora (en pesos). */
public enum VehicleType {
    CAR("Carro", 3000), MOTORCYCLE("Moto", 1500);

    private final String label;
    private final long hourlyRate;

    VehicleType(String label, long hourlyRate) { this.label = label; this.hourlyRate = hourlyRate; }
    public String label() { return label; }
    public long hourlyRate() { return hourlyRate; }
}
