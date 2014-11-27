package be.kuleuven.cs.oss.charts;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.trees.TreeNodeRV;

public class SystemComplexityScaling {
	
	private TreeNodeRV treeNodeRV;
	private List<ResourceVisualization> rvs;
	private static int SCALING_FACTOR;
	
	public SystemComplexityScaling(TreeNodeRV treeNodeRV) {
		this.treeNodeRV = treeNodeRV;
		rvs = this.treeNodeRV.getAllRvs();
		scaleWidths();
		scaleHeights();
	}

	private void scaleHeights() {
		// TODO Auto-generated method stub
		
	}

	private void scaleWidths() {
		List<Integer> widths = new ArrayList<Integer>();
		//Calculate StandardDeviaton
		for(ResourceVisualization rv : rvs){
			widths.add(rv.getWidth());
		}
		
	}
	
	private double standardDeviation(List<Integer> values){
		int sum = 0;
		double mean = mean(values);
		
		for(Integer value : values){
			sum += Math.pow((value - mean), 2);
		}
		
		return Math.sqrt(sum/values.size());
	}
	
	private double mean(List<Integer> values){
		int sum = sum(values);
		double mean = sum/values.size()*1.0;
		return mean;
		
	}
	
	private int sum(List<Integer> values){
		int sum = 0;
		for(Integer value : values){
			sum += value;
		}
			
		return sum;
	}
	
}
