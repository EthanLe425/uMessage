
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
        this.data[this.size]=work;

        if(size<2){
            return;
        }
        int size2=this.size;
        int pog=0;
        while((this.data[(size2-1)/4].compareTo(work))>0&&size2>0){
            this.data[size2]=this.data[(size2-1)/4];
            size2=(size2-1)/4;
        }
        pog=size2;
        this.data[pog]=this.data[this.size];
        this.size++;
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
        E put=this.data[size];
        if(this.size>1) {
            int index = 0;
            int min=0;
            while (size>((index*4)+1)) {
                min=(4*index)+1;
                for(int i=min+1;i<min+4;i++){
                    if(i==size){
                        break;
                    }
                    if(this.data[min].compareTo(this.data[i])>0){
                        min=i;
                    }
                }
                if(data[min].compareTo(put)==-1){
                    this.data[index]=this.data[min];
                    index=min;
                }
                else{
                    break;
                }
            }
            this.data[index]=this.data[size];
        }
        return ans;
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