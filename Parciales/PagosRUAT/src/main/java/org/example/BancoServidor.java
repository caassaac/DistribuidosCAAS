package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// TCP
public class BancoServidor {
    private RUATInterface ruat;

    public BancoServidor() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ruat = (RUATInterface) registry.lookup("RUAT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BancoServidor servidor = new BancoServidor();
        servidor.start();
    }

    public void start() {
        int puerto = 5000;
        try (ServerSocket servidorSocket = new ServerSocket(puerto)) {
            System.out.println("El servidor TCP Banco está corriendo en el puerto " + puerto);
            while (true) {
                Socket clienteSocket = servidorSocket.accept();
                new Thread(new ClientHandler(clienteSocket, ruat)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;
    private final RUATInterface ruat;

    public ClientHandler(Socket socket, RUATInterface ruat) {
        this.socket = socket;
        this.ruat = ruat;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String request;
            while ((request = in.readLine()) != null) {
                System.out.println("Comando recibido: " + request);
                if (request.startsWith("Deuda:")) {
                    String ci = request.substring(6);
                    Deuda[] deudas = ruat.buscar(ci);
                    StringBuilder response = new StringBuilder("deudas:");
                    for (Deuda d : deudas) {
                        response.append(d.toString()).append(";");
                    }
                    out.println(response);
                } else if (request.startsWith("Pagar:")) {
                    String params = request.substring(6);
                    String[] tokens = params.split(",");
                    if (tokens.length == 4) {
                        String ci = tokens[0];
                        int ano = Integer.parseInt(tokens[1]);
                        String impuesto = tokens[2];
                        double monto = Double.parseDouble(tokens[3]);
                        Deuda deuda = new Deuda(ci, ano, impuesto, monto);
                        Boolean result = ruat.Pagar(deuda);
                        out.println("transacción:" + result);
                    } else {
                        out.println("transacción:false");
                    }
                } else {
                    out.println("Comando desconocido");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
