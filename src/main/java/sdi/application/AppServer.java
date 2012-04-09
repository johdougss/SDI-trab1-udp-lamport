package sdi.application;

import sdi.protocolo.TCPServer;

/**
 *
 * @author Johnathan Douglas S. Santos
 */
public class AppServer {

    public static void main(String[] args) {
        TCPServer tcpserver = new TCPServer();
        tcpserver.run();
    }

}
