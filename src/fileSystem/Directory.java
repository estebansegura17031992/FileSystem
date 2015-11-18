package fileSystem;

import java.util.ArrayList;
import libraries.Messages;

public class Directory extends FileStruct {
    
    private ArrayList<FileStruct> content;

    public Directory(Directory parent, String name, String path){
        super(parent, name, path, 0);
        this.content = new ArrayList<>();
    }
    
    /**
     * @return the content
     */
    public ArrayList<FileStruct> getContent() {
        return content;
    }
    
    /**
     * @param content the content to set
     */
    public void setContent(ArrayList<FileStruct> content) {
        this.content = content;
    }
    
    public void addFile(FileStruct file){
        this.content.add(file);
        addSize(file.getSize());
    }
    
    public void addSize(int size){
        this.setSize(this.getSize() + size);
        if(this.getParent() != null)
            ((Directory)this.getParent()).addSize(size);
    }
    
    public Messages deleteFile(String filename){
        FileStruct file = find(filename);
        if(file != null){
            content.remove(file);
            subSize(file.getSize());
            
            return Messages.SUCCESS;
        }
        return Messages.FILENOTFOUND;
    }
    
    public void subSize(int size){
        this.setSize(this.getSize() - size);
        if(this.getParent() != null)
            ((Directory)this.getParent()).subSize(size);
    }
    
    public FileStruct find(String filename){
        for (FileStruct file : content) {
            if(file.getName().equals(filename))
                return file;
        }
        return null;
    }
    
}
