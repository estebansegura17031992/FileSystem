/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileSystem;

/**
 *
 * @author Adonis
 */
public class FileStruct {
    
    private FileStruct parent;
    private String name;
    private String path;
    private int size;

    public FileStruct(FileStruct parent, String name, String path, int size) {
        this.parent = parent;
        this.name = name;
        this.path = path;
        this.size = size;
    }
    
    /**
     * @return the parent
     */
    public FileStruct getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(FileStruct parent) {
        this.parent = parent;
    }

    /**
     * @return the name
     */
    public String getName() {
        return (name.equals("")) ? "/" : name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    public String getAbsolutePath() {
        return path + name;
    }
    
    /**
     * @return the path
     */
    public String getRelativePath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }  

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
    
}
