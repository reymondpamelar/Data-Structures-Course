//Reymond Pamelar
//cssc1253

package data_structures;

import java.util.Iterator;

public class OrderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E>{
	private E[] elements;
	private int size=0;
	private int maxSize;
	
	public OrderedArrayPriorityQueue(){
	}
	
	@SuppressWarnings("unchecked")
	public OrderedArrayPriorityQueue(int max){
		size=0;
		maxSize=max;
		elements = (E[])new Comparable[maxSize];
	}
	
	// Inserts a new object into the priority queue. Returns true if
	 // the insertion is successful. If the PQ is full, the insertion
	 // is aborted, and the method returns false.
	 public boolean insert(E object) {
		if(isFull()) {
			return false;
		}
		int i;
		for(i=size-1; (i>=0 && ((Comparable<E>)elements[i]).compareTo(object) >= 0); i--) {
			elements[i+1]=elements[i];
		}
		elements[i+1]=object;
		size++;
		return true;

	 }

	 // Removes the object of highest priority that has been in the
	 // PQ the longest, and returns it. Returns null if the PQ is empty.
	 public E remove() {
		 if(isEmpty()) {
				return null;
		 }
		 for (int i = 0; i < size-1; i++) {
			 elements[i] = elements[i + 1]; 
		 }
		size--;
		return elements[0];
	 }

	 private int binSearch(E obj) {
		 int lo = 0;
		 int mid=0;
		 int hi = size;
		 while (lo<=hi) {
			 mid = (lo+hi)/2;
			 if(((Comparable<E>)elements[mid]).compareTo(obj) < 0) {
				 lo = mid+1;
			 }
			 else if(((Comparable<E>)elements[mid]).compareTo(obj) > 0) {
				 hi=mid-1;
			 }
			 else {
				 return mid;
			 }
		 }
		return -1;
	 }
	 
	 // Deletes all instances of the parameter obj from the PQ if found, and
	 // returns true. Returns false if no match to the parameter obj is found.
	 public boolean delete(E obj) {
		 if(binSearch(obj)==-1) {
			 return false;
		 }
		 while(binSearch(obj)!=-1) { 
			 for (int i = binSearch(obj); i < size; i++) 
				 elements[i] = elements[i + 1];;
				 size--;
		 }
		 return true;
	 }

	 // Returns the object of highest priority that has been in the
	 // PQ the longest, but does NOT remove it.
	 // Returns null if the PQ is empty.
	 public E peek() {
		 if(isEmpty()) {
			 return null;
		 }
		 int hi = 0;
		 for(int i = 1; i < size; i++)
		 {
			 if(((Comparable<E>)elements[i]).compareTo(elements[hi]) < 0)
			 {
				 hi = i;
			 }
		 }
		 return elements[hi];
	 }

	 // Returns true if the priority queue contains the specified element
	 // false otherwise.
	 public boolean contains(E obj) {
		 int begin = 0;
		 int mid=0;
		 int last = size;
		 while (begin<=last) {
			 mid = (begin+last)/2;
			 if(((Comparable<E>)elements[mid]).compareTo(obj) < 0) {
				 begin = mid+1;
			 }
			 else if(((Comparable<E>)elements[mid]).compareTo(obj) > 0) {
				 last=mid-1;
			 }
			 else {
				 return true;
			 }
		 }
		 return false;
	 }

	 // Returns the number of objects currently in the PQ.
	 public int size() {
		return size;
	 }

	 // Returns the PQ to an empty state.
	 public void clear() {
		 elements=null;
		 size=0;
	 }

	 // Returns true if the PQ is empty, otherwise false
	 public boolean isEmpty() {
		 if(size==0) {
			 return true;
		 }
		return false;
	 }

	 // Returns true if the PQ is full, otherwise false. List based
	 // implementations should always return false.
	 public boolean isFull() {
		 if(size==maxSize) {
			 return true;
		 }
		return false;
	 }

	 // Returns an iterator of the objects in the PQ, in no particular
	 // order.
	 public Iterator<E> iterator(){
		 return new Iterator<E>()
	       {          
	           private int curr = 0;
	           private int temp = 0;

	           public boolean hasNext()
	           {
	               return curr < size;
	           }
	           public E next()
	           {
	               temp = curr;
	               curr++;
	               return elements[curr - 1];
	           }
	           public void remove()
	           {              
	               if(temp != curr)
	                   curr--;
	               for(int i = curr; i < size - 1; i++)
	               {
	                   elements[i] = elements[i + 1];                  
	               }  
	               size--;
	           }
	       };
	   }


}
