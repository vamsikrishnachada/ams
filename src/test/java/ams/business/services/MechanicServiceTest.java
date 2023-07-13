package ams.business.services;

import ams.business.model.DayDateTime;
import ams.business.model.Mechanic;
import ams.business.model.constants.TimeToIndex;
import ams.business.repositories.MechanicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MechanicServiceTest {

    @Mock
    MechanicRepository mechanicRepository;

    MechanicService mechanicService;
    Mechanic mechanic;

    @BeforeEach
    public void setUp() {
        TimeToIndex.initialize();
        mechanicService = new MechanicService();
        mechanicService.setMechanicRepository(mechanicRepository);

        mechanic = new Mechanic();
        mechanic.setDailyTimeMap(new HashMap<>());
    }


    @Test
    public void addAppointmentHours_singleDate() {
        LocalDate date = LocalDate.of(2023, 7, 11);
        String startTime = "09:00";
        String endTime = "17:00";

        mechanicService.addAppointmentHours(mechanic, date, startTime, endTime);

        Map<LocalDate, DayDateTime> dailyTimeMap = mechanic.getDailyTimeMap();
        assertTrue(dailyTimeMap.containsKey(date));

        DayDateTime dayDateTime = dailyTimeMap.get(date);
        assertNotNull(dayDateTime);
        assertNotNull(dayDateTime.getTimeMap().get(startTime));
        assertNotNull(dayDateTime.getTimeMap().get(endTime));
        assertTrue(dayDateTime.getTimeMap().get(startTime).get("Appointment"));
        assertTrue(dayDateTime.getTimeMap().get(endTime).get("Appointment"));
    }

    @Test
    public void addAppointmentHours_multipleDates() {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(LocalDate.of(2023, 7, 11));
        dateList.add(LocalDate.of(2023, 7, 12));
        String startTime = "09:00";
        String endTime = "13:00";

        mechanicService.addAppointmentHours(mechanic, dateList, startTime, endTime);

        Map<LocalDate, DayDateTime> dailyTimeMap = mechanic.getDailyTimeMap();
        assertEquals(2, dailyTimeMap.size());

        for (LocalDate date : dateList) {
            assertTrue(dailyTimeMap.containsKey(date));

            DayDateTime dayDateTime = dailyTimeMap.get(date);
            Map<String, Map<String, Boolean>> timeMap = dayDateTime.getTimeMap();

            assertNotNull(dayDateTime);
            assertNotNull(timeMap);
            System.out.println("Date: " + date);
            System.out.println("Time Map: " + timeMap);

            for (int i = TimeToIndex.getTimeToIndex().get(startTime); i <= TimeToIndex.getTimeToIndex().get(endTime); i++) {
                String timeSlot = TimeToIndex.getIndexToTime().get(i);
                System.out.println(timeSlot);
                assertNotNull(timeMap.get(timeSlot));
                assertTrue(timeMap.get(timeSlot).get("Appointment"));
            }
        }
    }


    @Test
    public void isAppointmentSlotAvailable_slotAvailable() {
        LocalDate date = LocalDate.of(2023, 7, 11);
        String slotTime = "10:00";

        DayDateTime dayDateTime = new DayDateTime(date);
        Map<String, Map<String, Boolean>> timeMap = new HashMap<>();
        Map<String, Boolean> slotMap = new HashMap<>();
        slotMap.put("Appointment", true);
        timeMap.put(slotTime, slotMap);
        dayDateTime.setTimeMap(timeMap);

        mechanic.getDailyTimeMap().put(date, dayDateTime);

        boolean isAvailable = mechanicService.isAppointmentSlotAvailable(mechanic, date, slotTime);
        assertTrue(isAvailable);
    }

    @Test
    public void isAppointmentSlotAvailable_slotNotAvailable() {
        LocalDate date = LocalDate.of(2023, 7, 11);
        String slotTime = "10:00";

        DayDateTime dayDateTime = new DayDateTime(date);
        Map<String, Map<String, Boolean>> timeMap = new HashMap<>();
        Map<String, Boolean> slotMap = new HashMap<>();
        slotMap.put("Appointment", false);
        timeMap.put(slotTime, slotMap);
        dayDateTime.setTimeMap(timeMap);

        mechanic.getDailyTimeMap().put(date, dayDateTime);

        boolean isAvailable = mechanicService.isAppointmentSlotAvailable(mechanic, date, slotTime);
        assertFalse(isAvailable);
    }

    @Test
    public void addMechanic() {
        mechanicService.addMechanic(mechanic);
        Mockito.verify(mechanicRepository).save(mechanic);
    }

    @Test
    public void deleteMechanic() {
        mechanicService.deleteMechanic(mechanic);
        Mockito.verify(mechanicRepository).delete(mechanic);
    }

    @Test
    public void getMechanicByPhoneNumber() {
        String phoneNumber = "1234567890";
        when(mechanicRepository.getMechanicByPhoneNumber(phoneNumber)).thenReturn(mechanic);

        Mechanic retrievedMechanic = mechanicService.getMechanicByPhoneNumber(phoneNumber);
        assertNotNull(retrievedMechanic);
        assertEquals(mechanic, retrievedMechanic);
    }

    @Test
    public void testGetAllMechanics() {
        Mechanic mechanic1 = new Mechanic();
        mechanic1.setMechanicId(UUID.randomUUID());
        mechanic1.setFirstName("John");
        mechanic1.setLastName("Doe");

        Mechanic mechanic2 = new Mechanic();
        mechanic2.setMechanicId(UUID.randomUUID());
        mechanic2.setFirstName("Jane");
        mechanic2.setLastName("Smith");

        List<Mechanic> mechanicsList = Arrays.asList(mechanic1, mechanic2);

        when(mechanicRepository.findAll()).thenReturn(mechanicsList);

        List<Mechanic> result = mechanicService.getAllMechanics();

        assertEquals(2, result.size());
        assertEquals(mechanic1.getFirstName(), result.get(0).getFirstName());
        assertEquals(mechanic2.getFirstName(), result.get(1).getFirstName());
    }
}
