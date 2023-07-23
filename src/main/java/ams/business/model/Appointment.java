package ams.business.model;

import ams.business.model.Mechanic;
import ams.client.model.Customer;
import ams.client.model.Persistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Appointment extends Persistence {

    @Id
    @GeneratedValue
    private UUID appointmentId;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Mechanic mechanic;
    private LocalDate date;
    private String slotTime;
}
