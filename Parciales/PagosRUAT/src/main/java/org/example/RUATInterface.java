package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RUATInterface extends Remote {
    Deuda[] buscar(String ci) throws RemoteException;

    Boolean Pagar(Deuda deuda) throws RemoteException;
}