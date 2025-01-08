package ro.tuc.ds2020.dtos;

import java.math.BigDecimal;

public class HourConsumptionDTO {
    private int hour;
    private BigDecimal totalConsumption;

    public HourConsumptionDTO(int hour, BigDecimal totalConsumption) {
        this.hour = hour;
        this.totalConsumption = totalConsumption;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public BigDecimal getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(BigDecimal totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}
