package be.kuleuven.cs.oss.resourcevisualizations;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;


/**
 * Class representing Box resource visualizations.
 * @author Lennart De Graef
 *
 */
public class Box extends ResourceVisualization{                     
	
	private static final int TEXT_OFFSET = 1;
	
	
	public Box(Position position, Size size, Color color, String name) throws IllegalArgumentException{
		super(position, size, color, name);
	}

	
	
	@Override
	public void draw(IDraw d){

		//TODO zet overloading ook in interface
		d.drawBox(getX(), getY(), getWidth(), getHeight(), getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		d.drawText(getName(), getX(), getY()- getHeight()/2 - TEXT_OFFSET, 0, 0, 0, 0);

	}


	
	
}
