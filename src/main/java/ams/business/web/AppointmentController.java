package ams.business.web;
import ams.business.model.Appointment;
import ams.business.model.Mechanic;
import ams.business.services.AppointmentService;
import ams.client.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping(value = "/availableSlots", produces = "application/json")
    public Map<Mechanic,List<String>> getAvailableSlots(@RequestParam("date") LocalDate date) {
         return appointmentService.getAvailableSlots(date);
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestParam("date") String date,
                                             @RequestParam("slotTime") String slotTime,
                                             @RequestBody Customer customer, @RequestParam("mechanicID") Mechanic mechanic) {
        LocalDate parsedDate = LocalDate.parse(date);


        appointmentService.bookAppointment(parsedDate, slotTime, customer, mechanic);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable UUID appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<?> updateAppointment(@PathVariable UUID appointmentId, @RequestBody Appointment updatedAppointment) {
        try {
            appointmentService.updateAppointment(appointmentId, updatedAppointment);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable UUID appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }
}
