package hackumbc2023;
import java.net.*;
import java.io.*;
import java.util.LinkedList;
import com.google.gson.Gson;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedReader  in = new BufferedReader(new InputStreamReader(System.in));;
    private LinkedList<Shift> shifts = new LinkedList<Shift>();
    private Gson gson = new Gson();
    public Server(int port) {

        try {

            server = new ServerSocket(port);
            System.out.println(server.toString());


        }
        catch (IOException i) {

        }
    }

    public void acceptRequests() {
        while (true) {
            String line = "";
            try {
                socket = server.accept();

                TempRequest temp = new TempRequest();

                String request = gson.fromJson(line, temp.getClass()).getType();
                DriverLevel driverLevel = gson.fromJson(line,temp.getClass()).getDriverLevel();
                String driverNumber = gson.fromJson(line,temp.getClass()).getDriverNumber();
                boolean isMOD = gson.fromJson(line, temp.getClass()).isMOD();
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                if (request.equals("getShifts")) {
                    writer.println(gson.toJson(getShifts(driverLevel)));
                }
                else if (request.equals("takeShift")) {
                    String wantedShift = gson.fromJson(line, temp.getClass()).wantedShiftID();
                    writer.println(gson.toJson(takeShift(driverNumber,driverLevel,wantedShift,isMOD)));
                }
                else if (request.equals("dropShift")) {
                    String wantedShift = gson.fromJson(line, temp.getClass()).wantedShiftID();
                    writer.println(gson.toJson(dropShift(driverNumber,wantedShift)));
                }
                else if (request.equals("createShift")) {
                    Shift createdShift = gson.fromJson(line, temp.getClass()).createShift();
                    writer.println(gson.toJson(createdShift));
                }
                else if (request.equals("deleteShift")) {
                    String deleteShift = gson.fromJson(line, temp.getClass()).wantedShiftID();
                    writer.println(gson.toJson(deleteShift(deleteShift)));
                }
                else if (request.equals("editShift")) {
                    Shift editedShift = gson.fromJson(line, temp.getClass()).createShift();
                    writer.println(gson.toJson(editShift(editedShift)));
                }
                else if (request.equals("assignShift")) {
                    String driver = gson.fromJson(line, temp.getClass()).setDriver();
                    String wantedShift = gson.fromJson(line, temp.getClass()).wantedShiftID();
                    writer.println(gson.toJson(setShiftDriver(driver, wantedShift)));
                }




            }
            catch (IOException i) {
                System.out.println(i);
            }

        }
    }

    private LinkedList<Shift> getShifts(DriverLevel driverLevel) {
        LinkedList<Shift> eligible = new LinkedList<Shift>();
        for (Shift s : shifts) {
            if (s.getRequired().getValue() <= driverLevel.getValue()) {
                eligible.add(s);
            }
        }
        return eligible;
    }

    private void addShift(Shift toAdd) {
        shifts.add(toAdd);
    }

    private String takeShift(String driverNumber, DriverLevel driverLevel, String id, boolean MODRequest) {
        boolean conflict = false;
        LinkedList<Shift> driverShifts = new LinkedList<Shift>();
        Shift wantedShift = null;
        for (Shift s : shifts) {
            if (s.getAssignedDriver().equals(driverNumber)) {
                driverShifts.add(s);
            }
            if (s.getId().equals(id)) {
                wantedShift = s;
            }
        }

        for (Shift s : driverShifts) {
            if (checkConflict(s, wantedShift)) {
                conflict = true;
            }
        }
        if (conflict && !MODRequest) {
            return "conflict";
        }
        if (wantedShift.getAssignedDriver().equals("unassigned")) {
            return "Shift already assigned";
        }
        for (Shift s : shifts) {
            if (s.getId().equals(id)) {
                if (s.getRequired().getValue() <= driverLevel.getValue()) {
                    s.setAssignedDriver(driverNumber);
                    return "assigned";
                }
                else if (s.getRequired().getValue() > driverLevel.getValue()) {
                    s.setAssignedDriver(driverNumber);
                    return "ineligible";
                }

            }
        }
        return "unspecified error";
    }

    private boolean checkConflict(Shift assigned, Shift toAssign) {
        boolean hasConflict = false;
        if (toAssign.getStart().isAfter(assigned.getStart()) && toAssign.getStart().isBefore(assigned.getEnd())) {
            hasConflict = true;
        }
        if (toAssign.getEnd().isAfter(assigned.getStart()) && toAssign.getStart().isBefore(assigned.getEnd())) {
            hasConflict = true;
        }
        return hasConflict;

    }

    private String dropShift(String driverNumber, String shiftID) {
        for (Shift s : shifts) {
            if (s.getAssignedDriver().equals(driverNumber) && s.getId().equals(shiftID)) {
                s.setAssignedDriver("unassigned");
                return "Success";
            }
            else if (!s.getAssignedDriver().equals(driverNumber) && s.getId().equals(shiftID)) {
                return "Shift assigned to other driver";
            }
        }
        return "Shift not found";
    }

    private String createShift(Shift created) {

        for (Shift s : shifts) {
            if (s.getId().equals(created.getId())) {
                return "ID in use";
            }
        }
        shifts.add(created);
        return "Sucess";


    }

    private String deleteShift(String deleted) {
        for (Shift s : shifts) {
            if (s.getId().equals(deleted) {
                shifts.remove(s);
                return "Sucess";
            }
        }
        return "Shift not found";
    }

    private String editShift(Shift edited) {
        for (Shift s :shifts) {
            if (s.getId().equals(edited.getId())) {
                s = edited;
                return "Success";
            }
        }
        return "Shift not found";
    }

    private String setShiftDriver(String driver, String shiftID) {
        for (Shift s :shifts) {
            if (s.getId().equals(shiftID)) {
                s.setAssignedDriver(driver);
                return "Success";
            }
        }
        return "Shift not found";
    }




}
