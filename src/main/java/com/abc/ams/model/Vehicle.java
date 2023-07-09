package com.abc.ams.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Vehicle extends Persistence {

    @Id
    @GeneratedValue
    private UUID vehicleId;

    private String make;

    private String model;

    private String type;


}
