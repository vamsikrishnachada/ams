package ams.business.web;

import ams.business.model.Mechanic;
import ams.business.services.MechanicService;
import ams.client.model.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/mechanic")
public class MechanicController {
    private MechanicService mechanicService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addMechanic(@RequestBody Mechanic mechanic) {
        mechanicService.addMechanic(mechanic);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(method = RequestMethod.GET, path = "/searchByPhoneNumber", produces = "application/json")
    public Mechanic findMechanicByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return mechanicService.getMechanicByPhoneNumber(phoneNumber);
    }
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity deleteMechanic(@RequestBody Mechanic mechanic) {
        mechanicService.deleteMechanic(mechanic);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/isAppointmentSlotAvailable", produces = "application/json")
    public Boolean isAppointmentSlotAvailable(@RequestBody Mechanic mechanic,@RequestParam("date") LocalDate date,@RequestParam("slotTime") String slotTime) {
        return mechanicService.isAppointmentSlotAvailable(mechanic,date,slotTime);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity addAppointmentHours(@RequestBody Mechanic mechanic,@RequestParam("date") LocalDate date,
                                                    @RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime) {
        mechanicService.addAppointmentHours(mechanic,date,startTime,endTime);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity addAppointmentHours(@RequestBody Mechanic mechanic,@RequestParam("dateList") List<LocalDate> dateList,
                                              @RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime) {
        mechanicService.addAppointmentHours(mechanic,dateList,startTime,endTime);
        return ResponseEntity.ok().build();
    }





}
