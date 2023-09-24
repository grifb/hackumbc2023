package hackumbc2023;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TempRequest {


    public TempRequest() {}



    public String getType() {
        return "";
    }

    public String getSenderIP() {
        return "";
    }

    public int getSenderPort() {
        return 0;
    }

    public DriverLevel getDriverLevel() {
        return DriverLevel.CDL;
    }

    public Shift setShift() {
        return new Shift("temp", LocalDateTime.of(LocalDate.now(),LocalTime.now()),LocalDateTime.of(LocalDate.now(),LocalTime.now()),LocalDateTime.of(LocalDate.now(),LocalTime.now()),LocalDateTime.of(LocalDate.now(),LocalTime.now()),DriverLevel.CDL);
    }

    public boolean isMOD() {
        return false;
    }

    public String wantedShiftID() {
        return "";
    }

    public String getDriverNumber() {
        return "";
    }

    public Shift createShift() {
        return null;
    }

    public String setDriver() {
        return "";
    }




}
