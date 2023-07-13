package ams.business.model.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimeToIndex {
    private static Map<String, Integer> timeToIndex;
    private static Map<Integer, String> indexToTime;

    public static Map<String, Integer> getTimeToIndex() {
        return timeToIndex;
    }

    public static Map<Integer, String> getIndexToTime() {
        return indexToTime;
    }

    public static void initialize() {
        Map<String, Integer> timeIndexMap = new HashMap<>();
        Map<Integer, String> indexTimeMap = new HashMap<>();

        String startTime = "09:00";
        int timeSlotInMinutes = 30;
        int numTimeSlots = 16;

        for (int i = 0; i < numTimeSlots; i++) {
            timeIndexMap.put(startTime, i);
            indexTimeMap.put(i, startTime);
            startTime = incrementTime(startTime, timeSlotInMinutes);
        }
        timeIndexMap.put("17:00", 17);
        indexTimeMap.put(17, "17:00");

        timeToIndex = Collections.unmodifiableMap(timeIndexMap);
        indexToTime = Collections.unmodifiableMap(indexTimeMap);
    }

    private static String incrementTime(String time, int minutes) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        int totalMinutes = hour * 60 + minute + minutes;
        hour = totalMinutes / 60;
        minute = totalMinutes % 60;

        return String.format("%02d:%02d", hour, minute);
    }
}
