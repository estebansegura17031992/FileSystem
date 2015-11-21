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
            file.clean();
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

    @Override
    public void clean() {
        this.setParent(null);
        
        this.content.stream().forEach((file) -> {
            file.clean();
        });
    }
    
    public ArrayList<String> getPreOrderTraversal() {
        ArrayList<String> preOrder = new ArrayList<>();
        buildPreOrder(this, preOrder);
        printTree();
        return preOrder;
    }
    
    private void buildPreOrder(FileStruct node, ArrayList<String> preOrder) {
        preOrder.add(node.getName());
        if(node instanceof Directory){
            ((Directory)node).content.stream().forEach((child) -> {
                buildPreOrder(child, preOrder);
            });
        }
    }
    
    public ArrayList<String> findAll(String regex){
        ArrayList<String> result = new ArrayList<>();
        findAll(result, regex);
        return result;
    }
    
    private void findAll(ArrayList<String> filesList, String regex){
        content.stream().forEach((file) -> {
            if(file.getName().matches(regex))
                filesList.add(file.getAbsolutePath());
            if (file instanceof Directory) {
                filesList.addAll(((Directory)file).findAll(regex));
            }
        });
    }
    
    @Override
    public String printTree() {
        return printTree("", true);
    }

    @Override
    protected String printTree(String prefix, boolean isTail) {
        String result = "\n" + prefix + (isTail ? "└─ " : "├─ ") + this.getName();
        
        for (int i = 0; i < content.size() - 1; i++) {
            result += content.get(i).printTree(prefix + (isTail ? "    " : "│   "), false);
        }
        if (content.size() > 0) {
            result += content.get(content.size() - 1).printTree(prefix + (isTail ? "    " : "│   "), true);
        }
        return result;
    }
}
