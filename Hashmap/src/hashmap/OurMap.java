package hashmap;

import java.util.ArrayList;
import java.util.List;

public class OurMap<k, v> {
	
	private List<MapNode<k, v>> bucket;  // List of LinkedList
	private int capacity; // length of the bucket
	private int size;     // number of elements in the map
	private final int INTIAL_CAPACITY = 5;  // initial length of the bucket array
	
	
	public OurMap() {
		bucket = new ArrayList<>();
		capacity = INTIAL_CAPACITY;
		for(int i = 0; i < capacity; i++)
			bucket.add(null);  // to make sure that 'capacity' no of indexes exist.
		                      // otherwise if it will give 'IndexOutOfBoundException' because bucket_size will be equal to 0.
		  // this can be solved using 'array' as in array we intialise with size initially.
		 // But using list because here we are dealing with generic size.
	}
	
	
	// 1) getting the hashcode
	// 2) using the compression function to make it within the range of bucket size
	public int getBucketIndex(k key) {
		int hashCode = key.hashCode();
		return hashCode % capacity;  // to bring within range of bucket size
	}
	
	public v get(k key) {
		// for getting the value, 1st we have to find index where key exist in bucket list.
		System.out.println("get is called for key "+key);
		// 1)get the index where key exist
		int bucketIndex = getBucketIndex(key);
		// 2) Get the head of the linklist at this index from the bucket
		MapNode<k, v> head = bucket.get(bucketIndex); // this 'get' for getting element at index 'bucketIndex'
		// 3) Now find in this link list 'key' exist or not.
		while(head != null) {
			// can't compare custom object with '==' so used 'equals'.
			// '==' compares the address space not the actual value.
			if(head.key.equals(key))
				System.out.println("get is called for key and value found "+key+" "+head.value);
				return head.value;
		}
		System.out.println("get is called for key and value not found "+key);
		return null ;
		
		
	}
	
	public void put(k key, v value) {
     // 1) First find the index where this key will go
		int bucketIndex = getBucketIndex(key);
		// 2) Get the head of the linklist at this index
		MapNode<k, v> head = bucket.get(bucketIndex);
		// 3) a)If key already exist then update the value else
		// b) Add this key at the first in this linklist
		while(head != null) {
			if(head.key.equals(key))
			{
				head.value = value ;
//				System.out.println(key+" "+value+" got updated");
				return ;
			}
			head = head.next;
		}
		// key doesn't exist
		size ++ ;  // Increase total no of elements
		// get new node
		MapNode<k, v> newEntry = new MapNode<k, v>(key, value);
		head = bucket.get(bucketIndex);
		newEntry.next = head;
		bucket.set(bucketIndex, newEntry);  // at this index making newEntry as head
		System.out.println("inserted "+key);
		
		// check if re-hashing is required
		double loadFactor = (1.0 *size) / capacity;
		System.out.println("Load Factor: "+loadFactor);
		if(loadFactor > 0.7)
			rehash();
		
	}
	
	public void rehash() {
		System.out.println("Re-hashing buckets");
		List<MapNode<k, v>> temp = bucket;  // copying old bucket into temp
		bucket = new ArrayList<>();
		capacity *= 2 ;
		for(int i = 0; i < capacity; i ++)
			bucket.add(null);
		size = 0;
		
		// Now re-hash all elements of old bucket into new bucket
		for(int i = 0; i < temp.size(); i ++) {
			MapNode<k, v> head = temp.get(i);
			while(head != null) {
				put(head.key, head.value);
				head = head.next;
			}
		}
	}
	
	public void remove(k key) {
		
		// 1) First find the index where this key will go
		int bucketIndex = getBucketIndex(key);
		// 2) Get the head of the linklist at this index
		MapNode<k, v> head = bucket.get(bucketIndex);
		// 3) Now Question reduces to removing a particular element from linklist based on key
		MapNode<k, v> pre = null ;
		while(head != null) {
			if(head.key.equals(key))
			{
				if(pre == null)
					// means we are removing 1st element
					bucket.set(bucketIndex, head.next);
				else
					pre.next = head.next ;
				head.next = null ;
				size --;
				break ;
			}
			pre = head ;
			head = head.next;
		}
		
		
	}
	
	// just node of a linked list.
	// this 'MapNode' class has no existence outside this class. 
	// Also we don't want to expose outside class because this is
	// internal implementation of hashmap
	private class MapNode<k, v> {
		k key;
		v value;
		MapNode<k, v> next;
		
		public MapNode(k key, v value) {
			this.key = key;
			this.value = value;
		}
	
		@Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            MapNode<?, ?> mapNode = (MapNode<?, ?>) obj;
            return key.equals(mapNode.key) && value.equals(mapNode.value);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (key == null ? 0 : key.hashCode());
            result = prime * result + (value == null ? 0 : value.hashCode());
            return result;
        }
	}

}
