package be.kuleuven.cs.oss.drawingPackage;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import be.kuleuven.cs.oss.drawingPackage.Java2DFacade;

public class JVA2DTestEline {

	@Test
	public void testbox() {
		Java2DFacade impl = new Java2DFacade();
		impl.createEmptyImage(400, 100);
		impl.drawBox(
				0, 0, //int xCoord, int yCoord
				60, 20, //int width, int heigth
				0, 255, 255, //int redBorder, int greenBorder, int blueBorder -> cyan
				255, 127, 0, //redFill, int greenFill, int blueFill -> orange
				2 // borderWidth
				);
		
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void output(BufferedImage bi){
		try {
			
		    File outputfile = new File("C:\\users\\eline vanermen\\Documents\\GitHub\\OSS-Project\\sonar-polymorphic-views\\src\\main\\java\\be\\kuleuven\\cs\\oss\\drawingPackage\\tests\\saved2.png");
		    ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
		   
		}
	}
}

