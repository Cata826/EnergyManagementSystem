package ro.tuc.ds2020.dtos;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ConsumerDetailsDTO {

    private UUID id;
    @NotNull
    private UUID deviceId;
    @NotNull
    private BigDecimal measurementValue;
    public ConsumerDetailsDTO() {
    }
    public ConsumerDetailsDTO(UUID deviceId, BigDecimal measurementValue) {
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }
    public ConsumerDetailsDTO(UUID id, UUID deviceId, BigDecimal measurementValue) {
        this.id = id;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public BigDecimal getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(BigDecimal measurementValue) {
        this.measurementValue = measurementValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumerDetailsDTO that = (ConsumerDetailsDTO) o;
        return Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(measurementValue, that.measurementValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, measurementValue);
    }
}
