package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Broker;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface BrokerRepository extends JpaRepository<Broker, UUID> {


    List<Broker> findById(String id);
    boolean existsByDeviceIdAndTimestamp(UUID deviceId, Timestamp timestamp);
    List<Broker> findByDeviceIdAndTimestampBetween(UUID deviceId, Timestamp startTime, Timestamp endTime);
    List<Broker> findByDeviceIdInAndTimestampBetween(List<UUID> deviceIds, Timestamp startTime, Timestamp endTime);
}
