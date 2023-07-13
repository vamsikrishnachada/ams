package ams.business.repositories;

import ams.business.model.Mechanic;
import ams.business.model.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;



@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByDateAndMechanic(LocalDate date, Mechanic mechanic);
    List<Appointment> findByDateAndMechanicAndSlotTime(LocalDate date, Mechanic mechanic, String slotTime);
    Appointment findByAppointmentId(UUID appointmentId);
}
