package dots;

import java.time.LocalDateTime;

public class Shift {

    String id;
    LocalDateTime start;
    LocalDateTime end;
    LocalDateTime leaveOut;
    LocalDateTime changeOver; 

    public Shift(String id, LocalDateTime start, LocalDateTime end, LocalDateTime leaveOut, LocalDateTime changeOver) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
