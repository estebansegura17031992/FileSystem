package libraries;

import java.util.ArrayList;

/**
 *
 * @author Adonis
 * @param <T>
 */
public class Tree<T> {

    private Node<T> root;

    public Tree(Node<T> root) {
        this.root = root;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public boolean exists(T key) {
        return find(root, key);
    }

    public int getNumberOfNodes() {
        return getNumberOfDescendants(root) + 1;
    }

    public int getNumberOfDescendants(Node<T> node) {
        int n = node.getChildren().size();
        
        for (Node<T> child : node.getChildren())
            n += getNumberOfDescendants(child);

        return n;
    }

    private boolean find(Node<T> node, T keyNode) {
        boolean res = false;
        if (node.getData().equals(keyNode))
            return true;

        for (Node<T> child : node.getChildren())
            if (find(child, keyNode))
                res = true;

        return res;
    }

    private Node<T> findNode(Node<T> node, T keyNode)
    {
        if(node == null)
            return null;
        if(node.getData().equals(keyNode))
            return node;

        Node<T> cnode;
        for (Node<T> child : node.getChildren())
            if ((cnode = findNode(child, keyNode)) != null)
                return cnode;

        return null;         
    } 

    public ArrayList<Node<T>> getPreOrderTraversal() {
        ArrayList<Node<T>> preOrder = new ArrayList<>();
        buildPreOrder(root, preOrder);
        return preOrder;
    }

    public ArrayList<Node<T>> getPostOrderTraversal() {
        ArrayList<Node<T>> postOrder = new ArrayList<>();
        buildPostOrder(root, postOrder);
        return postOrder;
    }

    private void buildPreOrder(Node<T> node, ArrayList<Node<T>> preOrder) {
        preOrder.add(node);
        node.getChildren().stream().forEach((child) -> {
            buildPreOrder(child, preOrder);
        });
    }

    private void buildPostOrder(Node<T> node, ArrayList<Node<T>> postOrder) {
        node.getChildren().stream().forEach((child) -> {
            buildPostOrder(child, postOrder);
        });
        postOrder.add(node);
    }

    public ArrayList<Node<T>> getLongestPathFromRootToAnyLeaf() {
        ArrayList<Node<T>> longestPath = null;
        int max = 0;
        for (ArrayList<Node<T>> path : getPathsFromRootToAnyLeaf()) {
            if (path.size() > max) {
                max = path.size();
                longestPath = path;
            }
        }
        return longestPath;
    }

    public int getMaxDepth()
    {
        return getLongestPathFromRootToAnyLeaf().size();
    }

    public ArrayList<ArrayList<Node<T>>> getPathsFromRootToAnyLeaf() {
        ArrayList<ArrayList<Node<T>>> paths = new ArrayList<>();
        ArrayList<Node<T>> currentPath = new ArrayList<>();
        getPath(root, currentPath, paths);

        return paths;
    }

    private void getPath(Node<T> node, ArrayList<Node<T>> currentPath,
                    ArrayList<ArrayList<Node<T>>> paths) {
        if (currentPath == null)
            return;

        currentPath.add(node);

        if (node.getChildren().isEmpty()) {
            // This is a leaf
            paths.add(clone(currentPath));
        }
        node.getChildren().stream().forEach((child) -> {
            getPath(child, currentPath, paths);
        });

        int index = currentPath.indexOf(node);
        for (int i = index; i < currentPath.size(); i++)
            currentPath.remove(index);
    }

    private ArrayList<Node<T>> clone(ArrayList<Node<T>> list) {
        ArrayList<Node<T>> newList = new ArrayList<>();
        list.stream().forEach((node) -> {
            newList.add(new Node<>(node));
        });

        return newList;
    }
}