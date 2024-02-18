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
    private int start;
    private Dictionary<K,V>[]arr;
    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.start=0;
        this.arr=new Dictionary[7];
    }

    @Override
    public V insert(K key, V value) {
       if((double)this.size/this.arr.length>=2){
           Dictionary<K, V>[] copy;
           if(this.start<PRIME_SIZES.length){
               copy=new Dictionary[PRIME_SIZES[this.start++]];
           }
           else{
               copy=new Dictionary[this.arr.length*2];
           }
           Iterator<Item<K,V>> iter=iterator();
           while(iter.hasNext()){
               Item<K,V>next=iter.next();
               int ind=Math.abs(key.hashCode()%copy.length);
               if(copy[ind]==null){
                   copy[ind]=this.newChain.get();
               }
               Dictionary<K,V> list=copy[ind];
               list.insert(next.key,next.value);
           }
           this.start++;
           this.arr=copy;
       }
       int index=Math.abs((key.hashCode())%arr.length);
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
        int ind=Math.abs((key.hashCode())%arr.length);
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
            private int start=-1;
            Iterator<Item<K,V>> pog;
            @Override
            public boolean hasNext() {
                if(!pog.hasNext()||start<0){
                    start++;
                    while(start<arr.length&&(arr[start].isEmpty()||arr[start]==null)){
                        start++;
                    }
                    if(start>=arr.length){
                        pog=null;
                        return false;
                    }
                    pog=arr[start].iterator();

                }
                if(pog!=null){
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
