/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sdi.lamportapplication;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import sdi.lamport.Lamport;
import sdi.lamport.UDPLamport;

/**
 *
 * @author Johnathan Douglas S. Santos
 */
public class App {

    private Lamport lamport;
    private Map<Integer, String> messages;

    public App() {
        lamport = new Lamport(new byte[100], new byte[100]);
        messages = new TreeMap<>();
    }

    public static void main(String[] args) {
        int port = 5000;
        String msg = "MSG";
        int idproc = 1;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
            idproc = Integer.parseInt(args[1]);
            msg = args[2];
        }

        App app = new App();
        app.runServer(port, idproc);
        app.runClint(msg, idproc, port);
    }

    private void runServer(final int port, final int idproc) {
        new Thread() {

            @Override
            public void run() {
                UDPLamport udpserver = new UDPLamport(port, lamport);
                udpserver.setTimeOut(500);
                System.out.println("[SERVER] INICIO");
                try {
                    while (true) {
                        String msg = udpserver.receive();
                        messages.put(udpserver.getLamportLi(), msg);
                        System.out.println("\t[SERVER] ADD:" + msg);
                    }
                } catch (IOException ex) {
                    System.out.println("[SERVER] TIMEOUT");
                }
                print(messages);
                System.out.println("[SERVER] FIM");
            }

            private void print(Map<Integer, String> messages) {
                Iterator<Map.Entry<Integer, String>> iterator = messages.entrySet().iterator();
                if (!iterator.hasNext()) {
                    System.out.println("[SERVER] Nenhuma mensagem recebida.");
                    return;
                }
                int recebidos = 0;
                StringBuilder str = new StringBuilder();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, String> entry = iterator.next();
                    str.append("Li:").append(entry.getKey()).append(entry.getValue()).append("\r\n");
                    recebidos++;
                }
                str.append("\r\nforam recebidos:").append(recebidos).append(" mensagens.");
                printArquivo(str.toString(), idproc);
            }

            private void printArquivo(String txt, int idproc) {
                try (FileWriter x = new FileWriter("proc[" + idproc + "].txt")) {
                    x.write(txt);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    private void runClint(String msg, int idproc, int port) {
        System.out.println("[CLIENT] INICIO");
        UDPLamport udpclient = new UDPLamport(lamport);
        StringBuilder str;
        for (int i = 0; i < 50; i++)
            for (int portServer = 5000; portServer < 5018; portServer++) {
                if (port == portServer || aletorio())
                    continue;
                str = new StringBuilder();
                str.append("\tProc:").append(idproc).append("\tMSG:").append(msg).append("-").append(i);
                udpclient.send(str.toString(), portServer);
                System.out.println("[CLIENT] Li[" + udpclient.getLamportLi() + "]  \tSEND: ServidorPort:" + portServer + " Conteudo:" + str.toString());
            }
        System.out.println("[CLIENT] FIM");
    }

    private boolean aletorio() {
        return (1 + (int) (Math.random() * 2)) % 2 == 0;
    }
}
