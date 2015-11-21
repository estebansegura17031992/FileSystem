/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import fileSystem.*;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Adonis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String test = "/";
        String test2 = "/dir1/dir2";
        
        String[] al = test.split("/");
        
        if(al.length == 0)
            System.out.println("/");
        
        al = test2.split("/");
        
        System.out.println(al[al.length-1]);
        
        Directory root = new Directory(null, "", "/");
        
        System.out.println(root.getName());
        
        Directory dir1 = new Directory(root, "dir1", "/");
        root.addFile(dir1);
        Directory dir2 = new Directory(root, "dir2", "/");
        root.addFile(dir2);
        
        Directory dir3 = new Directory(dir2, "dir3", dir2.getAbsolutePath()+"/");
        dir2.addFile(dir3);
        
        System.out.println(dir1.getName());
        
        MyFile file1 = new MyFile(dir1, "file1.txt", dir1.getAbsolutePath()+"/");
        dir1.addFile(file1);  
        file1.setContent("noob");
        
        MyFile file2 = new MyFile(dir1, "file2.txt", dir1.getAbsolutePath()+"/");
        dir1.addFile(file2);
        file2.setContent("prueba");
        
        MyFile file3 = new MyFile(dir2, "file3.txt", dir2.getAbsolutePath()+"/");
        dir2.addFile(file3);
        file3.setContent("txt");
        
        MyFile file4 = new MyFile(dir3, "file4.txt", dir3.getAbsolutePath()+"/");
        dir3.addFile(file4);
        file4.setContent("otro");
        
        //Add
        System.out.println(file1.getAbsolutePath() + " S: " + file1.getSize());
        System.out.println(file2.getAbsolutePath() + " S: " + file2.getSize());
        System.out.println(file3.getAbsolutePath() + " S: " + file3.getSize());
        System.out.println(file4.getAbsolutePath() + " S: " + file4.getSize());
        System.out.println(dir1.getAbsolutePath() + " S: " + dir1.getSize());
        System.out.println(dir2.getAbsolutePath() + " S: " + dir2.getSize());
        System.out.println(dir3.getAbsolutePath() + " S: " + dir3.getSize());
        System.out.println("Root " + root.getSize());
        
        //Delete
        dir1.deleteFile("file1.txt");
        dir2.deleteFile("dir3");
        System.out.println("Root " + root.getSize());
        
        System.out.println("\n----- File System test -----\n");
        FileSystem fs = FileSystem.getContext();
        fs.create("Me", 300);
        fs.mkdir("Me", "dir1");
        fs.file("Me", "file1", "noob"); //4 char
        fs.test("Me");
        fs.file("Me", "file2", "Esta es una prueba");   //18 char
        fs.test("Me");
        
        System.out.println("");
        System.out.println(Arrays.toString(("dir1").split("/")));
        System.out.println(Arrays.toString(("../ok/lol").split("/")));
        
        System.out.println(fs.cd("Me", "dir1"));
        fs.mkdir("Me", "dir2");
        System.out.println(fs.cd("Me", "dir2"));
        System.out.println(fs.cd("Me", "../.."));
        fs.mkdir("Me", "dir3");
        System.out.println(fs.cd("Me", "dir3"));
        fs.mkdir("Me", "dir4");
        fs.mkdir("Me", "dir5");
        System.out.println(fs.cd("Me", "dir4"));
        System.out.println(fs.cd("Me", "../../dir1/dir2"));
        System.out.println(fs.cd("Me", "../../dir3/dir4"));
        System.out.println(fs.cd("Me", "../.."));
        System.out.println("DU: " + fs.du("Me", "file2"));
        System.out.println(Arrays.toString(fs.ls("Me")));
        System.out.println(fs.cd("Me", "dir1"));
        System.out.println(Arrays.toString(fs.ls("Me")));
        
        fs.cd("Me", "dir2");
        System.out.println("PWD: " + fs.pwd("Me"));
        
        fs.cd("Me", "../..");
        fs.test("Me", "file1");
        System.out.println("CAT: " + Arrays.toString(fs.cat("Me", new String[]{"file1"})));
        System.out.println("CAT: " + Arrays.toString(fs.cat("Me", new String[]{"file1", "file2"})));
        
        System.out.println("split test: " + Arrays.toString("/file1".split("/")));
        System.out.println("MV: " + fs.mv("Me", "/file1", "/dir1/file1", true));
        System.out.println("LS: " + Arrays.toString(fs.ls("Me")));
        fs.cd("Me", "dir1");
        System.out.println("LS: " + Arrays.toString(fs.ls("Me")));
        
        System.out.println("CPVV:" +fs.cpyVV("Me", "/dir1/file1", "/file1", true));
        System.out.println("LS: " + Arrays.toString(fs.ls("Me")));
        fs.cd("Me", "..");
        System.out.println("LS: "+Arrays.toString(fs.ls("Me")));
        System.out.println("CPV: "+fs.cpyVR("Me", "/home/esteban/Desktop", "/"));
        System.out.println("LS: " + Arrays.toString(fs.ls("Me")));
        System.out.println("CAT: "+ Arrays.toString(fs.cat("Me", new String[]{"example.pl"})));
        System.out.println("DU: " + fs.du("Me", ""));
        System.out.println("RM: " + Arrays.toString(fs.rm("Me", new String[]{"file1"}, true)));
        System.out.println("LS: " + Arrays.toString(fs.ls("Me")));
        System.out.println("DU: " + fs.du("Me", ""));
        fs.cd("Me", "..");
        
        fs.file("Me", "file3", "nuevo archivo"); //13 char
        fs.test("Me");
        System.out.println("LS: " + Arrays.toString(fs.ls("Me")));
        System.out.println("MV: " + fs.mv("Me", "/file3", "/file4", true));
        System.out.println("LS: " + Arrays.toString(fs.ls("Me")));
        
        fs.cd("Me", "dir1");
        fs.file("Me", "file4", "filename repetido");
        fs.file("Me", "asdf.doc", ".doc file");
        fs.cd("Me", "..");
        fs.file("Me", "otro.doc", ".doc file");
        fs.mkdir("Me", "dir2");
        System.out.println("FIND:" + Arrays.toString(fs.find("Me", "file4")));
        System.out.println("FIND:" + Arrays.toString(fs.find("Me", "*.doc")));
        System.out.println("FIND:" + Arrays.toString(fs.find("Me", "dir2")));
        
        System.out.println("TREE:" + fs.tree("Me"));
    }
    
}
