package client;

import java.net.*;
import java.io.*;
import java.util.Dictionary;
import java.util.Map;
import com.google.gson.Gson;

import dots.*;

/**
 * The socket client for the DOTS Subleave
 */
class DOTClient implements Closeable {
    /** Socket client */
    private final Socket client;
    /** Used to read responses from the server */
    private final BufferedReader in;
    /** Used to send requests to the server */
    private final PrintWriter out;
    /** Used to serialize/deserialize JSON */
    private final Gson gson;

    /**
     * Connects to the server
     * @param host The IP or hostname of the server
     * @param port The port the server is listening on
     * @throws IOException
     */
    public DOTClient(String host, int port) throws IOException {
        client = new Socket(host, port);

        out = new PrintWriter(client.getOutputStream());

        var reader = new InputStreamReader(client.getInputStream());
        in = new BufferedReader(reader);

        gson = new Gson();
    }

    /**
     * Send a message to the server
     * @param message The message to send to the server
     * @throws IOException
     */
    private void sendMessage(String message) throws IOException {
        out.println(message);
        out.flush();
    }

    /**
     * Send a request to the server
     * @param type The type of request to send to the server
     * @param data The data associated with the request type
     * @throws IOException
     */
    public void sendRequest(RequestType type, Map<String, String> data) throws IOException {
        var r = new Request(type, data);
        sendMessage(gson.toJson(r));
    }

    /**
     * Gets a response from the server
     * @return The response from the server
     * @throws IOException
     */
    public String getResponse() throws IOException {
        return in.readLine();
    }

    /**
     * Closes the connection to the server and cleans up resources
     * @throws IOException
     */
    public void close() throws IOException {
        in.close();
        out.close();
        client.close();
    }
}

public class Client {
    public static void main(String[] args) throws IOException {
        try (var client = new DOTClient("localhost", 4999)) {

            client.sendRequest(RequestType.VIEWSHIFT, Map.of(
                    "id", "AB-01"
            ));

            System.out.println(client.getResponse());
        }
    }
}
 
