
package datastructures.dictionaries;

import com.sun.net.httpserver.Filter;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import datastructures.worklists.ArrayStack;
import datastructures.dictionaries.MoveToFrontList;

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
    public void delete(K key) {throw new UnsupportedOperationException();

    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}