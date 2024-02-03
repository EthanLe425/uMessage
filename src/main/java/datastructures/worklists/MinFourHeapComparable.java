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
        int index=1;
        if(size<=1){
            return ans;
        }
        E temp= this.data[0];
        E min= null;
        while((temp.compareTo(this.data[(4*index)-3])>0)||(temp.compareTo(this.data[(4*index)-2])>0)||(temp.compareTo(this.data[(4*index)-1])>0)||(temp.compareTo(this.data[(4*index)])>0)){
            min= this.data[(4*index)-3];
            for(int i=0;i<4;i++){
                if(min.compareTo(this.data[(4*index)-3+i])>0){
                    min=this.data[(4*index)-3+i];
                    index=(4*index)-3+i;
                }
            }
        }
        this.data[index]=temp;
        this.data[(int)Math.floor((index-1)/4)]=min;
        return ans;
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
