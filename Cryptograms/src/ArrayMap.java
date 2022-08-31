import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


/**
 * The following class represents an ArrayMap, which is used to replace a hashMap.
 * 
 * The following class represents a generic ArrayMap that consists of two object 
 * arrays one for keys and the other for values. The indexes in the array represent
 * a key and value pair. The class has the following methods: put, get, containsKey
 * size and entrySet, which all are overridden from AbstractMap. There are two inner
 * classes ArrayMapEntrySet and ArrayMapEntrySetItertor.
 * @author Fernando Ruiz
 *
 *
 * @param <K> generic type for keys
 * @param <V> generic type for values
 */
public class ArrayMap<K, V> extends AbstractMap<K,V> {
	
	private static int DEFAULT_SIZE = 10;
	private Object[] keys;
	private Object[] values;
	private int size;
	private int pairs;
	
	/**
	 * Constructor for ArrayMap
	 * 
	 * The constructor assigns an array to keys and
	 * values with a default size. Size is also set
	 * to default and pairs, which represents the 
	 * number of mappings, is set to zero. 
	 */
	public ArrayMap() {
		this.keys   = new Object[DEFAULT_SIZE];
		this.values = new Object[DEFAULT_SIZE];
		this.size   = DEFAULT_SIZE;
		this.pairs  = 0;
	}
	
	
	/**
	 * The following function adds the passed in key and value to ArrayMap.
	 * 
	 * The following function overrides put to add a pair to the ArrayMap. A key
	 * value pair is set to the same index in their corresponding arrays. An 
	 * existing key has its value replaced with the passed in value. The function
	 * also checks to see if the arrays for keys and values need to be adjusted
	 * based on the current number of pairs. 
	 * 
	 * @param key represents a key of type K
	 * @param value represents a value of type V
	 * 
	 * @return returns existing key's previous value if N/A returns null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		//check size of array
		if (pairs == size) {
			this.size = size * 2;
			this.keys = Arrays.copyOf(keys, size);
			this.values = Arrays.copyOf(values, size);
		}
		
		//check if key exists, return old value
		for (int i=0; i<keys.length; i++) {
			if (key.equals(keys[i])) {
				V oldVal = (V) values[i];
				values[i] = value;
				return oldVal;
			}
		}
		
		//key doesn't exist add new pair
		keys[pairs] = key;
		values[pairs] = value;
		this.pairs++;
		return null;
	}
	
	/**
	 * The following function returns the size of the ArrayMap. 
	 * 
	 * The following function overrides the function size and returns
	 * the field pairs which represents the number of mappings in the 
	 * ArrayMap. 
	 * 
	 * @return returns an int that represents the size(pairs) of theArrayMap
	 */
	@Override
	public int size() {
		return this.pairs;	
	}
	
	/**
	 * The following function returns a set of entry pairs.
	 * 
	 * The following function returns an ArrayMapEntrySet, which
	 * has an SimpleEntry of each key and value pair in the ArrayMap.
	 * Can be iterated over using the inner class functions in 
	 * ArrayMapEntrySetIterator.
	 * 
	 * @return returns an ArrayMapEntrySet of key value pairs in the ArrayMap.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entrySet = new ArrayMapEntrySet();
		for (int i = 0; i < this.pairs; i++) {
			Entry<K, V> entry = new SimpleEntry<K, V>((K) keys[i], (V) values[i]);
			entrySet.add(entry);
		}
		
		return entrySet;
	}
	
	/**
	 * The following function removes the passed in entry from ArrayMap. 
	 * 
	 * The following function uses the passed in entry's key to find the index of the
	 * the key value pair and removes them from their array fields. The field pairs
	 * is decremented since a pair has been removed. This function is to be used by
	 * the function remove in the private inner class Iterator.
	 * 
	 * @param entry is a key value pair in the object Entry<K, V>.
	 */
	public void remove(Entry<K, V> entry) {
		for (int i = 0; i < this.pairs; i++) {
			if (entry.getKey().equals(keys[i])) {
				ArrayList<Object> keyList = new ArrayList<>(Arrays.asList(this.keys));
				ArrayList<Object> valueList = new ArrayList<>(Arrays.asList(this.values));
				keyList.remove(i);
				valueList.remove(i);
				this.keys = keyList.toArray(new Object[0]);
				this.values = valueList.toArray(new Object[0]);
				this.pairs--;
			}
		}
	}

	
	/**
	 * The following class represents an entry set for the ArrayMap.
	 * 
	 * The following class represents an iteratorable set of entries 
	 * that represent key value pairs in the ArrayMap. The class 
	 * extends AbstractSet and overrides the methods size, contains,
	 * iterator, and add.
	 * 
	 * @author Fernando Ruiz
	 *
	 */
	private class ArrayMapEntrySet extends AbstractSet<Entry<K,V>> {
		
		private ArrayList<Entry<K, V>> entrySet;
		
		/**
		 * Constructor for ArrayMapEntrySet.
		 * 
		 * The constructor assigns the field entrySet to
		 * an ArrrayList of type Entry<K, V> to hold entries
		 * representing pairs in the ArrayMap.
		 */
		public ArrayMapEntrySet() {
			this.entrySet = new ArrayList<Entry<K, V>>();
		}
		
		/**
		 * The following function returns the size of the entrySet. 
		 * 
		 * The following function overrides the function size and returns
		 * the ArrayMap's field pairs which represents the number of mappings.
		 * entrySet is a set of entry pairs based on the ArrayMap pairs
		 * so represents the same size.  
		 * 
		 * @return returns int that represents the size of the entrySet
		 */
		@Override
		public int size() {
			return pairs;
		}
		
		/**
		 * The following function returns a boolean based on whether or not
		 * the entrySet contains the passed in entry.
		 * 
		 * The following function overrides the function contains. It returns
		 * true if the passed in object is an entry in the entrySet. If the object
		 * is not an entry or is not in the entrySet false is returned.
		 * 
		 * @param o is an Object that may exist in the entry set.
		 * 
		 * @return returns a boolean based on if the object is in the entrySet
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean contains(Object o) {
			if (o instanceof Entry) {
				for (Entry<K, V> entry: entrySet) {
					Entry<K, V> newEntry = (Entry<K, V>) o ;
					boolean keyEquality = newEntry.getKey().equals(entry.getKey());
					boolean valEquality = newEntry.getValue().equals(entry.getValue());
					if (keyEquality && valEquality) {
						return true;
					}
				}
			}
			return false;
		}
		
		/**
		 * The following function returns an iterator that walks
		 * over the entrySet. 
		 * 
		 * The following function returns an iterator that walks
		 * over the entrySet which represents the Entries in the Map. 
		 * The iterator is implemented in the private inner class
		 * ArrayMapEntrySetIterator.
		 * 
		 * @return returns an ArrayMapEntrySetIterator to iterate over entrySet
		 */
		@Override
		public Iterator<Entry<K, V>> iterator() {
			Iterator<Entry<K, V>> iterator = 
					new ArrayMapEntrySetIterator<Entry<K, V>>(entrySet);
			return iterator;
		}
		
		/**
		 * The following function adds the passed in entry to the entry set.
		 * 
		 * The following function overrides the function add and adds the passed
		 * in entry to the entry set using the ArrayList function add. Always
		 * returns true, since all entries are only added once in the ArrayMap.
		 * 
		 * @param entry is an entry of type Entry<K, V> representing a key value pair.
		 * 
		 * @return returns true always successful addition to entry set.
		 */
		@Override
		public boolean add(Entry<K, V> entry) {
			entrySet.add(entry);
			return true;
		}
		
	}
	
	/**
	 * The following class represents an iterator for ArrayMapEntrySet.
	 * 
	 * The following class is used to iterate over the ArrayMapEntrySet.
	 * It implements the interface Iterator and has the methods hasNext,
	 * next, and remove. 
	 * 
	 * @author Fernando Ruiz
	 *
	 * @param <T> is the type of element in the ArrayMapEntrySet.
	 */
	private class ArrayMapEntrySetIterator<T> implements Iterator<T> {
		
		private ArrayList<T> entrySet;
		private int index;
		private boolean nextCall;
		private boolean removeCall;
		
		/**
		 * Constructor for ArrayMapEntrySetIterator.
		 * 
		 * The constructor assigns the entrySet filed to the
		 * passed in entrySet. Index of the set is set to zero.
		 * The fields nextCall and removeCall are set to false,
		 * they determine whether a entry can be removed by the func.
		 * remove.
		 * 
		 * @param entrySet is a Collection<T> to be iterated over.
		 */
		public ArrayMapEntrySetIterator(Collection<T> entrySet) {
			this.entrySet = (ArrayList<T>) entrySet;
			this.index = 0;
			this.nextCall = false;
			this.removeCall = false;
		} 
		
		/**
		 * The following function returns a boolean based on whether there
		 * is a next element in the entry set. 
		 * 
		 * The following function overrides the function hasNext and
		 * returns a boolean based on whether there is a next element 
		 * in the entry set. Uses size of entry set to determine if the
		 * index can be incremented. 
		 * 
		 * @return returns a boolean based on whether there is a next element in the entry set.
		 */
		@Override
		public boolean hasNext() {
			if(index < entrySet.size()) {
				return true;
			}
			return false;
		}
		
		
		/**
		 * The following function returns the next entry in the entry set.
		 * 
		 * The following function overrides the function next and
		 * returns the next element in the entry set. Sets the field
		 * nextCall to true to represent that the next has been called
		 * for the object at least once and sets the removeCall to false
		 * to reset the current elements removal status.Index is
		 * incremented to get next element on the next call.
		 * 
		 * @return returns the next entry in the entry set of type T.
		 */
		@Override
		public T next() {
			//function has been called for object instance
			this.nextCall = true;
			//resets removal status of current entry
			this.removeCall = false;
			
			T entry = entrySet.get(index);
			this.index++;
			return entry;
		}
		
		/**
		 * The following function removes the last element returned by this iterator from
		 * ArrayMap. 
		 * 
		 * Removes from the underlying collection the last element returned by this iterator 
		 * (optional operation). This method can be called only once per call to next().
		 * The behavior of an iterator is unspecified if the underlying collection is 
		 * modified while the iteration is in progress in any way other than by 
		 * calling this method. (this java doc is from the spec)
		 * 
		 * @throws IllegalStateException if the next method has not yet been called, or the remove 
		 * method has already been called after the last call to the next method.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void remove() {
			if(this.nextCall == false || this.removeCall == true) {
				throw new IllegalStateException();
			}else {
				Entry<K, V> entry = (Entry<K, V>) entrySet.get(index-1);
				ArrayMap.this.remove(entry);
				//this.entrySet.remove(index-1);
				this.removeCall = true;
			}
		}
	}
}