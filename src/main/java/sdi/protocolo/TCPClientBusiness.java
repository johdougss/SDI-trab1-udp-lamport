package sdi.protocolo;

import com.google.gson.Gson;
import java.io.IOException;
import sdi.entity.Status;

/**
 *
 * @author Johnathan Douglas S. Santos
 */
public class TCPClientBusiness {

    private TCPClient tcpclient;
    private Gson gson = new Gson();

    public TCPClientBusiness() throws IOException {
        tcpclient = new TCPClient();
    }

    public String getTimeServer() {
        return (String) tcpclient.send(TCPServer.PROTOCOLO.TIMESERVER);
    }

    public Status login(String loginJson) {

        if (tcpclient.receive().equals("LOGIN"))
            return gson.fromJson((String) tcpclient.send(TCPServer.PROTOCOLO.LOGIN, loginJson), Status.class);
        return null;
    }

    public void connect() throws IOException, ClassNotFoundException {
        tcpclient.connect();
    }
}
