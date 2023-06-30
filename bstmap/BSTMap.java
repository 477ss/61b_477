package bstmap;

import edu.princeton.cs.algs4.LinkedQueue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{
    private class Node{
        K key;
        V value;
        Node left,right;

        public Node(K key,V value,Node left,Node right){
            this.key=key;
            this.value=value;
            this.left=left;
            this.right=right;
        }
        public Node(K key,V value){
            this.key=key;
            this.value=value;
        }
    }

    Node root=null;

    @Override
    public void clear() {
        root=null;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root,key);
    }

    private boolean containsKey(Node n,K key){
        if(n==null){
            return false;
        }
        if(key.compareTo((K)n.key)==0){
            return true;
        }else if(key.compareTo((K)n.key)<0){
            return containsKey(n.left,key);
        }else{
            return containsKey(n.right,key);
        }
    }

    @Override
    public V get(K key) {
        return get(root,key);
    }

    private V get(Node n, K key){
        if(n==null){
            return null;
        }
        if(key.compareTo((K)n.key)==0){
            return n.value;
        }else if(key.compareTo((K)n.key)<0){
            return get(n.left,key);
        }else{
            return get(n.right,key);
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node n){
        if(n==null){
            return 0;
        }
        return 1+size(n.left)+size(n.right);
    }

    @Override
    public void put(K key, V value) {
        root=put(root,key,value);
    }

    private Node put(Node n, K key, V value) {
        if(n==null){
            return new Node(key,value);
        }else if(key.compareTo((K)n.key)==0){
            n.value=value;
        }else if(key.compareTo((K)n.key)<0){
            n.left=put(n.left,key,value);;
        }else{
            n.right=put(n.right,key,value);
        }
        return n;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> ret=new HashSet<>();
        for(K k:this){
            ret.add(k);
        }
        return ret;
    }

    @Override
    public V remove(K key) {
        Node retNode=new Node(key,null);
        root=remove(root,key,retNode);
        return retNode.value;
    }

    private Node remove(Node n, K key, Node retNode){
        if(n==null){
            return null;
        }
        if(key.compareTo((K)n.key)==0){
            retNode.value=n.value;
            if(n.right==null){
                return n.left;
            }else if(n.left==null){
                return n.right;
            }else{
                set_left_max(n.left,n);
                return n;
            }
        }else if(key.compareTo((K)n.key)<0){
            n.left=remove(n.left,key,retNode);
            return n;
        }else{
            n.right=remove(n.right,key,retNode);
            return n;
        }
    }

    private Node set_left_max(Node n,Node target){
        if(n.right==null){
            target.key=n.key;
            target.value=n.value;
            return n.left;
        }
        n.right=set_left_max(n.right,target);
        return n;
    }

    @Override
    public V remove(K key, V value) {
        Node retNode=new Node(key,null);
        remove(root,key,retNode);
        if(value==retNode.value){
            return value;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<K>{
        LinkedQueue<Node> l=new LinkedQueue<>();

        public BSTIterator(){
            l.enqueue(root);
        }

        @Override
        public boolean hasNext() {
            return !l.isEmpty();
        }

        @Override
        public K next() {
            Node n=l.dequeue();
            if(n.left!=null){
                l.enqueue(n.left);
            }
            if(n.right!=null){
                l.enqueue(n.right);
            }
            return n.key;
        }
    }
}