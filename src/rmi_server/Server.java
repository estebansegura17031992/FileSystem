/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author esteban
 */
public class Server {
    public static void main(String[] args) {
        try {
            Message message = new MessageImpl();
            
            // Registro creado en el puerto 1099
            Registry registry = LocateRegistry.createRegistry(1099);
             
            // Creamos un nuevo serivcio llamado cmd
            registry.rebind("cmd", message);
            System.out.println("Servidor listo");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
