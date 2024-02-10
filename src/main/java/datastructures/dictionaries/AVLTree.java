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
        int branch=-69;
        while(curr!=null){
            next.add(curr);
            if(key.compareTo(curr.key)>0){
                sign=1;
                branch=1;
            }
            else if(key.compareTo(curr.key)=0){
                return curr;
            }
            else{
                sign=-1;
                branch=0;
            }
            curr= (AVLNode) curr.children[branch];
        }
        curr=new AVLNode(key,null);
        next.add(curr);
        this.size++;
        AVLNode first=next.peek();
        next.add(curr);
        first.children[branch]=curr;
        if(first.children[1-branch]==null){
            prev=this.heights(next);
        }
        else{
            prev.height=prev.height+sign;
        }
        if(prev!=null){
            int sign2=0;
            int branch2=0;
            if(key.compareTo(prev.key)>0){
                sign2=1;
                branch2=2;
            }
            else if(key.compareTo(prev.key)=0){
                sign2=0;
                branch2=1;
            }
            else{
                sign2=-1;
                branch2=0;
            }
            prev.children[branch2]=this.rotate(next);
        }
        return curr;
    }
}
