package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//RMI
public class Cliente {

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.start();
    }

    public void start() {
        String host = "localhost";
        int puerto = 5000;
        try (Socket socket = new Socket(host, puerto);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Ingrese CI: ");
            String ci = scanner.nextLine();

            System.out.println("Seleccione opción:");
            System.out.println("1. Consultar deudas");
            System.out.println("2. Pagar deuda");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 1) {
                String comando = "Deuda:" + ci;
                out.println(comando);
                String respuesta = in.readLine();
                System.out.println("Respuesta: " + respuesta);
            } else if (opcion == 2) {
                String comando = "Deuda:" + ci;
                out.println(comando);
                String respuesta = in.readLine();
                System.out.println("Deudas encontradas: " + respuesta);
                System.out.println("Ingrese deuda a pagar en formato: año,impuesto,monto");
                String deudaParams = scanner.nextLine();
                comando = "Pagar:" + ci + "," + deudaParams;
                out.println(comando);
                respuesta = in.readLine();
                System.out.println("Respuesta: " + respuesta);
            } else {
                System.out.println("Opción no válida");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
