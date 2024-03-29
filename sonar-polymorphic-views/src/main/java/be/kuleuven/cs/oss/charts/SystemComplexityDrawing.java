package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.lines.LineFactory;
import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.trees.TreeNodeRV;


public class SystemComplexityDrawing {
	
	private final static Logger LOG = LoggerFactory.getLogger(PolymorphicViewsChart.class);
	
	private IDraw drawInterface;
	private LineFactory lf;
	
	public SystemComplexityDrawing(IDraw drawInterface, LineFactory lf) {
		this.drawInterface = drawInterface;
		this.lf = lf;

	}
	

	public BufferedImage drawTreeRV(TreeNodeRV tree) {
		LOG.info("Started drawing a TreeRV");
		
		SystemComplexityScaling scaler = new SystemComplexityScaling(tree);
		List<ResourceVisualization> rvs = scaler.scale();
		List<Connection> cons = tree.getAllConnections();
		tree.updateXPosition(0);
		tree.updateYPositions(new HashMap<Integer, Integer>());
		
		drawInterface.createEmptyImage(1920, 1080);
		
		
		for(ResourceVisualization rv: rvs) {
			rv.draw(drawInterface);
		}
		for(Connection connection : cons){
			connection.draw(drawInterface, lf);
		}
		
		LOG.info("Ended drawing a TreeRV");
		return drawInterface.getBufferedImage();
	}


	

}
