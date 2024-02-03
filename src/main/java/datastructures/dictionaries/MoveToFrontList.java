package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
    private MFLnode head;
    private class MFLnode extends Item<K,V>{
        private MFLnode next;
        public MFLnode(K key, V val, MFLnode nex){
            super(key,val);
            this.next=nex;
        }
    }
    public MoveToFrontList(){
        this.head=null;
        this.size=0;
    }
    @Override
    public V insert(K key, V value) {
        if(key==null||value==null){
            throw new IllegalArgumentException();
        }
        if(!this.head.key.equals(key)){
            this.head= new MFLnode(key,value,this.head);
            this.size++;
            return null;
        }
        if(this.head.equals(key)){
            V old=this.head.value;
            this.head.value=value;
            return old;
        }
        MFLnode curr=this.head;
        while(curr.next!=null&&!curr.next.key.equals(key)){
            curr=curr.next;
        }
        if(curr.next==null){
            this.head= new MFLnode(key,value,this.head);
            this.size++;
            return null;
        }
        MFLnode temp= curr.next;
        curr.next=curr.next.next;
        temp.next=this.head;
        this.head=temp;
        V ans=this.head.value;
        this.head.value=value;
        return ans;
    }

    @Override
    public V find(K key) {
        if(key==null){
            throw new IllegalArgumentException();
        }
        if(this.head==null){
            return null;
        }
        if(this.head.key.equals(key)){
            return this.head.value;
        }
        MFLnode curr=this.head;
        while(curr.next!=null && !curr.next.key.equals(key)){
            curr=curr.next;
        }
        if(curr.next==null){
            return null;
        }
        MFLnode temp=curr.next;
        curr.next=curr.next.next;
        temp.next=this.head;
        this.head=temp;
        return this.head.value;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MFLit();
    }
    private class MFLit implements Iterator<Item<K,V>>{
        private MFLnode curr;
        public MFLit(){
            curr=head;
        }
        public boolean hasNext(){
            return this.curr!=null;
        }
        public Item<K,V> next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
           Item<K,V> item=new Item<>(curr);
           this.curr=this.curr.next;
           return item;
        }
    }
}
