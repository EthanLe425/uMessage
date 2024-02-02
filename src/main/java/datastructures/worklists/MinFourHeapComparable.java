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
        this.data=(E[]) new Object[10];
        this.size=1;
    }

    @Override
    public boolean hasWork() {
        if(this.size-1==0){
            return false;
        }
        return true;
    }
    @Override
    public void add(E work)  {
        if(this.data.length==this.size-1){
            E[] copy=(E[]) new Object[this.data.length*2];
                    for(int i=0; i<this.data.length;i++){
                        copy[i]=this.data[i];
                    }
                    this.data=copy;
        }
        this.data[size-1]=work;
        this.size++;
        int size2=this.size;
        while(this.data[(int)Math.floor((size2+2)/4)-1].compareTo(work)<0){
            this.data[size2]=this.data[(int)Math.floor((size2+2)/4)-1];
            this.data[(int)Math.floor((size2+2)/4)-1]=work;
            size2=(int)Math.floor((size2+2)/4);
        }
    }

    @Override
    public E peek() {
        if(this.size==1){
            throw new NoSuchElementException();
        }
        return this.data[0];
    }

    @Override
    public E next() {
        if(this.size==1){
            throw new NoSuchElementException();
        }
        E ans=this.data[0];
        this.data[0]=this.data[size-1];
        size--;
        int index=1;
        E temp= this.data[0];
        E min= this.data[(4*index)-3];
        while((temp.compareTo(this.data[(4*index)-3])>0)||(temp.compareTo(this.data[(4*index)-2])>0)||(temp.compareTo(this.data[(4*index)-1])>0)||(temp.compareTo(this.data[(4*index)])>0)){
            min= this.data[(4*index)-3];
            for(int i=0;i<4;i++){
                if(min.compareTo(this.data[(4*index)-3+i])<0){
                    min=this.data[(4*index)-3+i];
                    index=4*index-2+i;
                }
            }
        }
        this.data[index-1]=temp;
        this.data[(int)Math.floor((index+2)/4)-1]=min;
        return ans;
    }

    @Override
    public int size() {
        return size-1;
    }

    @Override
    public void clear() {
        this.data=(E[]) new Object[10];
        this.size=0;
    }
}
