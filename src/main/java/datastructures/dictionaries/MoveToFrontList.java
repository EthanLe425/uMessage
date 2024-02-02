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
    public MoveToFrontList(){
        super();
        this.head=null;
    }
    private class MFLnode{
        private Item<K,V> data;
        private MFLnode next;
        public MFLnode(){
            this(null,null);
        }

        public MFLnode(Item<K,V> ins, MFLnode next) {
            this.data=ins;
            this.next=next;
        }
        public MFLnode(Item<K,V> ins){
            this(ins,null);
        }
    }
    @Override
    public V insert(K key, V value) {
        if(key==null||value==null){
            throw new IllegalArgumentException();
        }
        V vals=this.find(key);
        if(vals!=null){
            this.head.data.value=vals;
        }
        else{
            this.head=new MFLnode(new Item(key,value),this.head);
            this.size++;
        }
        return vals;
    }

    @Override
    public V find(K key) {
        if(key==null){
            throw new IllegalArgumentException();
        }
        if(this.head==null||this.head.data==null){
            return null;
        }
        if(this.head.data.key.equals(key)){
            return this.head.data.value;
        }
        MFLnode curr= this.head;
        V ans = null;
        while(curr.next!=null&&curr.next.data!=null&&curr.next.data.key.equals(key)){
            curr=curr.next;
        }
        if(curr.next!=null&&curr.next.data!=null){
            ans=curr.next.data.value;
            MFLnode temp=curr.next;
            curr.next=temp;
            temp.next=this.head;
            this.head=temp;
        }
        return ans;
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
           Item<K,V> item=curr.data;
           curr=curr.next;
           return item;
        }
    }
}
