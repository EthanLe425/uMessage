
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
        if(this.data.length==this.size+1){
            E[] copy=(E[]) new Comparable[this.data.length*2];
            for(int i=0; i<this.data.length;i++){
                copy[i]=this.data[i];
            }
            this.data=copy;
        }
        this.data[this.size]=work;
        this.size++;
        if(size<2){
            return;
        }
        int size2=this.size-1;
        while((this.data[(size2-1)/4].compareTo(work))>0&&size2>0){
            this.data[size2]=this.data[(size2-1)/4];
            this.data[(size2-1)/4]=work;
            size2=(size2-1)/4;
        }
    }

    @Override
    public E peek() {
        if(this.size==0){
            throw new NoSuchElementException();
        }
        return this.data[0];
    }

    @Override
    public E next() {
        if(!this.hasWork()){
            throw new NoSuchElementException();
        }
        if(this.size==1){
            E pow= this.data[0];
            this.size--;
            this.data[0]=null;
            return pow;
        }
        E ans=this.data[0];
        this.data[0]=this.data[size-1];
        this.data[size-1]=null;
        this.size--;
        if(this.size>1) {
            int index = 0;
            int minin=minind(index);
            while (minin!=-1 && this.data[index].compareTo(this.data[minin])>0) {
                E temp=this.data[minin];
                this.data[minin]=this.data[index];
                this.data[index]=temp;
                index=minin;
                int lol=4*index+1;
                minin=minind(lol);
            }
        }
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
        this.data=(E[]) new Comparable[10];
        this.size=0;
    }
}