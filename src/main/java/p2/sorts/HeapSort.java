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
        for(int i=0;i<copy.length;i++){
            heap.add(copy[i]);
        }
        for(int j=0;j<copy.length;j++){
            copy[j]=heap.next();
        }
        array=copy;
    }
}
