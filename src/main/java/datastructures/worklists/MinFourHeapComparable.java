
package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    public MinFourHeapComparable() {
        this.data=(E[]) new Comparable[10];
        this.size=0;
    }

    @Override
    public boolean hasWork() {
        if(this.size==0){
            return false;
        }
        return true;
    }
    @Override
    public void add(E work)  {
        if(this.data.length==this.size){
            E[] copy=(E[]) new Comparable[this.data.length*2];
            for(int i=0; i<this.size;i++){
                copy[i]=this.data[i];
            }
            this.data=copy;
        }
        int size2=size;
        while(size2>0 && this.data[(size2-1)/4].compareTo(work)>0){
            this.data[size2]=this.data[(size2-1)/4];
            size2=(size2-1)/4;
        }
        this.data[size2]=work;
        size++;
    }

    @Override
    public E peek() {
        if(!this.hasWork()){
            throw new NoSuchElementException();
        }
        return this.data[0];
    }

    @Override
    public E next() {
        if(!this.hasWork()){
            throw new NoSuchElementException();
        }
        E ans=this.data[0];
        this.size--;
        int size2=0;
        E pog=this.data[size];
        while(size>(size2*4+1)){
            int min=size2*4+1;
            int min2=Math.min(size,min+4);
            for(int i=min+1;i<min2;i++){
                if(this.data[min].compareTo(this.data[i])>0){
                    min=i;
                }
            }
            if(this.data[min].compareTo(pog)<0){
                this.data[size2]=this.data[min];
                size2=min;
            }
        }
        this.data[size2]=this.data[size];
        return ans;
    }
    private int minind(int index){
        int mint=-1;
        if(index>this.size){
            return mint;
        }
        mint=index;
        for(int i=index+1;i<index+4;i++){
            if(i<this.size && this.data[i].compareTo(this.data[mint])<0){
                mint=i;
            }
        }
        return mint;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        this.size=0;
    }
}