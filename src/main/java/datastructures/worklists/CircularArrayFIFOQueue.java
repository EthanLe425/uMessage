package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
    private int front;
    private int back;
    private int size;
    private E[] arr;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.size=0;
        this.front=0;
        this.back=0;
        this.arr= (E[]) new Comparable[capacity];
    }

    @Override
    public void add(E work) {

        if(this.isFull()){
            throw new IllegalStateException();
        }
        else{
            this.arr[this.back]=work;
            this.back=(this.back+1)%this.arr.length;
            this.size++;
        }
    }

    @Override
    public E peek() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        else{
            return this.arr[this.front];
        }
    }

    @Override
    public E peek(int i) {
        if(!this.hasWork()){
            throw new NoSuchElementException();
        }
        else if(i<0 || i>=size()){
            throw new IndexOutOfBoundsException();
        }
        else{
            return this.arr[(this.front+i)%this.arr.length];
        }

    }

    @Override
    public E next() {
        if(!this.hasWork()){
            throw new NoSuchElementException();
        }
        else{
            E ans= this.arr[this.front];
            this.front= (this.front+1)%this.arr.length;
            this.size--;
            return ans;
        }
    }

    @Override
    public void update(int i, E value) {
        if(!this.hasWork()){
            throw new NoSuchElementException();
        }
        else if(i<0 || i>=this.size){
            throw new IndexOutOfBoundsException();
        }
        else{
            this.arr[(this.front+i)%this.arr.length]=value;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size=0;
        this.front=0;
        this.back=0;
        this.arr=(E[]) new Comparable[this.capacity()];
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
