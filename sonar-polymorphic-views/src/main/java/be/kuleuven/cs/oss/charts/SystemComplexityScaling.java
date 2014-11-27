package be.kuleuven.cs.oss.charts;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.trees.TreeNodeRV;

public class SystemComplexityScaling {
	
	private TreeNodeRV treeNodeRV;
	private List<ResourceVisualization> rvs;
	
	private double SCALING_FACTOR = 1; //TODO FOR NOW
	private int STANDARD_WIDTH = 50;
	private int STANDARD_HEIGHT = 50;
	
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
		for(ResourceVisualization rv : rvs){
			widths.add(rv.getWidth());
		}
		double sd = standardDeviation(widths);
		double mean = mean(widths);
		
		//sample = factor*sd +mean => (sample - mean)/sd = amount of sigmas away from mean
		//calculate the amount of sigma's a width of an RV is from the mean and scale accordingly
		
		for(ResourceVisualization rv : rvs){
			double z = (rv.getWidth() - mean)/sd;
			
			if(z>=0){
				int scaledWidth = (int) (STANDARD_WIDTH*(z*SCALING_FACTOR + 1));
				rv.getSize().setWidth(scaledWidth);
			}
			else{
				int scaledWidth = (int)	(STANDARD_WIDTH/(Math.abs(z)*SCALING_FACTOR +1));
				rv.getSize().setWidth(scaledWidth);
			}
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
