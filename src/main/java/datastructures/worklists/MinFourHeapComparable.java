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
        this.data[size]=work;
        this.size++;
        if(size<2){
            return;
        }
        int size2=this.size;
        while((this.data[(int)Math.floor((size2-1)/4)].compareTo(work))<0&&size2>0){
            this.data[size2]=this.data[(int)Math.floor((size2-1)/4)];
            this.data[(int)Math.floor((size2-1)/4)]=work;
            size2=(int)Math.floor((size2-1)/4);
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
        if(this.size==0){
            throw new NoSuchElementException();
        }
        E ans=this.data[0];
        this.data[0]=this.data[size-1];
        this.data[size-1]=null;
        this.size--;
        if(this.size>0) {
            int index = 0;
            int minin=minind(index);
            while (minin!=-1 && this.data[index].compareTo(this.data[minin])>0) {
                E temp=this.data[index];
                this.data[index]=this.data[minin];
                this.data[minin]=temp;
                index=minin;
                minin=minind(index);
            }
        }
        return ans;
    }
    private int minind(int index){
        int min=-1;
        int ind=4*index+1;
        for(int i=0;i<4;i++){
            int curr=ind+i;
            if(curr<size){
                if(min==-1||this.data[curr].compareTo(this.data[min])<0){
                    min=curr;
                }
            }
        }
        return min;
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
