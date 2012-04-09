package sdi.protocolo;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdi.entity.Login;
import sdi.entity.Status;
import sdi.protocolo.TCPServer.PROTOCOLO;

/**
 *
 * @author Johnathan Douglas S. Santos
 */
public class TCPServer {

    public enum PROTOCOLO {

        LOGIN, USER, PASS, TIMESERVER
    }

    public enum LOGIN_STATUS {

        AUTENTIC, ERROR_USER, ERROR_PASS
    }
    private long serverTime;
    private int port;
    private String ip;

    //<editor-fold defaultstate="collapsed" desc="CONSTRUTOR">
    public TCPServer(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    /**
     * ip = localhost port =5000
     */
    public TCPServer() {
        this(5000, "localhost");
    }

    public TCPServer(int port) {
        this(port, "localhost");
    }
    //</editor-fold>

    public void run() {
        ServerSocket conn = null;
        try {
            conn = new ServerSocket(this.port); //cria Socket Server
            serverTime = System.currentTimeMillis();
        } catch (IOException ex) {
            System.out.println("O Servidor não pode iniciar na porta [" + port + "],pois já esta sendo utilizada.");
            System.exit(0);
        }
        //<editor-fold defaultstate="collapsed" desc="Cabecalho">
        System.out.println("[SERVER INCIADO]\tIP[" + ip + "]" + "  PORTA[" + port + "]");
        System.out.println("----------------------------");
        //</editor-fold>

        int maxThread = 10; //numero max de clientes atendidos simultaneamente
        ExecutorService executor = Executors.newFixedThreadPool(maxThread);
        while (!executor.isShutdown())
            try {
                //CRIA UMA THREAD PARA ATENDER CADA PEDIDO
                executor.submit(new ServerAuthentication(conn.accept()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    public class ServerAuthentication implements Runnable { //FASE 1

        Socket socket;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        private ServerAuthentication(Socket socket) { //FASE 1
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println("SERVER THREAD [" + Thread.currentThread().getId() + "]\tconexão com cliente [" + socket.getInetAddress().getHostAddress() + "]"
                        + "[" + socket.getPort() + "]");
//                socket.setSoTimeout(1000);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream()); //entrada
                for (;;) {
                    out.writeObject("LOGIN");
                    if ((PROTOCOLO) in.readObject() == PROTOCOLO.LOGIN)
                        if (login(out, in)) break; //se o login estiver correto sai do loop e essa thread acaba
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private boolean login(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
            String correctUsername = "udesc", correctPassword = "123";

            Gson gson = new Gson();
            String x = (String) in.readObject();
            Login login = gson.fromJson(x, Login.class);
            if (!login.getUsername().equals(correctUsername))
                out.writeObject(gson.toJson(new Status(false, "Usuario incorreto")));
            else if (!login.getPassword().equals(correctPassword))
                out.writeObject(gson.toJson(new Status(false, "Senha incorreta")));
            else {
                out.writeObject(gson.toJson(new Status(true, "login efetuado com sucesso")));
                ServerService serverService = new ServerService(socket, out, in);
                try {
                    Thread threadPedidosCliente = new Thread(serverService);
                    threadPedidosCliente.start(); //chama outra thread para gerenciar os pedidos do cliente
                    threadPedidosCliente.join(); //thread pai espera ate essa thread filho terminar
                } catch (InterruptedException ex) {
                    Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    socket.close();
                }
                return true;
            }
            return false;
        }
    }

    public class ServerService implements Runnable {//FASE 2

        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        private ServerService(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
            this.socket = socket;
            this.out = out;
            this.in = in;
        }

        @Override
        public void run() {
            try {
                menu((PROTOCOLO) in.readObject(), out, in);
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void menu(TCPServer.PROTOCOLO protocolo, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
            switch (protocolo) {
                case TIMESERVER:
                    getTimeServer(out);
                    break;
            }
        }

        private void getTimeServer(ObjectOutputStream out) throws IOException {
            StringBuilder str = new StringBuilder();
            str.append("Tempo de operação do Servidor: ").append(getTimeFormat(System.currentTimeMillis(), serverTime));
            str.append("\nHora atual do Servidor: ").append(getTimeFormat(System.currentTimeMillis()));
            out.writeObject(str.toString());
        }

        private String getTimeFormat(long currentTime, long serverStart) {
//            Calendar calendarCurrent = new GregorianCalendar();
//            calendarCurrent.setTimeInMillis(currentTime);
//
//            Calendar calendarServer = new GregorianCalendar();
//            calendarServer.setTimeInMillis(serverStart);
//
//            int hora = calendarCurrent.get(Calendar.HOUR) - calendarServer.get(Calendar.HOUR);
//            int min = calendarCurrent.get(Calendar.MINUTE) - calendarServer.get(Calendar.MINUTE);
//            int seg = calendarCurrent.get(Calendar.SECOND) - calendarServer.get(Calendar.SECOND);
            long tempo = currentTime - serverStart;
            int hora = (int) (tempo / 1000 / 60 / 60);
            int min = (int) (tempo / 1000 / 60 % 60);
            int seg = (int) (tempo / 1000 % 60);
            StringBuilder horario = new StringBuilder();
            horario.append(hora).append("h:").append(min).append("m:").append(seg).append("s");
            return horario.toString();
        }

        private String getTimeFormat(long tempo) {
            Calendar c = new GregorianCalendar();
            c.setTimeInMillis(tempo);

            StringBuilder horario = new StringBuilder();
            horario.append(c.get(Calendar.HOUR)).append("h:").append(c.get(Calendar.MINUTE)).append("m:").append(c.get(Calendar.SECOND)).append("s");
            return horario.toString();
        }
    }
}
