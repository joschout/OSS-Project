package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class SystemComplexity extends Chart {
	
	private List<Connection> connections;
	
	public SystemComplexity(List<Resource> resources, ResourceVisualizationFactory RVF, SonarFacade sonarF, ResourcePropertiesManager propManager) {
		super(resources, RVF, sonarF, propManager);
		connections = new ArrayList<Connection>();
	}

	@Override
	public BufferedImage draw(IDraw drawInterface) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
}
