package ams.business.model;

import ams.business.model.constants.TimeToIndex;
import ams.client.model.Persistence;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class DayDateTime extends Persistence {
    private LocalDate date;
    private DayOfWeek dayOfWeek;

    private Map<String, Map<String, Boolean>> timeMap;

    static {
        TimeToIndex.initialize();
    }

    public DayDateTime(LocalDate date) {
        this.date = date;
        this.dayOfWeek = date.getDayOfWeek();
        this.timeMap = new HashMap<>();
        for (int i = 9; i <17; i++) {
            String hour = String.format("%02d", i);
            Map<String, Boolean> hourMap = new HashMap<>();
            hourMap.put("Appointment", false);
            hourMap.put("Service", false);
            timeMap.put(hour + ":00", hourMap);
            timeMap.put(hour + ":30", hourMap);
        }
        Map<String, Boolean> hourMap = new HashMap<>();
        hourMap.put("Appointment", false);
        hourMap.put("Service", false);
        timeMap.put("17:00",hourMap);
    }


    public void setAppointmentHours(String startTime, String endTime) {
        try {
            TimeToIndex.initialize(); // Initialize TimeToIndex class

//            System.out.println(TimeToIndex.timeToIndex);
//            System.out.println(startTime);
//            System.out.println(TimeToIndex.timeToIndex.get(startTime));
//            System.out.println(endTime);
//            System.out.println(TimeToIndex.timeToIndex.get(endTime));

            int startIndex = TimeToIndex.getTimeToIndex().get(startTime);
            int endIndex = TimeToIndex.getTimeToIndex().get(endTime);

            if (startIndex == -1 || endIndex == -1) {
                throw new InvalidTimeException("Invalid start or end time.");
            }

            for (int i = startIndex; i <= endIndex; i++) {
                String time = TimeToIndex.getIndexToTime().get(i);
                if (time != null) {
                    timeMap.get(time).put("Appointment", true);
                }
            }
        } catch (InvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setServiceHours(String startTime, String endTime) {
        try {
            TimeToIndex.initialize(); // Initialize TimeToIndex class

            int startIndex = TimeToIndex.getTimeToIndex().get(startTime);
            int endIndex = TimeToIndex.getTimeToIndex().get(endTime);

            if (startIndex == -1 || endIndex == -1) {
                throw new InvalidTimeException("Invalid start or end time.");
            }

            for (int i = startIndex; i <= endIndex; i++) {
                String time = TimeToIndex.getIndexToTime().get(i);
                if (time != null) {
                    timeMap.get(time).put("Service", true);
                }
            }
        } catch (InvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public class InvalidTimeException extends RuntimeException {
        public InvalidTimeException(String message) {
            super(message);
        }
    }
}
