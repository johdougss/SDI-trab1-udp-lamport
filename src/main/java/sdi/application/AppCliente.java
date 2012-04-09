package sdi.application;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdi.entity.Login;
import sdi.entity.Status;
import sdi.protocolo.TCPClientBusiness;

public class AppCliente {

    public static void main(String[] args) {
        for (int i = 0; i < 15; i++) //numero de clientes
            new AppCliente().run();
    }

    private void run() {
        Gson gson = new Gson();
        Scanner sc = new Scanner(System.in);
        try {
            TCPClientBusiness tcpclient = new TCPClientBusiness();
            tcpclient.connect();

            Login login = new Login();
            for (int i = 0; i < 5; i++) {//max tentativa
//                System.out.print("usuário[udesc]:");
//                login.setUsername(sc.next().toLowerCase());
//                System.out.print("senha[123]:");
//                login.setPassword(sc.next().toLowerCase());

                System.out.println("usuário: udesc");
                login.setUsername("udesc");
                System.out.println("senha: 123");
                login.setPassword("123");

                String loginJSON = gson.toJson(login);
                Status status = tcpclient.login(loginJSON);
                if (status != null)
                    if (status.isSucess()) {
                        pedidos(tcpclient);
                        break;
                    } else System.err.println(status.getMessage());
            }
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(AppCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("-----------");
    }

    private void pedidos(TCPClientBusiness tcpclient) {
        System.out.println(tcpclient.getTimeServer());
        //fazer aqui todos os pedidos ao servidor que o cliente necessitar
    }
}
