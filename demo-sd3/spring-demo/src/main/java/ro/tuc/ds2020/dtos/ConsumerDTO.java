package ro.tuc.ds2020.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ConsumerDTO extends RepresentationModel<ConsumerDTO> {

    private UUID id;
    @NotNull
    private UUID deviceId;
    @NotNull
    private BigDecimal measurementValue;

    public ConsumerDTO() {
    }

    public ConsumerDTO(UUID deviceId, BigDecimal measurementValue) {
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }
    public ConsumerDTO(UUID id, UUID deviceId, BigDecimal measurementValue) {
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
        ConsumerDTO that = (ConsumerDTO) o;
        return Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(measurementValue, that.measurementValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, measurementValue);
    }
}
