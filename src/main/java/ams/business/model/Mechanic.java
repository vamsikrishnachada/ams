package ams.business.model;

import ams.client.model.Persistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Mechanic extends Persistence {

    @Id
    @GeneratedValue
    private UUID mechanicId;
    private String firstName;
    private String lastName;

    private String phoneNumber;
    private Map<LocalDate,DayDateTime> dailyTimeMap = new HashMap<>();

}
