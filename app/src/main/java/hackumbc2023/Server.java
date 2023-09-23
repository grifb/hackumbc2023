package hackumbc2023;
import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private LinkedList<Shift> shifts = new LinkedList<Shift>();
    public Server(int port) {
        try {
            server = new ServerSocket(port);
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        catch (IOException i) {

        }
    }

}
