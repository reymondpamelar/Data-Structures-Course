/**Prog2
*Reymond Pamelar
*cssc1253
*Kraft
*/
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E>{
	
	private Node<E> head;
    private int size=0;
    private int mCounter;
    
    //node class
    private class Node<E> { 
        E data; 
        Node<E> next; 
        
        Node(E d) {
            data = d; 
            next = null; 
        } 
    } 
    
   // Constructor
    public OrderedLinkedListPriorityQueue() {
    	head= new Node<E>(null);
    }
    
    // Inserts a new object into the priority queue. Returns true if
    // the insertion is successful. If the PQ is full, the insertion
    // is aborted, and the method returns false.
	@Override
	public boolean insert(E object) {
		Node<E> newNode=new Node<E>(object);
		Node<E> curr=head;
		if(head==null) {
			newNode.next=head;
			head=newNode;
			size++;
			mCounter++;
			return true;
		}
		if(head!=null) {
			while(curr.next!=null && ((Comparable<E>) curr.next.data).compareTo((E) newNode.data)<0) {
				curr=curr.next;
			}
			newNode.next=curr.next;
			curr.next=newNode;
			size++;
			mCounter++;
			return true;
		}
		return false;
	}

	
	 // Removes the object of highest priority that has been in the
	 // PQ the longest, and returns it. Returns null if the PQ is empty.
	@Override
	public E remove() {
		if(isEmpty()){
			return null;
		}
		Node<E> temp=head.next;
		if(head!=null){
			head=head.next;
			size--;
			mCounter++;
		}
		return temp.data;
	}

	
	 // Deletes all instances of the parameter obj from the PQ if found, and
	 // returns true. Returns false if no match to the parameter obj is found.
	@Override
	public boolean delete(E obj) {
		Node<E> temp=head;
		Node<E> prev=null;
		if(temp!=null && temp.data==obj){
			head=temp.next;
			return true;
		}
		while (temp!=null && temp.data!=obj) 
        { 
            prev=temp; 
            temp=temp.next; 
        }  
		if(temp==null) {
			return false;
		}
		else {
			prev.next=temp.next;
			size--;
			mCounter++;
			return true;
		}
	}

	
	 // Returns the object of highest priority that has been in the
	 // PQ the longest, but does NOT remove it.
	 // Returns null if the PQ is empty.
	@Override
	public E peek() {
		 if(isEmpty()) {
	           return null;
		 }
		 else {
	           return head.data;
		 }
	}

	
	 // Returns true if the priority queue contains the specified element
	 // false otherwise.
	@Override
	public boolean contains(E obj) {
		Node<E> curr=head;
		while(curr!=null) {
			if(obj==curr.data) {
				return true;
			}
			curr=curr.next;
		}
		return false;
	}

	
	 // Returns the number of objects currently in the PQ.
	@Override
	public int size() {
		return size;
	}

	
	 // Returns the PQ to an empty state.
	@Override
	public void clear() {
		head=null;
		size=0;
		mCounter=0;
	}

	
	 // Returns true if the PQ is empty, otherwise false
	@Override
	public boolean isEmpty() {
		return size==0;
	}

	
	 // Returns true if the PQ is full, otherwise false. List based
	 // implementations should always return false.
	@Override
	public boolean isFull() {
		return false;
	}

	
	 // Returns an iterator of the objects in the PQ, in no particular
	 // order.
	@Override
	public Iterator<E> iterator(){
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator<E>{
		Node<E> iter;
		Node<E> next;
		long stateCheck;
		  
		public IteratorHelper() {
			iter = head;
			stateCheck = mCounter;
		}
		
		public boolean hasNext() {
			if(stateCheck != mCounter)
				throw new ConcurrentModificationException();
			return iter != null;
		}
		      
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			next = iter;
			iter = iter.next;
			return next.data;
		}
		
		
	}

}
