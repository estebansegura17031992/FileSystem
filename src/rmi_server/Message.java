/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Paquete;

/**
 *
 * @author esteban
 */
public interface Message extends Remote {
    String command(Paquete paquete) throws RemoteException;
}
