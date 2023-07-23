package ams.business.services;

import ams.business.model.DayDateTime;
import ams.business.model.Mechanic;
import ams.business.model.Appointment;
import ams.business.model.TimeSlot;
import ams.business.repositories.AppointmentRepository;
import ams.client.model.Customer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Data
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MechanicService mechanicService;

    public Map<Mechanic, List<String>> getAvailableSlots(LocalDate date) {
        List<Mechanic> mechanics = mechanicService.getAllMechanics();
        Map<Mechanic, List<String>> mechanicAvailableSlots = new HashMap<>();
        for (Mechanic mechanic : mechanics) {
            mechanicAvailableSlots.put(mechanic, getAvailableSlotsForMechanic(date, mechanic));
        }
        return mechanicAvailableSlots;
    }

    public List<String> getAvailableSlotsForMechanic(LocalDate date, Mechanic mechanic) {
        List<String> availableSlots = new ArrayList<>();

        DayDateTime dayDateTime = mechanic.getDailyTimeMap().get(date);

        if (dayDateTime != null) {
            for (TimeSlot timeSlot : dayDateTime.getTimeMap().values()) {
                if (timeSlot.isAppointment()) {
                    availableSlots.add(timeSlot.getTime());
                }
            }
        }

        return availableSlots;
    }


    // private static final Object sharedLock = new Object(); // Shared lock object across all instances

    public void bookAppointment(LocalDate date, String slotTime, Customer customer, Mechanic mechanic) {
        if (mechanicService.isAppointmentSlotAvailable(mechanic, date, slotTime)) {
            Appointment appointment = new Appointment();
            appointment.setDate(date);
            appointment.setSlotTime(slotTime);
            appointment.setMechanic(mechanic);
            appointment.setCustomer(customer);

            Map<LocalDate, DayDateTime> dailyTimeMap = mechanic.getDailyTimeMap();
            DayDateTime dayDateTime = dailyTimeMap.get(date);

            if (dayDateTime == null) {
                dayDateTime = new DayDateTime(date);
                dailyTimeMap.put(date, dayDateTime);
            }

            Map<String, TimeSlot> timeMap = dayDateTime.getTimeMap();
            TimeSlot timeSlot = timeMap.get(slotTime);

            if (timeSlot == null || !timeSlot.isAppointment()) {
                // This should not happen if the timeMap is properly initialized.
                throw new IllegalStateException("Selected appointment slot is not available");
            }

            appointmentRepository.save(appointment);
            timeSlot.setAppointment(false);
        } else {
            throw new IllegalStateException("Selected appointment slot is not available");
        }
    }


    public Appointment getAppointmentById(UUID appointmentId) {
        return appointmentRepository.findByAppointmentId(appointmentId);
    }

    public void updateAppointment(UUID appointmentId, Appointment updatedAppointment) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setDate(updatedAppointment.getDate());
            appointment.setSlotTime(updatedAppointment.getSlotTime());
            appointment.setMechanic(updatedAppointment.getMechanic());
            appointment.setCustomer(updatedAppointment.getCustomer());
            appointmentRepository.save(appointment);
        } else {
            throw new IllegalArgumentException("Appointment not found with ID: " + appointmentId);
        }
    }

    public void deleteAppointment(UUID appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
}
