import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;

/** 
 * The following class tests the class ArrayMap.
 * 
 * The class ArrayMap and its inner classes ArrayMapEntrySet
 * and ArrayMapEntrySetIterator methods are tested in this 
 * suite.
 * 
 * @author Fernando Ruiz
 * 
 * @see ArrayMap
 * @see ArrayMapEntrySet
 * @see ArrayMapEntrySetIterator
 */
public class ArrayMapTests {
	
    /**
     * Tests ArrayMap Methods.
     */
	@Test
	void testArrayMap() {
		ArrayMap<String, String> map = new ArrayMap<>();
		
		//test put
		map.put("Test0", "0"); 
		//alter existing key
		String oldVal = map.put("Test0", "zero");
		//check that it returns previous value
		assertEquals(oldVal, "0");
		//test bounds to grow array
		map.put("Test1", "one");
		map.put("Test2", "two");
		map.put("Test3", "three");
		map.put("Test4", "four");
		map.put("Test5", "five");
		map.put("Test6", "six");
		map.put("Test7", "seven");
		map.put("Test8", "eight");
		map.put("Test9", "nine");
		map.put("Test10", "ten");
		
		//test map size
		int sizemap = map.size();
		assertEquals(sizemap, 11);
		
		//test invariant methods work with implemented methods
		//test get
		String str = map.get("Test0");
		assertEquals(str,"zero");
		//test get on non-existing
		assertNull(map.get("null"));
		//test contains
		assertTrue(map.containsKey("Test0"));
		assertTrue(map.containsKey("Test10"));
		//test non-existing
		assertFalse(map.containsKey("N/A"));
	}
	
    /**
     * Tests the ArrrayMapEntrySet and Iterator methods.
     */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	void testSetAndIter() {
		ArrayMap<String, Integer> map = new ArrayMap<>();
		map.put("Test1", 1);
		map.put("Test2", 2);
		map.put("Test3", 3);
		
		//test entrySet()
		Set<Entry<String, Integer>> set = map.entrySet();
		
		//test set entrySet size()
		assertEquals(set.size(), 3);
		
		Entry<String, Integer> simpleEntry = new SimpleEntry<String, Integer>("Test3", 3);
		Entry<String, Integer> wrongEntry = new SimpleEntry<String, Integer>("Test4", 4);
		
		//test entrySet contains
		assertTrue(set.contains(simpleEntry));
		//entry not in set
		assertFalse(set.contains(wrongEntry));
		//not an instance of entry
		assertFalse(set.contains(1000));
		
		//test iterator on entrySet
		Iterator<Entry<String, Integer>> iter = set.iterator();
		int i = 1;
		//test hasNext on entrySet
		while(iter.hasNext()) {
			//test Next on entrySet
			Entry<String, Integer> entry = iter.next();
			//test correct pair in set
			assertEquals("Test"+i, entry.getKey());
			assertEquals(i, entry.getValue());
			//test remove
			iter.remove();
			i++;
		}
		
		//test that entries were removed.
		Set<Entry<String, Integer>> set2 = map.entrySet();
		Iterator<Entry<String, Integer>> iter2 = set2.iterator();
		assertFalse(iter2.hasNext());
		
		//test that remove throws IllegalStateException
		assertThrows(IllegalStateException.class, ()->{ iter.remove();});
	}
}