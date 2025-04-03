package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// RMI
public class RUATServidor {
    public static void main(String[] args) {
        try {
            RUATInterface ruat = new RUATImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RUAT", ruat);
            System.out.println("Servidor RMI RUAT está corriendo...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
