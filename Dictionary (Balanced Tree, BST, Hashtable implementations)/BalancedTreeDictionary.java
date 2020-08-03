 /**
       *  Program 4
       *  CS310
       *  Due 05/05/2020
       *  @author  Reymond Pamelar cssc1253
       */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.TreeMap;


public class BalancedTreeDictionary<K,V> implements DictionaryADT<K,V>{

	private int size;
	private long modCounter;
	private TreeMap<K,V> map;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BalancedTreeDictionary() {
		size=0;
		modCounter=0;
		map=new TreeMap();
	}
	
	@Override
	public boolean put(K key, V value) {
		
		//search for duplicates
		if(map.containsKey(key)) {
			return false;
		}
		map.put(key, value);
		size++;
		modCounter++;
		return true;
	}

	@Override
	public boolean delete(K key) {
		V v=map.remove(key);
		
		//key not found
		if(v==null) {
			return false;
		}
		size--;
		modCounter--;
		return true;
	}

	@Override
	public V get(K key) {
		return map.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public K getKey(V value) {
		
		//Traverse an array of keys
		for(K key: map.keySet()) {
			
			//elements of keys and their values are compared to the value wanted
			if(((Comparable<V>)map.get(key)).compareTo(value)==0)
				return key;
		}
		return null;
	}

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
		return size==0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void clear() {
		modCounter=0;
		size=0;
		map=new TreeMap();
	}
	
	/*
	 * Iterator credit to Riggins
	 */
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected Object [] keys;
		protected Object [] values;
		protected int idx;
		protected long modCheck;
		
		public IteratorHelper() {
			idx=0;
			modCheck=modCounter;
			
			//set of keys to array
			keys=map.keySet().toArray(new Object[size]);
			
			//set of values to array
			values=map.values().toArray(new Object[size]);
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
			return (K) keys[idx++];
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
			return (V) values[idx++];
		}
	}
	
}

