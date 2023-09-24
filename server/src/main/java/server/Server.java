package server;

import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import dots.*;


/**
 * The socket server for the DOTS Subleave
 */
class DOTServer implements Closeable {

    /** Socket server */
    private final ServerSocket server;
    /** Used for JSON serialization/deserialization */
    private final Gson gson;
    /** Represents the currently connected client */
    private Socket currentClient;
    /** Used to accept requests */
    private BufferedReader in;
    /** Used to send responses */
    private PrintWriter out;

    public DOTServer(int port) throws IOException {
        server = new ServerSocket(port);

        gson = new Gson();
    }

    /**
     * Accepts the connection request from the client
     * @return The IP address of the client
     * @throws IOException
     */
    public String acceptConnection() throws IOException {
        currentClient = server.accept();

        out = new PrintWriter(currentClient.getOutputStream());

        var reader = new InputStreamReader(currentClient.getInputStream());
        in = new BufferedReader(reader);

        return currentClient.getInetAddress().toString();
    }

    /**
     * Send a response back to the client
     * @param message The message to send to the client
     * @throws IOException
     */
    public void respond(String message) throws IOException {
        out.println(message);
        out.flush();
    }

    /**
     * Deserializes the JSON request into a class
     * @return The deserialized data
     * @throws IOException
     */
    public Request parseRequest() throws IOException {
        return gson.fromJson(readRequest(), Request.class);
    }

    /**
     * Reads a JSON string sent by the client
     * @return The string sent by the client
     * @throws IOException
     */
    private String readRequest() throws IOException {
        return in.readLine();
    }

    /**
     * Closes the socket server and cleans up resources
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        server.close();
    }
}

public class Server {

    public static void handleRequest(DOTServer server, ArrayList<Shift> shifts) throws IOException {
        var request = server.parseRequest();
        var data = request.getData();

        switch (request.getRequest()) {
            case VIEWSHIFT:

                for (var shift : shifts) {
                    if (shift.getId().equals(data.get("id"))) {
                        server.respond("Found shift " + data.get("id"));
                        break;
                    }
                }

                break;

            case POSTSHIFT:

                var startTime = LocalDateTime.ofEpochSecond(
                        Integer.parseInt(data.get("startTime")),
                        0,
                        ZoneOffset.of("Z")
                );

                var endTime = LocalDateTime.ofEpochSecond(
                        Integer.parseInt(data.get("endTime")),
                        0,
                        ZoneOffset.of("Z")
                );

                var level = Integer.parseInt(data.get("driverLevel"));

                DriverLevel levelEnum = null;

                for (var l: DriverLevel.values()) {
                    if (l.getValue() == level) {
                        levelEnum = l;
                        break;
                    }
                }

                var s = new Shift(
                        data.get("id"),
                        startTime,
                        endTime,
                        levelEnum
                );

                shifts.add(s);

                server.respond("Added shift " + data.get("id"));
                break;

            case AVAILABLESHIFTS:

                String response = "";

                for (var shift : shifts) {
                    if (shift.getAssignedDriver().equals("unassigned")) {
                        response += String.format("ID: %s | START: %s | END: %s | REQUIRED: %s\n", shift.getId(), shift.getStart(), shift.getEnd(), shift.getRequired());
                    }
                }

                server.respond(response);

                break;
        }
    }

    public static void main(String[] args) throws IOException {
        var shifts = new ArrayList<Shift>();

        try (var server = new DOTServer(4999)) {

            System.out.println("Client has connected: " + server.acceptConnection());

            handleRequest(server, shifts);
            handleRequest(server, shifts);
            handleRequest(server, shifts);
            handleRequest(server, shifts);

        }
    }

}
