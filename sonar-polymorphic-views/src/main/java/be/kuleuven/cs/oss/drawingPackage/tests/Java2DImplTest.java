package be.kuleuven.cs.oss.drawingPackage.tests;


import java.awt.image.BufferedImage;


import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import be.kuleuven.cs.oss.drawingPackage.Java2DImpl;

public class Java2DImplTest {

	public static void main(String[] args){
		testArrowRight();
	}

	public static void testBackground() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		impl.createEmptyImage(400, 100);
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void testDrawBox2() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		impl.drawBox(
				10, 10, //int xCoord, int yCoord
				40, 20, //int width, int heigth
				0, 255, 255, //int redBorder, int greenBorder, int blueBorder -> cyan
				255, 127, 0, //redFill, int greenFill, int blueFill -> orange
				2 // borderWidth
				);
		
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void testDrawCircle() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		impl.drawCircle(
				10, 10, //int xCoord, int yCoord
				40, 20, //int width, int heigth
				0, 255, 255, //int redBorder, int greenBorder, int blueBorder -> cyan
				255, 127, 0, //redFill, int greenFill, int blueFill -> orange
				2 // borderWidth
				);
		
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void testDrawText() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		impl.drawText(
				"Sweet baby Jesus", 
				60, 60, 
				-90, 
				255, 127, 0);
		
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void testDrawStraightLine() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		impl.drawStraightLine(10, 10, 50, 50, 255, 172, 0, 2);
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void testDrawTriangle() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		impl.drawTriangle(
				10, 10, //int x1, int y1,
				10, 20, //int x2,int y2,
				20, 10, //int x3, int y3,
				0, 255, 255, //int redBorder, int greenBorder, int blueBorder -> cyan
				255, 127, 0, //redFill, int greenFill, int blueFill -> orange
				2 // borderWidth
				);
		
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void testDrawTriangle2() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		impl.drawTriangle(10, 10, 5, 20, 15, 20);
		
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void testArrowUp() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		impl.drawArrowUp(10, 60, 30);
		
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void testArrowRight() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		impl.drawArrowRight(20, 20, 30);
		
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void output(BufferedImage bi){
		try {
			
		    File outputfile = new File("D:\\eclipse\\workspace OSS\\OSS-Project\\sonar-polymorphic-views\\src\\main\\java\\be\\kuleuven\\cs\\oss\\drawingPackage\\tests\\saved.png");
		    ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
		   
		}
	}
	

}
