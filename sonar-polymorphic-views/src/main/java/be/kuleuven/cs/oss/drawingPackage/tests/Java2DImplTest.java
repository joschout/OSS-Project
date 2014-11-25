package be.kuleuven.cs.oss.drawingPackage.tests;


import java.awt.image.BufferedImage;


import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import be.kuleuven.cs.oss.drawingPackage.Java2DImpl;

public class Java2DImplTest {

	public static void main(String[] args){
		test5();
	}

	public static void test1() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void test2() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		/*
		int xCoord, int yCoord,
		int width, int heigth,
		int redBorder, int greenBorder, int blueBorder,
		int redFill, int greenFill, int blueFill,
		int borderWidth
		*/
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
	
	public static void test3() {
		Java2DImpl impl = new Java2DImpl(400, 100);
		/*
		int xCoord, int yCoord,
		int width, int heigth,
		int redBorder, int greenBorder, int blueBorder,
		int redFill, int greenFill, int blueFill,
		int borderWidth
		*/
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
	
	public static void test4() {
		Java2DImpl impl = new Java2DImpl(400, 100);

		impl.drawText(
				"Sweet baby Jesus", 
				60, 60, 
				-90, 
				255, 127, 0);
		
		BufferedImage bi = impl.getBufferedImage();
		output(bi);
	}
	
	public static void test5() {
		Java2DImpl impl = new Java2DImpl(400, 100);

		impl.drawStraightLine(10, 10, 50, 50, 255, 172, 0, 2);
		
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