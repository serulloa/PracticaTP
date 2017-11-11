package tp.util;

/*  
 * Author: Simon Pickin, 28/09/17
 */

import java.util.Random;

// A primitive arrayList in form of an array of Object (Java arrays are covariant).
// It is created with a certain capacity and is currently not redimensioned.
// This class is implemented to avoid using the Java Collections classes.

// In order to use the foreach loop, ArrayAsList should implement the Iterable interface
// which is currently not the case.

public class ArrayAsList {
		
	private Object[] arrayAsList;
	private int capacity = 100;  // capacity with default value (= ArrayAsList.length)
	private int size = 0;        // number of elements in the list

	// constructor with specified capacity
	public ArrayAsList(int initialCapacity) {
		if (! (capacity>0))
			// Should we suffer in silence (in absence of exceptions)?
			System.err.println("The initial capacity must be positive");
		else{
			capacity = initialCapacity;
			arrayAsList = new Object[capacity];
		}
	}

	// constructor with default capacity
	public ArrayAsList() {
		arrayAsList = new Object[capacity];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public Object get(int index) {
		if (index < 0 || index > size-1) {
			// There is no element at position "index"
			return null;
		} else
			return arrayAsList[index];
	}

	// In this implementation, the list does not redimension itself.
	public boolean add(Object anObject){
		if (size == capacity) 
			// The list is full and does not redimension itself
			return false;
		else {
			arrayAsList[size] = anObject;
			size++;
			return true;
		}
	}

	public boolean remove(int index) {
		if (index < 0 || index > size-1)
			// There is no element at position "index"
			return false;
		else {
			for(int i = index; i < size-1; i++)
				arrayAsList[i] = arrayAsList[i+1];
			size--;
			return true;
		}
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		if ( size > 0 ) {
			sb.append(arrayAsList[0].toString());
			for (int i=1; i<size; i++) {
				sb.append("\t");
				sb.append(arrayAsList[i].toString());
			}
		}
		return sb.toString();
	}
	
	// Method similar to Collections.shuffle()
	public static void shuffle(ArrayAsList list, Random random) {
        for (int i = list.size(); i > 1; i--) {
            swap(list.arrayAsList, i - 1, random.nextInt(i));
        }
   	}
	
	public static Object choice(ArrayAsList list, Random random) {
		return list.get(random.nextInt(list.size()));
	}

	private static void swap(Object[] anArray, int i, int j) {
        	Object temp = anArray[i];
        	anArray[i] = anArray[j];
        	anArray[j] = temp;
	}
	
}
