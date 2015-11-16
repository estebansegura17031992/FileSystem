/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_server;

import fileSystem.FileSystem;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.Paquete;

/**
 *
 * @author esteban
 */
public class MessageImpl extends UnicastRemoteObject implements Message {
	public MessageImpl() throws RemoteException {     
		super();
	}
	
	@Override
	public String command(Paquete paquete) throws RemoteException {
            FileSystem fs = FileSystem.getContext();
            String id = paquete.getId_sesion();
            switch (paquete.getParam()[0]) {
	        case "CREATE":
                /*
                 Este comando lo utilizaremos para crear un disco virtual. 
                 El parámetro será solamente el tamaño del disco. 
                 */
                    System.out.println("CREATE ID: "+ paquete.getId_sesion());
                    if(fs.create(id, Integer.parseInt(paquete.getParam()[1])))
                        return "/";
                    else
                        return "Usuario ya tiene un file system";
                    //System.out.println(fs.ls_root(id));
		
                    
                case "MKDIR":
                /*
                Este comando crea un directorio en el directorio Actual. 
                El parámetro es el nombre del directorio. 
                */
                    System.out.println("MKDIR ID: "+ id);
                    fs.mkdir(id, paquete.getParam()[1]);
                    break;
                case "CD":
                    System.out.println("CD ID: "+id);
                    return fs.cd(id,paquete.getParam()[1]);
                    
                case "LS":
                    break;
                case "DU":
                    break;
                default:
                    break;
        }
        return "";
    }

}
