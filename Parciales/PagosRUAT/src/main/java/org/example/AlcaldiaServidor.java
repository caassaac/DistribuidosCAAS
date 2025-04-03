package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

// UDP
public class AlcaldiaServidor {
    public static void main(String[] args) {
        int puerto = 9876;
        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("El servidor UDP Alcaldía está corriendo en el puerto " + puerto);
            byte[] receiveBuffer = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String recibido = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Recibido: " + recibido);
                DatagramPacket sendPacket = getDatagramPacket(recibido, receivePacket);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DatagramPacket getDatagramPacket(String received, DatagramPacket receivePacket) {
        String[] partes = received.split(":");
        String respuesta;
        if (partes.length == 2 && partes[0].equalsIgnoreCase("consulta")) {
            String ci = partes[1];
            if (ci.equals("1234567")) {
                respuesta = "respuesta:false";
            } else {
                respuesta = "respuesta:true";
            }
        } else {
            respuesta = "respuesta:invalido";
        }
        byte[] sendBuffer = respuesta.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                receivePacket.getAddress(), receivePacket.getPort());
        return sendPacket;
    }
}
