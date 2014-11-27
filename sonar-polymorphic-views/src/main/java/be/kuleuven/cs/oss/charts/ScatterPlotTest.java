package be.kuleuven.cs.oss.charts;



import java.util.ArrayList;
import java.util.Collections;



public class ScatterPlotTest {

	public static void main(String[] args){
		test();
		
	}
	public static void test() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for(int i=10; i>=0; i--){
			a.add(i);
		}
		Collections.sort(a);
		System.out.println(""+a);
	}

}
