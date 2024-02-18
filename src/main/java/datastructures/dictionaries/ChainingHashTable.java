package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * - You must implement a generic chaining hashtable. You may not
 *   restrict the size of the input domain (i.e., it must accept
 *   any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given PRIME_SIZES list.
 *   Past this, it should continue to resize using some other mechanism (primes not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 *   dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private double load;
    private int start;
    private Dictionary<K,V>[]arr;
    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.load=0.9;
        this.arr=new Dictionary[10];
    }

    @Override
    public V insert(K key, V value) {
       if((double)this.size/this.arr.length>=this.load){
           Dictionary<K, V>[] copy;
           if(this.start<arr.length){
               copy=new Dictionary[PRIME_SIZES[this.size++]];
           }
           else{
               copy=new Dictionary[this.size*2];
           }
           Iterator<Item<K,V>> iter=iterator();
           while(iter.hasNext()){
               Item<K,V> nex=iter.next();
               int ind=Math.abs((nex.key.hashCode()*31)%copy.length);
               if(copy[ind]==null){
                   copy[ind]=this.newChain.get();
               }
               Dictionary<K,V>next=copy[ind];
               next.insert(nex.key,nex.value);
           }
           this.arr=copy;
       }
       int index=Math.abs((key.hashCode()*31)%arr.length);
           if(this.arr[index]==null){
               this.arr[index]= newChain.get();
           }
           Dictionary<K,V> curr=arr[index];
           int size2=curr.size();
          V ans=curr.insert(key,value);
          if(size2!=curr.size()){
              this.size++;
          }
          return ans;
    }

    @Override
    public V find(K key) {
        int ind=Math.abs((key.hashCode()*31)%arr.length);
        if(this.arr[ind]==null){
            return null;
        }
        return this.arr[ind].find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        if(arr[0]==null){
            arr[0]= newChain.get();
        }
        Iterator<Item<K,V>> iter=new Iterator<>() {
            private int start=0;
            Iterator<Item<K,V>> pog= arr[0].iterator();
            @Override
            public boolean hasNext() {
                if(start<arr.length&&!pog.hasNext()){
                    if(arr[start]==null){
                        start++;
                        while(arr[start]==null){
                            start++;
                            if(start>=arr.length){
                                return false;
                            }
                        }
                    }
                    else{
                        start++;
                    }
                if(start<arr.length){
                    pog=arr[start].iterator();
                }
                }
                if(start<arr.length){
                    return pog.hasNext();
                }
        return false;
            }

            @Override
            public Item<K, V> next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                return pog.next();
            }
        };
        return iter;
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
}
