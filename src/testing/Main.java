/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import fileSystem.*;
import java.util.Arrays;

/**
 *
 * @author Adonis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        
        File file1 = new File(dir1, "file1.txt", dir1.getAbsolutePath()+"/");
        dir1.addFile(file1);  
        file1.setContent("noob");
        
        File file2 = new File(dir1, "file2.txt", dir1.getAbsolutePath()+"/");
        dir1.addFile(file2);
        file2.setContent("prueba");
        
        File file3 = new File(dir2, "file3.txt", dir2.getAbsolutePath()+"/");
        dir2.addFile(file3);
        file3.setContent("txt");
        
        File file4 = new File(dir3, "file4.txt", dir3.getAbsolutePath()+"/");
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
        System.out.println(fs.cd("Me", "dir4"));
        System.out.println(fs.cd("Me", "../../dir1/dir2"));
        System.out.println(fs.cd("Me", "../../dir3/dir4"));
        System.out.println(fs.cd("Me", "../.."));
        System.out.println(fs.du("Me", "file2"));
        System.out.println(Arrays.toString(fs.ls("Me")));
        System.out.println(fs.cd("Me", "dir1"));
        System.out.println(Arrays.toString(fs.ls("Me")));
        
        fs.cd("Me", "dir2");
        System.out.println("PWD: " + fs.pwd("Me"));
        
        fs.cd("Me", "../..");
        System.out.println("CAT: " + Arrays.toString(fs.cat("Me", new String[]{"file1"})));
        System.out.println("CAT: " + Arrays.toString(fs.cat("Me", new String[]{"file1", "file2"})));
        
        
    }
    
}
