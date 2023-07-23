package ams.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class CustomerVehicleMapping extends Persistence{
    @Id
    @GeneratedValue
    private UUID customerVehicleMappingId;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Vehicle vehicle;

}
