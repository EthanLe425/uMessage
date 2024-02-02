package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] arr;
    private int front;
    public ArrayStack() {
        this.front=-1;
        this.arr= (E[]) new Object[10];
    }
    @Override
    public void add(E work) {
        if(this.front+1==this.arr.length){
            E[] copy = (E[]) new Object[arr.length*2];
            for(int i=0; i<this.arr.length;i++){
                copy[i]=this.arr[i];
            }
            this.arr=copy;
        }
        this.arr[this.front+1]=work;
        this.front++;
    }

    @Override
    public E peek() {
        if (this.front==-1) {
            throw new NoSuchElementException();
        }
        else{
            return this.arr[this.front];
        }
    }

    @Override
    public E next() {
        if(this.front==-1){
            throw new NoSuchElementException();
        }
        else{
            E ans= this.arr[this.front];
            this.front--;
            return ans;
        }
    }

    @Override
    public int size() {
        return this.front+1;
    }

    @Override
    public void clear() {
        this.front=-1;
        this.arr=(E[])new Object[10];
    }
}
