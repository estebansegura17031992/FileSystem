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
		
                    
                case "MKDIR":
                /*
                Este comando crea un directorio en el directorio Actual. 
                El parámetro es el nombre del directorio. 
                */
                    System.out.println("MKDIR ID: "+ id);
                    return fs.mkdir(id, paquete.getParam()[1]);
                case "CD":
                    System.out.println("CD ID: "+id);
                    return fs.cd(id,paquete.getParam()[1]);
                    
                case "LS":
                    System.out.println("LS ID: "+id);
                    String[] files = fs.ls(id);
                    String ls = "";
                    for (int i = 0;  i < files.length; i++) 
                        ls+=files[i]+"\n";
                    
                    return ls;
                    
                case "DU":
                /*
                Da el tamaño de un directorio o archivo. 
                */
                    System.out.println("DU ID: "+id);
                    return fs.du(id, paquete.getParam()[1]);
                case "PWD":
                /*
                Despliega el directorio actual
                */
                    System.out.println("PWD ID: "+id);
                    return fs.pwd(paquete.getId_sesion());
                    
                case "CAT":
                /*
                Recibe como parámetro el nombre de uno o varios archivos y debe desplegar 
                su contenido. 
                */
                    System.out.println("CAT ID: "+id);
                    String cat="";
                    String[] files_pwd = fs.cat(paquete.getId_sesion(),paquete.getParam());
                    for (int i = 0;  i < files_pwd.length; i++) 
                        cat+=files_pwd[i]+"\n";
                    return cat;
                    
                default:
                    break;
        }
        return "";
    }

}
