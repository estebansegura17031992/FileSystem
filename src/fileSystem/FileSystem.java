package fileSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Adonis
 */
public class FileSystem {
    
    private static FileSystem _FSContext;
    private final HashMap<String, ClientSystem> fileSystems;
    
    private FileSystem(){
        this.fileSystems = new HashMap<>();
    }
    
    public static FileSystem getContext(){
        if(_FSContext == null)
            _FSContext = new FileSystem();
        return _FSContext;
    }
    
    public Boolean create(String user, double size){
        if(this.fileSystems.containsKey(user))
            return false;
        
        Directory root = new Directory(null, "", "/");
        ClientSystem cs = new ClientSystem(root, root, size);
        this.fileSystems.put(user, cs);
        return true;
    }
    
    public String mkdir(String user, String name){
        if(!this.fileSystems.containsKey(user))
            return "Usuario no tiene un file system";
        
        ClientSystem cs = this.fileSystems.get(user);
        String pathToDir = cs.current.getAbsolutePath() + ((cs.current.getParent() == null) ? "" : "/");
        Directory dir = new Directory(cs.current, name, pathToDir);
        cs.current.addFile(dir);
        return cs.current.getAbsolutePath();
    }
    
    public String file(String user, String name, String content){
        if(!this.fileSystems.containsKey(user))
            return "Usuario no tiene un file system";
        
        ClientSystem cs = this.fileSystems.get(user);
        String pathToFile = cs.current.getAbsolutePath() + ((cs.current.getParent() == null) ? "" : "/");
        File file = new File(cs.current, name, pathToFile);
        cs.current.addFile(file);
        file.setContent(content);
        return "Archivo creado exitosamente";
    }
    
    public String cd(String user, String path){
        if(!this.fileSystems.containsKey(user))
            return "Usuario no tiene un file system";
        
        ClientSystem cs = this.fileSystems.get(user);
        Directory current = cs.current;
        String[] pathList = path.split("/");
        
        for (String pathItem : pathList) {
            switch (pathItem) {
                case "..":
                    current = (Directory)current.getParent();
                    break;
                case ".":
                    //current = (Directory)current;
                    break;
                default:
                    Directory next = (Directory)current.find(pathItem);
                    if(next != null)
                        current = next;
                    else
                        return "No existe el directorio " + pathItem;
                    break;
            }
        }
        cs.current = current;
        return cs.current.getAbsolutePath();
    }
    
    public String[] ls(String user){
        if(!this.fileSystems.containsKey(user))
            return null;
        
        ClientSystem cs = this.fileSystems.get(user);
        ArrayList<String> result = new ArrayList<>();
        cs.current.getContent().stream().forEach((fs) -> {
            result.add(fs.getName());
        });
        return result.toArray(new String[cs.current.getContent().size()]);
    }
    
    public String du(String user, String Filename){
        if(!this.fileSystems.containsKey(user))
            return "Usuario no tiene un file system";
        
        ClientSystem cs = this.fileSystems.get(user);
        
        if(Filename.equals(""))
            return cs.current.getName() + " tamaño: " + cs.current.getSize();
        
        FileStruct fs = cs.current.find(Filename);
        if(fs != null)
            //return "Tamaño de " + fs.getName() + " es " + fs.getSize();
            return fs.getName()+" tamaño: "+fs.getSize();
        return "No existe el directorio/archivo con nombre " + Filename;
    }
    
    public String pwd(String user){
        if(!this.fileSystems.containsKey(user))
            return "Usuario no tiene un file system";
        
        ClientSystem cs = this.fileSystems.get(user);
        return cs.current.getAbsolutePath();
    }
    
    public String[] cat(String user, String[] filenames){
        if(!this.fileSystems.containsKey(user))
            return new String[]{"Usuario no tiene un file system"};
        
        ClientSystem cs = this.fileSystems.get(user);
        ArrayList<String> result = new ArrayList<>();
        for(String file : filenames){
            FileStruct fs = cs.current.find(file);
            
            if(fs == null){
                result.add("CAT: " + file + " no existe");
                continue;
            }
            
            if(fs instanceof Directory)
                result.add("CAT: " + file + " es un directorio");
            else{
                String content = "CAT: " + file + ":\n";
                content += ((File)fs).getContent();
                result.add(content);
            }
        }
        
        return result.toArray(new String[filenames.length]);
    }
    
//    public String cpyRR(String user, String routeOrigin, String routeDestination){
//        
//    }
//    
//    public String cpyVR(String user, String routeOrigin, String routeDestination){
//        
//    }
//    
//    public String cpyVV(String user, String routeOrigin, String routeDestination){
//        
//    }
    
    public String mv(String user, String routeOrigin, String routeDestination, Boolean force){
        if(!this.fileSystems.containsKey(user))
            return "Usuario no tiene un file system";
        
        ClientSystem cs = this.fileSystems.get(user);
        String[] originRoute = routeOrigin.split("/");
        String originFilename = originRoute[originRoute.length-1];
        Directory originParentDir = getLastDir(cs, originRoute);
        if(originParentDir == null)
            return "Imposible llegar al archivo o directorio";
        FileStruct originFile = originParentDir.find(originFilename);
        if(originFile == null)
            return "El archivo o directorio " + originFilename + "no existe";
        
        String[] destinationRoute = routeDestination.split("/");
        String destinationFilename = destinationRoute[destinationRoute.length-1];
        Directory destinationDir = getLastDir(cs, destinationRoute);
        if(destinationDir == null)
            return "Imposible llegar al directorio";
        
        FileStruct fileExist = destinationDir.find(destinationFilename);
        if(fileExist != null && !force)
            return "Ya existe un archivo o directorio con ese nombre, utilice -f para sobreescribir";
        
        originFile.setName(destinationFilename);
        moveFile(originFile, destinationDir, fileExist != null);
        
        return "El archivo o directorio " + originFilename + " se encuentra en " + routeDestination;
    }
    
    public String[] rm(String user, String[] filenames, Boolean force){
        if(!this.fileSystems.containsKey(user))
            return new String[]{"Usuario no tiene un file system"};
        
        ClientSystem cs = this.fileSystems.get(user);
        ArrayList<String> result = new ArrayList<>();
        for(String file : filenames){
            FileStruct fs = cs.current.find(file);
            
            if(fs == null){
                result.add("RM: " + file + " no existe");
                continue;
            }
            
            if(fs instanceof File || force){
                cs.current.deleteFile(file);
                result.add("RM: " + file + " eliminado");
            }
            else
                result.add("RM: " + file + " no se puede eliminar use la opcion -r");
        }
        
        return result.toArray(new String[filenames.length]);
        
    }
    
    public String[] find(String user, String regex){
        if(!this.fileSystems.containsKey(user))
            return new String[]{"Usuario no tiene un file system"};
        
        String customRegex = regex.replace("*", ".*?");
        ClientSystem cs = this.fileSystems.get(user);
        
        ArrayList<String> result = cs.current.findAll(customRegex);
        
        return result.toArray(new String[result.size()]);
    }
    
    public String tree(String user){
        if(!this.fileSystems.containsKey(user))
            return "Usuario no tiene un file system";
        
        ClientSystem cs = this.fileSystems.get(user);
        return cs.current.printTree();
    }
    
    private Directory getLastDir(ClientSystem cs, String[] route){
        Boolean failed = false;
        Directory current = cs.current;
        
        for(int i=0; i<route.length-1; i++){
            if(route[i].equals(""))
                continue;
                    
            FileStruct next = current.find(route[i]);
            
            if(next == null){
                failed = true;
                break;     
            }
            if(!(next instanceof Directory)){
                failed = true;
                break;     
            }
            
            current = (Directory)next;
        }
        
        if(failed){
            current = cs.root;
            for(int i=0; i<route.length-1; i++){
                if(route[i].equals(""))
                    continue;

                FileStruct next = current.find(route[i]);

                if(next == null)
                    return null;     
                if(!(next instanceof Directory))
                    return null;   

                current = (Directory)next;
            }
        }
        
        return current;
    }
    
    private void moveFile(FileStruct source, Directory destination, Boolean exist){
        if(exist)
            destination.deleteFile(source.getName());
        
        String pathToFile = destination.getAbsolutePath() + ((destination.getParent() == null) ? "" : "/");
        ((Directory)source.getParent()).deleteFile(source.getName());
        source.setParent(destination);
        source.setPath(pathToFile);
        destination.addFile(source);
        
        System.out.println(pathToFile);
        //"/"
        System.out.println(source.getRelativePath());
        System.out.println(source.getAbsolutePath());
    }
    
    // test delete on final version
    public void test(String user){
        ClientSystem cs = this.fileSystems.get(user);
        System.out.println("Root " + cs.root.getSize());
    }
    public void test(String user, String testString){
        ClientSystem cs = this.fileSystems.get(user);
        FileStruct fs = cs.current.find(testString);
        System.out.println("absolute path " + fs.getAbsolutePath());
    }
    
    private class ClientSystem{
        public Directory root;
        public Directory current;
        public double size;
        
        public ClientSystem(Directory root, Directory current, double size){
            this.root = root;
            this.current = current;
            this.size = size;
        }
    }
}
