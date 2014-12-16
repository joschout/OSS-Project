package be.kuleuven.cs.oss.resourcevisualizations;

import java.util.TreeMap;

public class TreeMapTest {
	public static void main(String[] args) {
	      // creating tree map 
	      TreeMap<Integer, String> treemap = new TreeMap<Integer, String>();
	      
	      // populating tree map
	      treemap.put(2, "two");
	      treemap.put(1, "one");
	      treemap.put(3, "three");
	      treemap.put(6, "six");
	      treemap.put(5, "five");
	      
	      // getting higher key for key 4          
	      System.out.println("Checking values of the map");
	      for(int i : treemap.keySet()){
	    	  System.out.println(""+i);
	      }
	      System.out.println("Value is: "+ treemap.higherKey(4));
	   }   
}
