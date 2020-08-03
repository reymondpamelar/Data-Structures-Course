 /**
       *  Program 4
       *  CS310
       *  Due 05/05/2020
       *  @author  Reymond Pamelar cssc1253
       */
package data_structures;

import java.util.Iterator;
import java.util.ConcurrentModificationException;

public class Hashtable<K, V> implements DictionaryADT<K, V>{

	private int currSize;
	private int maxSize;
	private int tableSize;
	private long modCounter;
	private LinkedListDS<DictionaryNode<K,V>> [] list;
	private LinkedListDS<DictionaryNode<V,K>> [] rlist;
	
	/*
	 * Credit to Riggins
	 */
	@SuppressWarnings("hiding")
	class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>> {
		K key;
		V value;
		
		public DictionaryNode(K k, V v) {
			key = k;
			value = v;
		}
		
		@SuppressWarnings("unchecked")
		public int compareTo(DictionaryNode<K,V> node) {
			return ((Comparable<K>)key).compareTo((K)node.key);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Hashtable(int size) {
		currSize=0;
		maxSize=size;
		tableSize=(int)(maxSize*1.3f);
		modCounter=0;
		list=new LinkedListDS[tableSize];
		for(int i=0; i<tableSize; i++) {
			list[i]=new LinkedListDS<DictionaryNode<K,V>>();
		}
		rlist=new LinkedListDS[tableSize];
		for(int i=0; i<tableSize; i++) {
			rlist[i]=new LinkedListDS<DictionaryNode<V,K>>();
		}
	}
	
	/*
	 * Credit to Riggins
	 * 
	 * Indexes a keys hash code to be compatible with the table
	 */
	private int getIndex(K key) {
		return (key.hashCode() & 0x7FFFFFFF) % tableSize;
	}

	@Override
	public boolean put(K key, V value) {
		
		//Search for duplicates
		if(list[getIndex(key)].contains(new DictionaryNode<K,V>(key,null))) {
			return false;
		}
		
		//add to end of list 
		list[getIndex(key)].addLast(new DictionaryNode<K,V>(key, value));
		
		//add to end of reverse list. -List to be used in getKey
		rlist[getIndex(key)].addLast(new DictionaryNode<V,K>(value, key));
		currSize++;
		modCounter++;
		return true;
	}

	
	@Override
	public boolean delete(K key) {
		
		//Empty tree
		if(isEmpty()) {
			return false;
		}
		
		//If remove method from LinkedList returns true
		if(list[getIndex(key)].remove(new DictionaryNode<K,V>(key,null))) {
			currSize--;
			modCounter--;
			return true;
		}
		return false;
	}

	@Override
	public V get(K key) {
		if(isEmpty()) {
			return null;
		}
		
		//At the list index, search for the key and return its value
		return list[getIndex(key)].search(new DictionaryNode<K,V>(key,null)).value; 
	}
	
	//Finds index of value for reverse list
	private int getIndexV(V value) {
		return (value.hashCode() & 0x7FFFFFFF) % tableSize;
	}

	@Override
	public K getKey(V value) {
		if(isEmpty()) {
			return null;
		}
		
		//Utilizes the reverse list to search for element with the value, returns key
		return rlist[getIndexV(value)].search(new DictionaryNode<V,K>(value, null)).value;
	}

	@Override
	public int size() {
		return currSize;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return currSize==0;
	}

	@Override
	public void clear() {
		currSize=0;
		modCounter=0;
		for(int i=0; i<tableSize; i++) {
			list[i]=new LinkedListDS<DictionaryNode<K,V>>();
		}
	}

	/*
	 * iterator credit to Riggins
	 */
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected DictionaryNode<K,V> [] nodes;
		protected int idx;
		protected long modCheck;
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public IteratorHelper() {
			nodes = new DictionaryNode[currSize];
			idx=0;
			int j=0;
			modCheck=modCounter;
			for(int i=0; i<tableSize; i++) {
				for(DictionaryNode n : list[i]) {
					nodes[j++]=n;
				}
			}
			
			/*
			 * Credit to geeksforgeeks.org
			 * 
			 * Sorts nodes
			 */
			DictionaryNode<K,V> tmp;
			for(int i=0; i<currSize; i++) {
				for(int x=1; x<(currSize-i); x++) {
					if(nodes[x-1].compareTo(nodes[x])>0) {
						tmp=nodes[x-1];
						nodes[x-1]=nodes[x];
						nodes[x]=tmp;
					}
				}
			}
		}
		
		public boolean hasNext() {
			if(modCheck != modCounter) {
				throw new ConcurrentModificationException();
			}
			return idx < currSize;
		}
		
		public abstract E next();
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
