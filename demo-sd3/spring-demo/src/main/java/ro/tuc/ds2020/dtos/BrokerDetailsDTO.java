
package ro.tuc.ds2020.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class BrokerDetailsDTO {

    private UUID id;

    @NotNull
    @JsonProperty("device_id")
    private UUID deviceId;

    @NotNull
    private Timestamp timestamp;

    @NotNull
    @JsonProperty("measurement_value")
    private BigDecimal measurementValue;

    public BrokerDetailsDTO() {
    }

    public BrokerDetailsDTO(Timestamp timestamp, UUID deviceId, BigDecimal measurementValue) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.measurementValue = measurementValue;
    }

    public BrokerDetailsDTO(UUID id, Timestamp timestamp, UUID deviceId, BigDecimal measurementValue) {
        this.id = id;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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
        BrokerDetailsDTO that = (BrokerDetailsDTO) o;
        return Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(measurementValue, that.measurementValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, timestamp, measurementValue);
    }
}
