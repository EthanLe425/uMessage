package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> heap= new MinFourHeap<E>(comparator);
        E[]copy=array;
        int ind=0;
        for(E next:copy){
            heap.add(next);
        }
        while(heap.size()>0){
            copy[ind]=heap.next();
            ind++;
        }
        array=copy;
    }
}
