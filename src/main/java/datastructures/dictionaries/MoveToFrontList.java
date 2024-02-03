package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;

import java.util.Iterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    public MFLnode head;
    private class MFLnode extends Item<K,V>{
        private MFLnode next;
        public MFLnode(K key, V val){
            super(key, val);
            this.next=null;

        }
        public MFLnode(K key, V val, MFLnode nex){
            super(key,val);
            this.next=nex;
        }
    }
    public MoveToFrontList(){
        super();
        this.head=null;
        this.size=0;
    }
    @Override
    public V insert(K key, V value) {
        if(key==null||value==null){
            throw new IllegalArgumentException();
        }
        if(this.head==null){
            this.head= new MFLnode(key, value, null);
                    size++;
            return null;
        }
        V val= find(key);
        if(val==null){
            MFLnode put= new MFLnode(key,value);
            put.next=this.head;
            this.head=put;
            size++;
            return val;
        }
        this.head.value=value;
        return val;
    }

    @Override
    public V find(K key) {
        if(key==null){
            throw new IllegalArgumentException();
        }
        if(this.head==null){
            return null;
        }
        MFLnode curr=this.head;
        MFLnode check= null;
        while(!curr.key.equals(key) && curr!=null){
            check=curr;
            curr=curr.next;
        }
        if(curr==null){
            return null;
        }
        if(check==null){
            return this.head.value;
        }
        check.next=curr.next;
        curr.next=this.head;
        this.head=curr;
        return curr.value;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MFLit();
    }
    private class MFLit extends SimpleIterator<Item<K,V>>{
        private MFLnode curr;
        public MFLit(){
            this.curr=MoveToFrontList.this.head;
        }
        public boolean hasNext(){
            return this.curr!=null &&curr.next!=null;
        }
        public Item<K,V> next(){
           Item<K,V> item=new Item<>(this.curr.key,this.curr.value);
           this.curr=this.curr.next;
           return item;
        }
    }
}
