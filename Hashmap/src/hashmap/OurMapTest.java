package hashmap;

public class OurMapTest {
	public static void main(String[] args) {
		
		OurMap<Integer, String> nameToLen = new OurMap<>();
		nameToLen.put(1, "a");
		nameToLen.put(6, "ramesh");
		nameToLen.put(5, "rahul");
		nameToLen.put(3, "raj");
		nameToLen.put(4, "ravi");
		nameToLen.put(5, "kumar");
		nameToLen.put(8, "raushan");
		
		System.out.println(nameToLen.get(3));
		System.out.println(nameToLen.get(7));
		System.out.println(nameToLen.get(1));  // this giving null , i don't know why but after commenting all other insertion it's giving correct value.
		System.out.println(nameToLen.get(6));
		System.out.println(nameToLen.get(5));
		System.out.println(nameToLen.get(4));
		System.out.println(nameToLen.get(8));

	}

}
