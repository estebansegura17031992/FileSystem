package fileSystem;

public class File extends FileStruct {
    
    private String content;
    
    public File(Directory parent, String name, String path){
        super(parent, name, path, 0);
        this.content = "";
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
        setSize(this.content.length());
        //add size to dir
        ((Directory)this.getParent()).addSize(getSize());
    }
}