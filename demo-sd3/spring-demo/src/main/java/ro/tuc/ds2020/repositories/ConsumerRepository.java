package ro.tuc.ds2020.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.tuc.ds2020.entities.Consumer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsumerRepository extends JpaRepository<Consumer, UUID> {

    List<Consumer> findById(String id);
    Optional<Consumer> findByDeviceId(UUID deviceId);
    @Query("SELECT DISTINCT c.deviceId FROM Consumer c")
    List<UUID> findDistinctDeviceIds();

    @Query("SELECT c.measurementValue FROM Consumer c WHERE c.deviceId = :deviceId ")
    BigDecimal findLatestMeasurementValueByDeviceId(UUID deviceId);

}
