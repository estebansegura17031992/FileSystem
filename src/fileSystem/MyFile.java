package fileSystem;

public class MyFile extends FileStruct {
    
    private String content;
    
    public MyFile(Directory parent, String name, String path){
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

    @Override
    public void clean() {
        this.setParent(null);
    }
    
    @Override
    public String printTree() {
        return printTree("", true);
    }

    @Override
    protected String printTree(String prefix, boolean isTail) {
        return "\n" + prefix + (isTail ? "└─ " : "├─ ") + this.getName();
    }
}
