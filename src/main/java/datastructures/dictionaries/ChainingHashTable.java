package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
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
    private double count;
    private int count2;
    private int start;
    private Dictionary<K,V>[]arr;
    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.load=0.0;
        this.arr=new Dictionary[10];
        for(int i=0; i<10;i++){
            arr[i]=newChain.get();
        }
        start=0;
        count=0.0;
        count2=0;
    }

    @Override
    public V insert(K key, V value) {
       if(load>0){
           Dictionary<K,V>[] copy= new Dictionary[this.arr.length*2];
           for(int i=0;i<arr.length;i++){
               if(arr[i]!=null){
                   for(Item<K,V>thing:arr[i]){
                       int ind=Math.abs(thing.key.hashCode()%copy.length);
                       if(ind>=0){
                           if(copy[ind]==null){
                            copy[ind]=newChain.get();
                           }
                           copy[ind].insert(thing.key,thing.value);
                       }
                       else{
                           copy=new Dictionary[0];
                       }
                   }
               }
           }
           start++;
           this.arr=copy;
       }
       int index=Math.abs(key.hashCode()%arr.length);
       if(index>=0){
           if(this.arr[index]==null){
               this.arr[index]= newChain.get();
           }
           V ans=null;
           if(this.find(key)==null){
               count2++;
           }
           else{
               ans=this.find(key);
           }
           this.arr[index].insert(key,value);
           load=(count+1)/arr.length;
           return ans;
       }
       return null;
    }

    @Override
    public V find(K key) {
        throw new NotYetImplementedException();
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        throw new NotYetImplementedException();
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
