package sdi.protocolo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Johnathan Douglas S. Santos
 */
public class TCPClient {

    private String ip;
    private int port;
    private Socket socket = null;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    //<editor-fold defaultstate="collapsed" desc="CONSTRUTOR">
    public TCPClient(String serverIp, int serverPort) throws IOException {
        this.ip = serverIp;
        this.port = serverPort;
    }

    public TCPClient(int port) throws IOException {
        this("localhost", port);
    }

    public TCPClient() throws IOException {
        this("localhost", 5000);

    }
    //</editor-fold>

    public Object send(TCPServer.PROTOCOLO protocolo, Object... object) {
        try {
            out.writeObject(protocolo);
            for (int i = 0; i < object.length; i++)
                out.writeObject(object[i]);
            return in.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("[ERRO]Comunicação com o servidor falhou.");
//            ex.printStackTrace();
            return null;
        }
    }

    public void connect() throws IOException, ClassNotFoundException {
        socket = new Socket(ip, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        System.out.println("[CLIENTE] CONECTADO ao servidor [" + ip + "] [" + port + "]");
//        socket.setSoTimeout(1000);
    }

    public Object receive() {
        try {
            return in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("[ERRO]Comunicação com o servidor falhou.");
//            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
