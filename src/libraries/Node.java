package libraries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Adonis
 * @param <T>
 */

public class Node<T> {
    
    private T data;
    private List<Node<T>> children;
    private Node<T> parent;

    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public Node(Node<T> node) {
        this.data = (T) node.getData();
        children = new ArrayList<>();
    }

    public void addChild(Node<T> child) {
        child.setParent(this);
        children.add(child);
    }

    public void addChildAt(int index, Node<T> child) {
        child.setParent(this);
        this.children.add(index, child);
    }

    public void setChildren(List<Node<T>> children) {
        children.stream().forEach((child) -> {
            child.setParent(this);
        });

        this.children = children;
    }

    public void removeChildren() {
        this.children.clear();
    }

    public Node<T> removeChildAt(int index) {
        return children.remove(index);
    }


    public void removeThisIfItsAChild(Node<T> childToBeDeleted)
    {
        List <Node<T>> list = getChildren();
        list.remove(childToBeDeleted);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return this.children;
    }

    public Node<T> getChildAt(int index) {
        return children.get(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;

        if (obj instanceof Node) {
            if (((Node<?>) obj).getData().equals(this.data))
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.data);
        hash = 97 * hash + Objects.hashCode(this.children);
        hash = 97 * hash + Objects.hashCode(this.parent);
        return hash;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

}