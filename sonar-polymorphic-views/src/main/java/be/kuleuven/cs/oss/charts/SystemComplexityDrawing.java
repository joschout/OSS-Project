package be.kuleuven.cs.oss.charts;

import java.util.List;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.trees.TreeNodeRV;


public class SystemComplexityDrawing {
	
	private IDraw drawInterface;
	
	public SystemComplexityDrawing(IDraw drawInterface) {
		this.drawInterface = drawInterface;

	}
	

	public void drawTreeRV(TreeNodeRV tree) {
		List<ResourceVisualization> rvs = tree.getAllRvs();
		
		for(ResourceVisualization rv: rvs) {
			rv.draw(drawInterface);
		}
	}


	

}
