package ams.business.model;

import ams.client.model.Persistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mechanic_id")
    @MapKey(name = "date") // Maps LocalDate keys to DayDateTime values
    private Map<LocalDate, DayDateTime> dailyTimeMap = new HashMap<>();

}
