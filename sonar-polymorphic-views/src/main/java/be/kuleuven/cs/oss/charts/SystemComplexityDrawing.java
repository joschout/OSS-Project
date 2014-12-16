package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.lines.LineFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.trees.TreeNodeRV;


public class SystemComplexityDrawing {
	
	private static final int OFFSET = 50;

	private final static Logger LOG = LoggerFactory.getLogger(SystemComplexityDrawing.class);

	private IDraw drawInterface;
	private LineFactory lf;

	public SystemComplexityDrawing(IDraw drawInterface, LineFactory lf) {
		this.drawInterface = drawInterface;
		this.lf = lf;

	}


	public BufferedImage drawTreeRV(TreeNodeRV tree) {
		LOG.info("Started drawing a TreeRV");

		//SystemComplexityScaling scaler = new SystemComplexityScaling(tree);
		//List<ResourceVisualization> rvs = scaler.scale();
		
		List<ResourceVisualization> rvs = tree.getAllRvs();
		try {
			List<Connection> cons = tree.getAllConnections();

			tree.updateXPosition(0);

			Map<Integer, Integer> maxHeights = tree.getMaxHeightPerLevel(new HashMap<Integer, Integer>());

			tree.updateYPositions(maxHeights);
			
			int imageX = 0;
			int imageY = 0;
			for(ResourceVisualization rv: rvs) {
				int tempX = rv.getX() + rv.getWidth()/2;
				int tempY = rv.getY() + rv.getHeight()/2;
				
				if(tempX > imageX) {
					imageX = tempX;
				}
				if(tempY > imageY) {
					imageY = tempY;
				}
			}
			
			drawInterface.createEmptyImage(imageX + OFFSET, imageY + OFFSET);


			for(ResourceVisualization rv: rvs) {
				rv.draw(drawInterface);
			}
			for(Connection connection : cons){
				connection.draw(drawInterface, lf);
			}

			LOG.info("Ended drawing a TreeRV");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return drawInterface.getBufferedImage();
	}




}
