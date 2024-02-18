package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        recurs(array,comparator,0,array.length);
    }
    private static<E> void recurs(E[]arr,Comparator<E> comp,int start,int end){
        if(end-start<2){
            return;
        }
        int firs=start-1;
        E last=arr[end-1];
        for(int i=start;i<end;i++){
            if(comp.compare(arr[i],last)<=0){
                firs++;
                E copy=arr[firs];
                arr[firs]=arr[i];
                arr[i]=copy;
            }
        }
        recurs(arr,comp,start,firs);
        recurs(arr,comp,firs+1,end);
    }
}
