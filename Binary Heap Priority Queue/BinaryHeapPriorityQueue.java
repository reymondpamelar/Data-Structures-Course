 /**
       *  Program 3
       *  A priority queue accomplished through a Binary Heap
       *  CS310
       *  Due 04/09/2020
       *  @author  Reymond Pamelar cssc1253
       */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryHeapPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
		
	private Wrapper<E>[] pq;
	private int size=0;
	public int maxSize;
	private long entryNum=0;
	
    /*
     * Credit to Riggins
     * 
     * Allows the BinaryHeap to be stable by comparing
     * the entry numbers of equal data values
     */
	@SuppressWarnings("hiding")
	private class Wrapper<E> implements Comparable<Wrapper<E>> {
		E data;
		long sequenceNum;
		
		public Wrapper(E obj) {
			sequenceNum=entryNum;
			entryNum++;
			data=obj;
		}
		
		@Override
		public int compareTo(Wrapper<E> o) {
			@SuppressWarnings("unchecked")
			int temp = ((Comparable<E>)data).compareTo(o.data);
			if(temp==0) {
				return (int)(sequenceNum - o.sequenceNum);
			}
			return temp;
		}
		
	}
	
	
	public BinaryHeapPriorityQueue() {
		this(DEFAULT_MAX_CAPACITY);
	}
	
	@SuppressWarnings("unchecked")
	public BinaryHeapPriorityQueue(int size) {
		pq = new Wrapper[size];
		maxSize=size;
		entryNum=0;	
	}
	
	/*
	 * swaps two objects
	 */
	private void swap(int x, int y) {
		Wrapper<E> temp = pq[x];
		pq[x]=pq[y];
		pq[y]=temp;
	}
	
	/*
	 * Credit to Rob Edwards
	 * 
	 * If the object inserted is less than the parent,
	 * swap the values with parents until the original 
	 * value reaches a parent that states otherwise
	 */
	@SuppressWarnings("unchecked")
	private void trickleUp(int pos) {
		if(pos==0) {
			return;
		}
		int parent=(int) Math.floor((pos-1)/2);
		if(((Comparable<E>)pq[pos]).compareTo((E) pq[parent])<0) {
			swap(pos,parent);
			trickleUp(parent);
		}
	}
	
	/*
	 * Credit to Rob EdWards
	 * 
	 * Compares the parent to its children and swaps with
	 * either child depending on whether it maintains
	 * the heap properties or not
	 */
	@SuppressWarnings("unchecked")
	private void trickleDown(int parent) {
		int left = 2*parent+1;
		int right = 2*parent+2;
		//If left child is not the last & parent>left then swap
		if(left<size-1 && (((Comparable<E>)pq[parent]).compareTo((E) pq[left])>0)){
			swap(parent,left);
			return;
		}
		//If right child is not the last & parent>right then swap
		if(right<size-1 && (((Comparable<E>)pq[parent]).compareTo((E) pq[right])>0)) {
			swap(parent,right);
			return;
		}
		//If the children are greater than the last position
		if(left>=size-1 || right>=size-1) {
			return;
		}
		//If left>right and parent>left swap parent with left
		if((((Comparable<E>)pq[left]).compareTo((E) pq[right])>0) && (((Comparable<E>)pq[parent]).compareTo((E) pq[left])>0)) {
			swap(parent,left);
			trickleDown(left);
		}
		//If parent>right then swap
		else if((((Comparable<E>)pq[parent]).compareTo((E) pq[right])>0)) {
			swap(parent,right);
			trickleDown(right);
		}
	}
	
	/*
	 * New object gets added to the last position and trickles
	 * up to maintain min-heap properties
	 */
	@Override
	public boolean insert(E object) {
		if(isFull()) {
			return false;
		}
		Wrapper<E> newElem = new Wrapper<E>(object);
		pq[size] = newElem;
		size++;
		trickleUp(size-1);
		return true;
	}

	/*
	 * Removes the root and returns it
	 * 
	 * Replaces the root with last object and trickleDown the
	 * object to maintain min-heap properties
	 */
	@Override
	public E remove() {
		if(isEmpty()) {
			return null;
		}
		Wrapper<E> rm= pq[0];
		--size;
		pq[0]= pq[size];
		trickleDown(0);
		return rm.data;
	}

	/*
	 * Deletes the specified object
	 * 
	 * If the object is the root then use remove()
	 * If not, iterate until found. Replace object with
	 * last position. Trickle up/down depending on value
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(E obj) {
		if(isEmpty()) {
			return false;
		}
		if(obj==pq[0].data) {
			remove();
			return true;
		}
		for(int i=1; i<size-1; i++) {
			if(obj==pq[i].data) {
				--size;
				pq[i]=pq[size];
				if(i==1 || (((Comparable<E>)pq[i/2]).compareTo((E) pq[i]) <0)) {
					trickleDown(i);
				}
				else {
					trickleUp(i);
				}
				
				return true;
			}
		}
		return false;
	}

	/*
	 * Reveals the root without deleting
	 */
	@Override
	public E peek() {
		return (E) pq[0].data;
	}

	/*
	 * Iterate over min-heap array to search
	 * for requested obj
	 */
	@Override
	public boolean contains(E obj) {
		if(isEmpty()) {
			return false;
		}
		for(int i=0; i<size-1; i++) {
			if(obj==pq[i].data) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		size=0;
		
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public boolean isFull() {
		return size==maxSize;
	}


	@Override
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	/*
	 * Credit to Riggins
	 */
	public class IteratorHelper implements Iterator<E> {
		int enumIndex;
	    long modCounter;
	    long modCount;
    	public IteratorHelper() {
    		enumIndex = 0;
    		modCount = modCounter;
    	}
    	
    	public boolean hasNext() {
    		if(modCount!=modCounter) {
    			throw new ConcurrentModificationException();    		}
    		return enumIndex < size;
    	}

    	public E next() {
    		if(!hasNext()) { 
    			throw new NoSuchElementException();
    		}
    		return (E) pq[enumIndex++].data;
    	}

		public void remove() {
			throw new UnsupportedOperationException();
		}
    }
	
}
