package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.ArrayStack;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    // TODO: Implement me!
    private class AVLNode extends BSTNode{
        private int height;
        public AVLNode(K key, V value){
            super(key,value);
            this.height=0;
        }
    }
    public AVLTree(){
        super();
    }
    public V insert(K key, V value){
        if(key==null||value==null){
            throw new IllegalArgumentException();
        }
        AVLNode curr= this.search(key);
        V temp=curr.value;
        curr.value=value;
        return temp;
    }
    private AVLNode search(K key){
        ArrayStack<AVLNode> next= new ArrayStack<>();
        if(this.root==null){
            this.root=new AVLNode(key,null);
            this.size++;
            return (AVLNode) this.root;
        }
        AVLNode curr=(AVLNode) this.root;
        AVLNode prev= null;
        int sign=0;
        int branch=-1;
        while(curr!=null){
            next.add(curr);
            if(key.compareTo(curr.key)>0){
                sign=1;
                branch=1;
            }
            else if(key.compareTo(curr.key)==0){
                return curr;
            }
            else{
                sign=-1;
                branch=0;
            }
            curr= (AVLNode) curr.children[branch];
        }
        curr=new AVLNode(key,null);
        this.size++;
        AVLNode first=next.peek();
        next.add(curr);
        first.children[branch]=curr;
        if(first.children[1-branch]!=null){
            first.height=first.height+sign;

        }
        else{
            prev=this.imbal(next);
        }
        if(prev!=null){
            if(key.compareTo(prev.key)>=0){
                prev.children[1]=this.rot(next);
            }
            else{
                prev.children[0]=this.rot(next);
            }
        }
        return curr;
    }
    private AVLNode imbal(ArrayStack<AVLNode>next){
        AVLNode prev=null;
        AVLNode nex=null;
        AVLNode nex2=null;
        while(next.size()>1){
            nex2=nex;
            nex=next.next();
            prev=next.peek();
            if(nex==prev.children[1]){
                prev.height++;
            }
            else{
                prev.height--;
            }
            if(Math.abs(prev.height)==2){
                if(next.size()==1){
                    next.clear();
                    next.add(prev);
                    next.add(nex);
                    next.add(nex2);
                    this.root=this.rot(next);
                    return null;
                }
                else{
                    next.next();
                    AVLNode prev2=next.peek();
                    next.clear();
                    next.add(prev);
                    next.add(nex);
                    next.add(nex2);
                    return prev2;
                }
            }
        }
        return null;
    }
    private AVLNode rot(ArrayStack<AVLNode>pog){
        AVLNode nex2=pog.next();
        AVLNode nex=pog.next();
        AVLNode prev= pog.next();
        AVLNode stuff=null;
        K key1=prev.key;
        K key2=nex.key;
        K key3=nex2.key;
        int sign= -69;
        int branch= -420;
        if(key3.compareTo(key1)>0){
            sign=1;
            branch=1;
        }
        else if(key3.compareTo(key1)==0){
            sign=0;
            branch=1;
        }
        else{
            sign=-1;
            branch=0;
        }
        if((key1.compareTo(key3)<0&&key3.compareTo(key2)<0)||(key2.compareTo(key3)<0&&key3.compareTo(key1)<0)){
            prev.children[branch]=nex2;
            stuff=(AVLNode)nex2.children[branch];
            nex.children[1-branch]=stuff;
            nex2.children[branch]=nex;
            AVLNode temp= nex;
            nex=nex2;
            nex2=temp;
            nex.height=nex.height+sign;
            nex2.height= nex2.height+sign;
        }
        stuff=(AVLNode) nex.children[1-branch];
        prev.children[branch]=stuff;
        nex.children[1-branch]=prev;
        nex.height=nex.height-sign;
        prev.height=prev.height-(2*sign);
        return nex;
    }
}
