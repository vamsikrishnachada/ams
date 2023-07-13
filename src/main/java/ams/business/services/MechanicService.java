package ams.business.services;

import ams.business.model.DayDateTime;
import ams.business.model.Mechanic;
import ams.business.model.constants.TimeToIndex;
import ams.business.repositories.MechanicRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Data
public class MechanicService {

    @Autowired
    private MechanicRepository mechanicRepository;

    public MechanicService() {
        TimeToIndex.initialize();
    }

    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
        TimeToIndex.initialize();
    }

    public void addAppointmentHours(Mechanic mechanic, LocalDate date, String startTime, String endTime) {
        DayDateTime dayDateTime = mechanic.getDailyTimeMap().get(date);
        if (dayDateTime == null) {
            dayDateTime = new DayDateTime(date);
            mechanic.getDailyTimeMap().put(date, dayDateTime);
        }

        dayDateTime.setAppointmentHours(startTime, endTime);
    }


    public void addAppointmentHours(Mechanic mechanic, List<LocalDate> dateList, String startTime, String endTime) {
        for (LocalDate date : dateList) {
            DayDateTime dayDateTime = mechanic.getDailyTimeMap().get(date);
            if (dayDateTime == null) {
                dayDateTime = new DayDateTime(date);
                mechanic.getDailyTimeMap().put(date, dayDateTime);
            }

            dayDateTime.setAppointmentHours(startTime, endTime);
        }
    }


    public Boolean isAppointmentSlotAvailable(Mechanic mechanic, LocalDate date, String slotTime) {
        if (mechanic.getDailyTimeMap().get(date).getTimeMap().get(slotTime).get("Appointment") != null) {
            return mechanic.getDailyTimeMap().get(date).getTimeMap().get(slotTime).get("Appointment");
        } else {
            return Boolean.FALSE;
        }
    }

    public void addMechanic(Mechanic mechanic) {
        mechanicRepository.save(mechanic);
    }

    public void deleteMechanic(Mechanic mechanic) {
        mechanicRepository.delete(mechanic);
    }

    public Mechanic getMechanicByPhoneNumber(String phoneNumber) {
        return mechanicRepository.getMechanicByPhoneNumber(phoneNumber);
    }

    public List<Mechanic> getAllMechanics(){
        return StreamSupport.stream(mechanicRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}