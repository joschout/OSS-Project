package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;

public class Parser {

	public List<ResourceProperty> parse(Parameters.Boxcolor enumClass, String input){
		try{
			List<ResourceProperty> result;
			if(input.matches("r[0-9]{1,3}g[0-9]{1,3}b[0-9]{1,3}")){
				List<String> split = Arrays.asList(input.split("[rgb]"));
				List<Integer> ints = parseStringsToInts(split.subList(1, split.size()));
				result = enumClass.getProperties(ints.get(0),ints.get(1),ints.get(2));
			}
			else if(input.matches("min[0-9]+(\\.[0-9]+)*max[0-9]+(\\.[0-9]+)*key(.)+")){
				
			}
			//TODO set default color here
			else{
				result = null;
			}
		}
		catch(Exception e){
			throw new IllegalArgumentException("Failed to parse boxcolor: "+e);
		}
	}
	
	private static List<Integer> parseStringsToInts(List<String> s) throws Exception{
		List<Integer> result = new ArrayList<Integer>();
		Iterator<String> it = s.iterator();
		while (it.hasNext()){
			result.add(Integer.parseInt(it.next()));
		}
		return result;
	}
	
	private List<Float> parseStringsToFloats(List<String> s) throws Exception{
		List<Float> result = new ArrayList<Float>();
		Iterator<String> it = s.iterator();
		while (it.hasNext()){
			result.add(Float.parseFloat(it.next()));
		}
		return result;
	}
}
