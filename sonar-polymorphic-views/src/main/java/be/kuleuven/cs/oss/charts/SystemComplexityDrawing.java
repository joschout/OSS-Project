package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.lines.LineFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.trees.TreeNodeRV;


public class SystemComplexityDrawing {
	
	private IDraw drawInterface;
	private LineFactory lf;
	
	public SystemComplexityDrawing(IDraw drawInterface, LineFactory lf) {
		this.drawInterface = drawInterface;
		this.lf = lf;

	}
	

	public BufferedImage drawTreeRV(TreeNodeRV tree) {
		tree.updateXPosition(0);
		tree.updateYPositions(new HashMap<Integer, Integer>());
		List<ResourceVisualization> rvs = tree.getAllRvs();
		List<Connection> cons = tree.getAllConnections();
		
		drawInterface.createEmptyImage(1920, 1080);
		
		for(ResourceVisualization rv: rvs) {
			rv.draw(drawInterface);
		}
		for(Connection connection : cons){
			connection.draw(drawInterface, lf);
		}
		
		return drawInterface.getBufferedImage();
	}


	

}
