package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ScaledResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class Parser {

	public List<ResourceProperty> parseBoxcolor(Parameter enumClass, String input) throws IllegalArgumentException{
		try{
			List<ResourceProperty> result;
			if(input.matches("r[0-9]{1,3}g[0-9]{1,3}b[0-9]{1,3}")){
				List<String> split = Arrays.asList(input.split("[rgb]"));
				List<Integer> ints = parseStringsToInts(split.subList(1, split.size()));
				result = enumClass.BOXCOLOR.getProperties(ints.get(0),ints.get(1),ints.get(2));
			}
			else if(input.matches("min[0-9]+(\\.[0-9]+)*max[0-9]+(\\.[0-9]+)*key(.)+")){
				List<String> split = Arrays.asList(input.split("[(min)(max)(key)]"));
				List<Float> floats = parseStringsToFloats(split.subList(1, split.size()-1));
				String key = split.get(split.size()-1);
				result = enumClass.BOXCOLOR.getProperties(floats.get(0),floats.get(1),key);
			}
			else{
				throw new IllegalArgumentException("Color not recognized!");
			}
		}
		catch(Exception e){
			throw new IllegalArgumentException("Failed to parse boxcolor ("+e+")");
		}
	}
	
	public ResourceProperty parseMetric(Parameter enumClass, String input){
		return enumClass.getproperty(input);
	}
	
	public Resource parseParent(Parameter enumClass,String input){
		return enumClass.getResource(input);
	}
	
	private static List<Integer> parseStringsToInts(List<String> s){
		List<Integer> result = new ArrayList<Integer>();
		Iterator<String> it = s.iterator();
		while (it.hasNext()){
			result.add(Integer.parseInt(it.next()));
		}
		return result;
	}
	
	private List<Float> parseStringsToFloats(List<String> s){
		List<Float> result = new ArrayList<Float>();
		Iterator<String> it = s.iterator();
		while (it.hasNext()){
			result.add(Float.parseFloat(it.next()));
		}
		return result;
	}

}
