 /**
       *  Program 4
       *  CS310
       *  Due 05/05/2020
       *  @author  Reymond Pamelar cssc1253
       */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BinarySearchTree <K, V> implements DictionaryADT<K, V>{

	private int size;
	private Node<K,V> root;
	private long modCounter;
	
	/*
	 * Credit to Riggins
	 */
	@SuppressWarnings("hiding")
	class Node<K,V>{
		public K key;
		public V value;
		@SuppressWarnings("rawtypes")
		public Node leftChild;
		@SuppressWarnings("rawtypes")
		public Node rightChild;
		
		public Node(K k, V v) {
			key = k;
			value = v;
			leftChild = rightChild = null;
		}
	}
	
	public BinarySearchTree() {
		size=0;
		modCounter=0;
		root=null;
	}
	
	/*
	 * Credit to Riggins
	 * Utilized for finding duplicates in put method
	 */
	private boolean contains(K key) {
		return contain(root, key);
	}
	@SuppressWarnings("unchecked")
	private boolean contain(Node<K,V> root, K k) {
		if(root==null) {
			return false;
		}
		if(((Comparable<K>)k).compareTo(root.key)==0) {
			return true;
		}
		if(((Comparable<K>)k).compareTo(root.key)>0) {
			return contain(root.rightChild, k);
		}
		return contain(root.leftChild, k);
	}
//////////////////////////////////////////////////////////////////////////	
	@SuppressWarnings("unchecked")
	private Node<K,V> putHelper(Node<K,V> root, K k, V v) {
		//When open spot is found
		if(root==null) {
			root=new Node<K,V>(k,v);
			insert=true;
			return root;
		}
		
		/*Comparisons to proceed left or right subtree
		 * left= less than
		 * right= higher than
		 */
		if(((Comparable<K>)k).compareTo(root.key)<0) {
			root.leftChild=putHelper(root.leftChild, k, v);
		}
		else if(((Comparable<K>)k).compareTo(root.key)>0) {
			root.rightChild=putHelper(root.rightChild, k, v);
		}
		return root;
	}
	
	private boolean insert;
	@Override
	public boolean put(K key, V value) {
		insert = false;
		
		//for finding duplicates
		if(contains(key)) {
			return false;
		}
		root=putHelper(root, key, value);
		size++;
		modCounter++;
		return insert;
	}
	
//////////////////////////////////////////////////////////////////////////
	/*
	 * Credit to geeksforgeeks.org
	 */
	@SuppressWarnings("unchecked")
	private Node<K,V> deleteHelper(Node<K,V> root, K k){
		
		//Empty tree
		if(root==null) {
			return root;
		}
		
		//traverse tree to find node
		if(((Comparable<K>)k).compareTo(root.key)<0) {
			root.leftChild = deleteHelper(root.leftChild, k);
		}
		else if(((Comparable<K>)k).compareTo(root.key)>0) {
			root.rightChild = deleteHelper(root.rightChild, k);
		}
		
		//when the key is found
		else {
			
			//Case with when root has one child
			if(root.leftChild==null) {
				return root.rightChild;
			}
			else if(root.rightChild==null) {
				return root.leftChild;
			}
			
			//Case when root has two children
			root.key=(K) minValue(root.rightChild);
			
			//Delete successor
			root.rightChild=deleteHelper(root.rightChild, root.key);
		}
		return root;
	}
	
	@SuppressWarnings("unchecked")
	private K minValue(Node<K,V> root) 
    { 
        K minv = root.key; 
        while (root.leftChild != null) 
        { 
            minv = (K) root.leftChild.key; 
            root = root.leftChild; 
        } 
        return minv; 
    } 
	
	@Override
	public boolean delete(K key) {
		
		//If element is not found
		if(!contains(key)) {
			return false;
		}
		
		root=deleteHelper(root, key);
		size--;
		modCounter--;
		return true;
	}

	
//////////////////////////////////////////////////////////////////////////

	/*
	 * Credit to Riggins
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		if(root==null) {
			return null;
		}
		Node<K,V> current = root;
		
		//traverse list
		while (((Comparable<K>)current.key).compareTo((K)key) != 0) {
			
			//Key is less than root, go to the right child
			if (((Comparable<K>)key).compareTo((K)current.key) < 0) {
				current=current.leftChild;
			}
			
			//higher than root
			else {
				current=current.rightChild;
			}
			if(current==null) {
				return null;
			}
		}
		return current.value;
	}
	
//////////////////////////////////////////////////////////////////////////

	/*
	 * Traverse each individual element to find key
	 */
	private K k=null;
	@SuppressWarnings("unchecked")
	private K getKeyHelper(Node<K,V> root, V v) {
		
		//Empty tree
		if(root==null) {
			return null;
		}
		
		//when value matches value, return key of the element
		if(((Comparable<V>)root.value).compareTo(v) == 0) {
			k=root.key;
		}
		//traverse left subtree
		getKeyHelper(root.leftChild,v);
		
		//traverse right subtree
		getKeyHelper(root.rightChild,v);
		return k;
		
	}
	
	
	@Override
	public K getKey(V value) {
		return getKeyHelper(root, value);
	}

//////////////////////////////////////////////////////////////////////////
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return root==null;
	}

	@Override
	public void clear() {
		size=0;
		modCounter=0;
		root=null;
	}
	
//////////////////////////////////////////////////////////////////////////	
	
	/*
	 * Iterator credit to Riggins
	 */
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected Node<K,V> [] nodes;
		protected int idx;
		private int iterIndex;
		protected long modCheck;
		
		@SuppressWarnings("unchecked")
		private void inorderFillArray(Node<K,V> n) {
			if(n==null) {
				return;
			}
			inorderFillArray(n.leftChild);
			nodes[iterIndex++] = n;
			inorderFillArray(n.rightChild);
		}
		
		@SuppressWarnings("unchecked")
		public IteratorHelper() {
			nodes = new Node[size];
			idx=0;
			modCheck=modCounter;
			inorderFillArray(root);
		}
		public boolean hasNext() {
			if(modCheck != modCounter) {
				throw new ConcurrentModificationException();
			}
			return idx < size;
		}
		
		public abstract E next();
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Iterator<K> keys() {
		return new KeyIteratorHelper();
	}
	
	@SuppressWarnings("hiding")
	class KeyIteratorHelper<K> extends IteratorHelper<K>{
		public KeyIteratorHelper() {
			super();
		}
		
		@SuppressWarnings("unchecked")
		public K next() {
			return (K) nodes[idx++].key;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Iterator<V> values() {
		return new ValueIteratorHelper();
	}

	@SuppressWarnings("hiding")
	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		public ValueIteratorHelper() {
			super();
		}
		
		@SuppressWarnings("unchecked")
		public V next() {
			return (V) nodes[idx++].value;
		}
	}
	
}
