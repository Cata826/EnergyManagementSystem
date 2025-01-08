package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByDescription(String description);
    List<Device> findByPersonReferenceId(UUID personReferenceId);
    List<Device> findByPersonReference_PersonId(UUID personId);
    List<Device> findAllByPersonReferenceId(UUID personReferenceId);
    List<Device> findByAddress(String address);
    Optional<Device> findById(UUID id);
}
