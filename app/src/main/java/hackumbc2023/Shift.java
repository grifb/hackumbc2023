package hackumbc2023;

import java.time.LocalDateTime;

public class Shift {

    LocalDateTime start;
    LocalDateTime end;
    LocalDateTime leaveOut;
    LocalDateTime changeOver; 

    public Shift(LocalDateTime start, LocalDateTime end, LocalDateTime leaveOut, LocalDateTime changeOver) {
        this.start = start;
        this.end = end;
        this.leaveOut = leaveOut; 
        this.changeOver = changeOver; 
    }

    public LocalDateTime getChangeOver() {
        return changeOver;
    }

    public void setChangeOver(LocalDateTime changeOver) {
        this.changeOver = changeOver;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getLeaveOut() {
        return leaveOut;
    }

    public void setLeaveOut(LocalDateTime leaveOut) {
        this.leaveOut = leaveOut;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

}
