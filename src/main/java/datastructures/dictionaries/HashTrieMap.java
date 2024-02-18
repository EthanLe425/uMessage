
package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import datastructures.worklists.ArrayStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = (Map<A, HashTrieNode>) new ChainingHashTable<>(()->new MoveToFrontList<>());
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if(key==null || value==null){
            throw new IllegalArgumentException();
        }
        if(this.root==null){
            this.root=new HashTrieNode();
        }
        V ans=null;
        if(key.isEmpty()){
            ans=this.root.value;
            this.root.value=value;
        }
        else {
            HashTrieNode curr = (HashTrieNode) this.root;
            for (A lett : key) {
                if (!curr.pointers.containsKey(lett)) {
                    curr.pointers.put(lett, new HashTrieNode());
                }
                curr = curr.pointers.get(lett);
            }
            ans = curr.value;
            curr.value = value;
        }
        if(ans==null){
            this.size++;
        }
        return ans;
    }

    @Override
    public V find(K key) {
        if(key==null){
            throw new IllegalArgumentException();
        }
        if(this.root==null){
            return null;
        }
        HashTrieNode fomb=(HashTrieNode) this.root;
        for(A lett:key){
            if(fomb.pointers.containsKey(lett)){
                fomb=fomb.pointers.get(lett);
            }
            else{
                return null;
            }
        }
        V ans=fomb.value;
        return ans;
    }

    @Override
    public boolean findPrefix(K key) {
        if(key==null){
            throw new IllegalArgumentException();
        }
        if(this.root==null){
            return false;
        }
        if(this.size==0){
            return false;
        }
        HashTrieNode fomb= (HashTrieNode)this.root;
        for(A lett:key){
            if(fomb.pointers.containsKey(lett)){
                fomb=fomb.pointers.get(lett);
            }
            else{
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if(key==null){
            throw new IllegalArgumentException();
        }
        ArrayStack<HashTrieNode>psps=new ArrayStack<HashTrieNode>();
        ArrayStack<A> charch=new ArrayStack<A>();
        HashTrieNode ayy=(HashTrieNode)this.root;
        for(A curr:key){
            if(!ayy.pointers.containsKey(curr)){
                return;
            }
            psps.add(ayy);
            charch.add(curr);
            ayy=ayy.pointers.get(curr);
        }
        if(ayy.value!=null) {
            this.size--;
            ayy.value = null;
            while (psps.size() > 0) {
                A pop=charch.next();
                HashTrieNode rem = psps.next();
                if (rem.pointers.containsKey(pop) && ayy.pointers.size()==0) {
                    rem.pointers.remove(pop);
                }
                if(rem.value!=null||rem.pointers.size()>0){
                    return;
                }
            }
        }

    }

    @Override
    public void clear() {
        this.size=0;
        this.root= new HashTrieNode();
    }
}