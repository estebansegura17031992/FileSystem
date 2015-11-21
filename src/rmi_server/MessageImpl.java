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
                   
                case "FILE":
                    System.out.println("FILE ID: "+id);
                    return fs.file(id, paquete.getParam()[1], paquete.getParam()[2]);
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
                    String[] files_cat = fs.cat(paquete.getId_sesion(),paquete.getParam());
                    for (int i = 1;  i < files_cat.length; i++) 
                        cat+=files_cat[i]+"\n";
                    return cat;
                    
                case "CPYRV":
                    System.out.println("CPYRV ID: "+id);
                    try{
                    String files_cpyrv = fs.cpyVR(paquete.getId_sesion(), 
                                                    paquete.getParam()[1],
                                                    paquete.getParam()[2]); 
                    return files_cpyrv;
                    }catch(Exception x)
                    {}
                    
                case "CPYVV":
                    System.out.println("CPYVV ID: "+id);
                    String filecpyvv="";
                    if(paquete.getParam()[3].equals("-r"))
                        filecpyvv = fs.cpyVV(paquete.getId_sesion(), 
                                                paquete.getParam()[1], 
                                                paquete.getParam()[2], 
                                                true);
                    else
                        filecpyvv = fs.cpyVV(paquete.getId_sesion(), 
                                                paquete.getParam()[1], 
                                                paquete.getParam()[2], 
                                                false);
                    return filecpyvv;
                case "MV":
                    System.out.println("MV ID: "+id);
                    String mv = "";
                    if(paquete.getParam()[3].equals("-f"))
                        mv = fs.mv(id, paquete.getParam()[1], paquete.getParam()[2], 
                               true);
                    else
                        mv = fs.mv(id, paquete.getParam()[1], paquete.getParam()[2], 
                               false);
                    
                    return mv;
                default:
                    break;
        }
        return "";
    }

}
