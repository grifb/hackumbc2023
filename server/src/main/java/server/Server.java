package server;

import java.net.*;
import java.io.*;
import java.util.Map;

import com.google.gson.Gson;

import dots.*;

class Shift {
    private String id;

    public String getId() {
        return id;
    }
}

class Request {
    private RequestType request;
    private Shift data;

    public Shift getData() {
        return data;
    }

    public RequestType getRequest() {
        return request;
    }
}

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

    public static void main(String[] args) throws IOException {
        try (var server = new DOTServer(4999)) {

            System.out.println("Client has connected: " + server.acceptConnection());

            var request = server.parseRequest();
            System.out.println(request.getData().getId());

            server.respond("I got your message!");
        }
    }

}
