/**Prog2
*Reymond Pamelar
*cssc1253
*Kraft
*/
package data_structures;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {

	private Node<E> head;
    private int size=0;
    private int mCounter=0;
    
    
    //Node class
    private static class Node<E> { 
        E data; 
        Node<E> next; 
        
        Node(E d) {
            data = d; 
            next = null; 
        } 
    } 
    
    //Constructor
    public UnorderedLinkedListPriorityQueue() {

    }
    
    // Inserts a new object into the priority queue. Returns true if
    // the insertion is successful. If the PQ is full, the insertion
    // is aborted, and the method returns false.
	@Override
	public boolean insert(E object) {
		Node<E> newNode=new Node<E>(object);
		Node<E> curr=head;
		if(head==null) {
			head=newNode;
			size++;
			mCounter++;
		}
		else {
			newNode.next=curr.next;
			curr.next=newNode;
			size++;
			mCounter++;
		}
		return true;
	}

	
	 // Removes the object of highest priority that has been in the
	 // PQ the longest, and returns it. Returns null if the PQ is empty.
	@Override
	public E remove() {
		Node<E> temp = head;
		Node<E> rm = head; 
		Node<E> prev = null;

		while(temp != null) {
			if(temp.next != null && ((Comparable<E>) temp.next.data).compareTo((E) rm.data)<0){
				rm = temp.next;
				prev = temp;
			}
			temp = temp.next;
		}
		
		if(rm != head) { 
			prev.next = rm.next;
		} else {
			head = head.next; 
		}
		size--;
		mCounter++;
		return rm.data;
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
            prev = temp; 
            temp = temp.next; 
        }  
		if(temp==null) {
			return false;
		}
		else {
			prev.next = temp.next;
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
		Node<E> temp=head;
		E small=head.data;
		if(isEmpty()) {
			return null;
		}
		while(temp!=null) {
			if(temp.data.compareTo(small)<=0)
				small=temp.data;
			temp=temp.next;
		}
		return small;
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
