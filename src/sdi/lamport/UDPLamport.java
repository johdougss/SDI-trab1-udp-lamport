/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sdi.lamport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Johnathan Douglas S. Santos
 */
public class UDPLamport {

    private Lamport lamport;
    private DatagramSocket socket;

    //<editor-fold defaultstate="collapsed" desc="CONSTRUTOR">
    public UDPLamport(int port, String ip, Lamport lamport) {
        if (lamport == null)
            this.lamport = new Lamport();
        else
            this.lamport = lamport;
        try {
            if (port == -1)
                socket = new DatagramSocket();
            else if (ip == null)
                socket = new DatagramSocket(port);
            else
                socket = new DatagramSocket(port, InetAddress.getByName(ip));
        } catch (UnknownHostException | SocketException ex) {
            ex.printStackTrace();
        }
    }

    public UDPLamport(int port) {
        this(port, null, null);
    }

    public UDPLamport(int port, Lamport lamport) {
        this(port, null, lamport);
    }

    public UDPLamport() {
        this(-1, null, null);
    }

    public UDPLamport(Lamport lamport) {
        this(-1, null, lamport);
    }
    //</editor-fold>

    public void send(String message, InetAddress addres, int port) {
        try {
            byte[] send = lamport.send(message);
            socket.send(new DatagramPacket(send, send.length, addres, port));
        } catch (IOException ex) {
            System.out.println("timeoutSEND");
        }
    }

    public void send(String message, String ip, int port) {
        try {
            this.send(message, InetAddress.getByName(ip), port);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    public void send(String message, int port) {
        try {
            this.send(message, InetAddress.getByName("localhost"), port);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    public String receive() throws IOException {
        DatagramPacket packet = new DatagramPacket(lamport.getIn(), lamport.getIn().length);
        socket.receive(packet);
        return lamport.receive(packet.getData());
    }

    public DatagramPacket receivePacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(lamport.getIn(), lamport.getIn().length);
        socket.receive(packet);
        lamport.receive(packet.getData());
        return packet;
    }

    public void close() {
        socket.close();
    }

    //<editor-fold defaultstate="collapsed" desc="GET/SET">
    public void setTimeOut(int timeout) {
        try {
            socket.setSoTimeout(timeout);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    public int getLamportLi() {
        return lamport.getLi();
    }
    //</editor-fold>
}
