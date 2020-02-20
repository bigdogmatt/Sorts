/*
 * Matthew Kleman
 * 11/19/18
 * COMP 2100
 * Assignment #7
 */

import java.util.Arrays;
import java.util.Random;

public class Sorting 
{

	public static void quickSort(int[] array, int start, int end)
	{
        if (start < end) 
        { 
            int index = partition(array, start, end); 
  
            quickSort(array, start, index - 1); 
            quickSort(array, index + 1, end); 
        } 
	}
	
    private static int partition(int array[], int start, int end) 
    { 
        int part = array[end];  
        int index = (start - 1); 
        for (int i = start; i < end; i++) 
        {  
            if (array[i] <= part) 
            { 
                index++; 
                int temp = array[index]; 
                array[index] = array[i]; 
                array[i] = temp; 
            } 
        } 
  
        int temp = array[index + 1]; 
        array[index + 1] = array[end]; 
        array[end] = temp; 
  
        return index + 1; 
    } 

	public static void mergeSort(int[] array, int[] scratch, int start, int end)
	{
		if(start < end - 1)
		{
			int middle = (end+start)/2;
			mergeSort(array, scratch, start, middle);
			mergeSort(array, scratch, middle, end);
			merge(array, scratch, start, middle, end);
		}
	}

	private static void merge(int[] values, int[] scratch, int start, int mid, int end)
	{
		for(int i = start; i < end; i++)
			scratch[i] = values[i];

		int a = start;
		int b = mid;
		int i = start;

		while(a < mid && b < end)
		{
			if(scratch[a] <= scratch[b])
			{
				values[i] = scratch[a];
				a++;
			}
			else
			{
				values[i] = scratch[b];
				b++;
			}
			i++;
		}

		while(a < mid )
		{
			values[i] = scratch[a];
			a++;
			i++;
		}
		while(b < end )
		{
			values[i] = scratch[b];
			b++;
			i++;
		}

	}

	public static void heapSort(int[] array)
	{
		int len = array.length; 

		for (int i = (len / 2) - 1; i >= 0; i--) 
			bubbleDown(array, len, i); 
 
		for (int i=len-1; i>=0; i--) 
		{ 
			int temp = array[0]; 
			array[0] = array[i]; 
			array[i] = temp; 

			bubbleDown(array, i, 0); 
		}
	}

	private static void bubbleDown(int array[], int end, int index) 
	{ 
		int largest = index; 
		int left = (2 * index) + 1; 
		int right = (2 * index) + 2; 

		if (left < end && array[left] > array[largest]) 
			largest = left; 

		if (right < end && array[right] > array[largest]) 
			largest = right; 

		if (largest != index) 
		{ 
			int temp = array[index]; 
			array[index] = array[largest]; 
			array[largest] = temp; 
			bubbleDown(array, end, largest); 
		} 
	} 


	public static void radixSort(int[] array, int digits)
	{
		int power = 1;
		int[] scratch = new int[array.length];
		for( int i = 0; i < digits; i++ )
		{
			int[] count = new int[11];

			for( int j = 0; j < array.length; j++ )
			{
				int digit = ( array[j] / power ) % 10;
				count[digit + 1]++;
			}

			for( int j = 1; j < count.length; j++ )
				count[j] += count[j-1];

			for( int j = 0; j < array.length; j++ )
			{
				int digit = ( array[j] / power ) % 10;
				scratch[count[digit]] = array[j];
				count[digit]++;
			}
			
			for( int j = 0; j < scratch.length; j++ )
				array[j] = scratch[j];
	
			power*=10;
		}
	}
	
	public static void main(String[] args)
	{	
		Random ran = new Random();
		for(int i = 10000; i <= 1000000; i = i*10)
		{
			int[] array = new int[i];
			int[] scratch = new int[i];
			
			for(int j = 0; j < array.length; j++)
				array[j] = ran.nextInt(1000000);
			
			
			int[] array2 = Arrays.copyOf(array, array.length);
			int[] array3 = Arrays.copyOf(array, array.length);
			int[] array4 = Arrays.copyOf(array, array.length);
			
			long start = System.nanoTime();
			quickSort(array, 0, array.length - 1);
			long end = System.nanoTime();
			double difQuick = (end - start) /  1000000000.0;
			
			start = System.nanoTime();
			mergeSort(array2, scratch, 0, array.length);
			end = System.nanoTime();
			double difMerge = (end - start) /  1000000000.0;
			
			start = System.nanoTime();
			heapSort(array3);
			end = System.nanoTime();
			double difHeap = (end - start) /  1000000000.0;
			
			start = System.nanoTime();
			radixSort(array4, 6);
			end = System.nanoTime();
			double difRadix = (end - start) /  1000000000.0;
			
			System.out.println("Array length: " + i);
			System.out.format("Quicksort: %.4f seconds %n", difQuick);
			System.out.format("Mergesort: %.4f seconds %n", difMerge);
			System.out.format("Heapsort: %.4f seconds %n", difHeap);
			System.out.format("Radixsort: %.4f seconds %n", difRadix);
			System.out.println("");	
		}
	}
}
