package ams.client.model;

import ams.business.model.Mechanic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Appointment extends Persistence {

    @Id
    @GeneratedValue
    private UUID appointmentId;
    private Customer customer;
    private Mechanic mechanic;
    private LocalDate date;
    private String slotTime;
}
