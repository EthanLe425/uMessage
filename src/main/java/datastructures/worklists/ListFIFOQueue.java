
package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private static class ListNode<E>{
        ListNode next;
        E work;
        public ListNode(E work){
            this.work=work;
            this.next=null;
        }
    }

    private ListNode front;
    private ListNode back;
    private int size;
    public ListFIFOQueue() {
        this.size=0;
        this.front=null;
        this.back=null;
    }

    @Override
    public void add(E work) {
        if(this.front==null){
            this.front=new ListNode<E>(work);
            this.back=this.front;
            this.size++;
        }
        else {
            this.back.next = new ListNode<E>(work);
            this.back = this.back.next;
            this.size++;
        }
    }

    @Override
    public E peek() {
        if(this.front==null|| this.front.work==null){
            throw new NoSuchElementException();
        }
        else{
            ListNode pog = this.front;
            return (E) pog.work;
        }
    }

    @Override
    public E next() {
        if(this.front==null || this.front.work==null){
            throw new NoSuchElementException();
        }
        else{
            E node= (E)this.front.work;
            this.front = this.front.next;
            this.size--;
            return node;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size=0;
        this.front=null;
        this.back=null;
    }
}