package ams.business.repositories;

import ams.business.model.Mechanic;
import ams.client.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface MechanicRepository extends CrudRepository<Mechanic, UUID>{
    Mechanic getMechanicByPhoneNumber(String phoneNumber);
//    void addAppointmentHours(LocalDate date,String startTime, String endTime);
//    void addAppointmentHours(List<LocalDate> dateList, String startTime, String endTime);
//
//    void addAppointmentHours(List<LocalDate> dateList, List<String> startTime, List<String> endTime);
//
//    Boolean isAppointmentSlotAvailable(String slotTime);
//
//    void addServiceHours(LocalDate date,String startTime, String endTime);
//    void addServiceHours(List<LocalDate> dateList, String startTime, String endTime);
//
//    void addServiceHours(List<LocalDate> dateList, List<String> startTime, List<String> endTime);
}
