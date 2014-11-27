package be.kuleuven.cs.oss.resourcevisualizations;

import java.util.Map;

public interface ResourceVisualizationFactory {
	
	
	public ResourceVisualization create(Map<String,Double> map);

}
