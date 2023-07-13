package ams.client.repositories;

import ams.business.model.Mechanic;
import ams.client.model.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {

    List<Mechanic> mechanicsAvailableForSlot(LocalDate date, String slotTime);
    //Should write more

}
