package ams.business.services;

import ams.business.model.Appointment;
import ams.business.model.DayDateTime;
import ams.business.model.Mechanic;
import ams.business.repositories.AppointmentRepository;
import ams.business.services.AppointmentService;
import ams.client.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private MechanicService mechanicService;

    private AppointmentService appointmentService;

    private Mechanic mechanic;
    private Customer customer;
    private LocalDate appointmentDate;
    private String appointmentSlotTime;

    @BeforeEach
    public void setUp() {
        appointmentService = new AppointmentService();
        appointmentService.setAppointmentRepository(appointmentRepository);
        appointmentService.setMechanicService(mechanicService);

        mechanic = new Mechanic();
        customer = new Customer();
        appointmentDate = LocalDate.of(2023, 7, 11);
        appointmentSlotTime = "10:00";
    }

    @Test
    public void getAvailableSlots() {
        LocalDate appointmentDate = LocalDate.now();
        String appointmentSlotTime = "10:00";

        List<Mechanic> mechanics = Arrays.asList(new Mechanic(), new Mechanic(), new Mechanic());
        when(mechanicService.getAllMechanics()).thenReturn(mechanics);

        Map<Mechanic, List<String>> mechanicAvailableSlots = new HashMap<>();
        for (Mechanic mechanic : mechanics) {
            DayDateTime dayDateTime = new DayDateTime(appointmentDate);
            mechanic.getDailyTimeMap().put(appointmentDate, dayDateTime);

            // Assuming that the time slot is available, we set "Appointment" to true for the slot
            dayDateTime.getTimeMap().get(appointmentSlotTime).setAppointment(true);

            List<String> availableSlots = new ArrayList<>();
            availableSlots.add(appointmentSlotTime);
            mechanicAvailableSlots.put(mechanic, availableSlots);
        }

        Map<Mechanic, List<String>> availableSlots = appointmentService.getAvailableSlots(appointmentDate);
        assertEquals(mechanicAvailableSlots, availableSlots);
    }


    @Test
    public void getAvailableSlotsForMechanic() {
        LocalDate appointmentDate = LocalDate.now();
        String appointmentSlotTime = "10:00";

        Mechanic mechanic = new Mechanic();
        DayDateTime dayDateTime = new DayDateTime(appointmentDate);
        mechanic.getDailyTimeMap().put(appointmentDate, dayDateTime);

        // Assuming that the time slot is available, we set "Appointment" to true for the slot
        dayDateTime.getTimeMap().get(appointmentSlotTime).setAppointment(true);

        List<String> availableSlots = appointmentService.getAvailableSlotsForMechanic(appointmentDate, mechanic);
        assertEquals(Collections.singletonList(appointmentSlotTime), availableSlots);
    }


    @Test
    public void bookAppointment_slotAvailable() {
        LocalDate appointmentDate = LocalDate.now();
        String appointmentSlotTime = "10:00";

        Mechanic mechanic = new Mechanic();
        Customer customer = new Customer();

        DayDateTime dayDateTime = new DayDateTime(appointmentDate);
        mechanic.getDailyTimeMap().put(appointmentDate, dayDateTime);

        // Assuming that the time slot is available, we set "Appointment" to true for the slot
        dayDateTime.getTimeMap().get(appointmentSlotTime).setAppointment(true);

        // We create an appointment object to be saved and returned by the repository
        Appointment appointment = new Appointment();
        appointment.setDate(appointmentDate);
        appointment.setSlotTime(appointmentSlotTime);
        appointment.setMechanic(mechanic);
        appointment.setCustomer(customer);

        when(mechanicService.isAppointmentSlotAvailable(mechanic, appointmentDate, appointmentSlotTime)).thenReturn(true);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        appointmentService.bookAppointment(appointmentDate, appointmentSlotTime, customer, mechanic);

        // Verify that the appointment is saved once
        verify(appointmentRepository, times(1)).save(any(Appointment.class));

        // Ensure that the appointment slot is set to false after booking
        assertFalse(mechanic.getDailyTimeMap().get(appointmentDate).getTimeMap().get(appointmentSlotTime).isAppointment());
    }



    @Test
    public void bookAppointment_slotNotAvailable() {
        when(mechanicService.isAppointmentSlotAvailable(mechanic, appointmentDate, appointmentSlotTime)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> {
            appointmentService.bookAppointment(LocalDate.parse(appointmentDate.toString()), appointmentSlotTime, customer, mechanic);
        });

        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    public void getAppointmentById_existingAppointment() {
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);
        when(appointmentRepository.findByAppointmentId(appointmentId)).thenReturn(appointment);

        Appointment retrievedAppointment = appointmentService.getAppointmentById(appointmentId);
        assertEquals(appointment, retrievedAppointment);
    }

    @Test
    public void getAppointmentById_nonExistingAppointment() {
        UUID appointmentId = UUID.randomUUID();
        when(appointmentRepository.findByAppointmentId(appointmentId)).thenReturn(null);

        Appointment retrievedAppointment = appointmentService.getAppointmentById(appointmentId);
        assertNull(retrievedAppointment);
    }

    @Test
    public void updateAppointment_existingAppointment() {
        UUID appointmentId = UUID.randomUUID();
        Appointment existingAppointment = new Appointment();
        existingAppointment.setAppointmentId(appointmentId);
        when(appointmentRepository.findByAppointmentId(appointmentId)).thenReturn(existingAppointment);

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setDate(appointmentDate);
        updatedAppointment.setSlotTime(appointmentSlotTime);
        updatedAppointment.setMechanic(mechanic);
        updatedAppointment.setCustomer(customer);

        appointmentService.updateAppointment(appointmentId, updatedAppointment);

        verify(appointmentRepository, times(1)).save(existingAppointment);
        assertEquals(appointmentDate, existingAppointment.getDate());
        assertEquals(appointmentSlotTime, existingAppointment.getSlotTime());
        assertEquals(mechanic, existingAppointment.getMechanic());
        assertEquals(customer, existingAppointment.getCustomer());
    }

    @Test
    public void updateAppointment_nonExistingAppointment() {
        UUID appointmentId = UUID.randomUUID();
        when(appointmentRepository.findByAppointmentId(appointmentId)).thenReturn(null);

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setDate(appointmentDate);
        updatedAppointment.setSlotTime(appointmentSlotTime);
        updatedAppointment.setMechanic(mechanic);
        updatedAppointment.setCustomer(customer);

        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.updateAppointment(appointmentId, updatedAppointment);
        });

        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    public void deleteAppointment() {
        UUID appointmentId = UUID.randomUUID();

        appointmentService.deleteAppointment(appointmentId);

        verify(appointmentRepository, times(1)).deleteById(appointmentId);
    }
}