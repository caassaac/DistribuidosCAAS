package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RUATImpl extends UnicastRemoteObject implements RUATInterface {

    private final List<Deuda> deudas;

    protected RUATImpl() throws RemoteException {
        super();
        deudas = new ArrayList<>();
        deudas.add(new Deuda("1234567", 2022, "Vehículo", 2451));
        deudas.add(new Deuda("1234567", 2022, "Casa", 2500));
        deudas.add(new Deuda("555587", 2021, "Vehículo", 5000));
        deudas.add(new Deuda("333357", 2023, "Casa", 24547));
    }

    @Override
    public Deuda[] buscar(String ci) throws RemoteException {
        List<Deuda> resultado = new ArrayList<>();
        for (Deuda deuda : deudas) {
            if (deuda.getCi().equals(ci)) {
                resultado.add(deuda);
            }
        }
        return resultado.toArray(new Deuda[resultado.size()]);
    }

    @Override
    public Boolean Pagar(Deuda deuda) throws RemoteException {
        boolean sinObservacion = consultarAlcaldia(deuda.getCi());
        if (!sinObservacion) {
            return false;
        }
        return deudas.removeIf(d -> d.getCi().equals(deuda.getCi())
                && d.getAno() == deuda.getAno()
                && d.getImpuesto().equalsIgnoreCase(deuda.getImpuesto())
                && d.getMonto() == deuda.getMonto());
    }

    private boolean consultarAlcaldia(String ci) {
        try {
            DatagramSocket socket = new DatagramSocket();
            String mensaje = "consulta:" + ci;
            byte[] buffer = mensaje.getBytes();
            String host = "localhost";
            InetAddress direccion = InetAddress.getByName(host);
            int puerto = 9876;
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, direccion, puerto);
            socket.send(packet);

            byte[] bufRec = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(bufRec, bufRec.length);
            socket.receive(responsePacket);
            String respuesta = new String(responsePacket.getData(), 0, responsePacket.getLength());
            socket.close();
            return respuesta.trim().equalsIgnoreCase("respuesta:true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
