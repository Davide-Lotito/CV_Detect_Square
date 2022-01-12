package it.unipv.cv.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


/**
 * Class with some generic useful methods
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class Utility {

	/**
	 * To read the image
	 * @param fileName		the path where read the file
	 * @return
	 */
	public static BufferedImage read(String fileName) {
		try {
			File f = new File(fileName);
			return ImageIO.read(f);
		} catch(IOException e) {
			System.err.println("Error in the file reading!");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	/**
	 * Starting from any (color) image, it creates a grayscale one
	 * @param w		the weight of the input image
	 * @param h		the weight of the input image
	 * @param input 	the input BufferedImage
	 * @return
	 */
	public static BufferedImage toGrayScale(BufferedImage input, int w, int h) {
		// creating output Image
		BufferedImage output = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);

		//convert to grayscale
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				int v = input.getRGB(x,y);
				
				int avg = getGrayScalePixel(v);
				//replace RGB value with avg
				v = (avg<<16) | (avg<<8) | avg;
				
				output.setRGB(x, y, v);
			}
		}

		writeImage(output,"grayScale");
		return output;
	}

	public static int getGrayScalePixel(int v) {
		int r = (v>>16)&255;
		int g = (v>>8)&255;
		int b = (v)&255;

		//int avg = (r+g+b)/3;
		int avg = (int) (0.299*r + 0.587*g + 0.114*b);//luminance
		
		return avg;
	}

	/**
	 * To write image on the disk
	 * @param output		the path where save the file
	 */
	public static void writeImage(BufferedImage output, String name) {
		
		
		if(! new File("./images/output/").isDirectory()) {
			new File("./images/output/").mkdir();
		}
		
		try{
			
			File f = new File("./images/output/"+name+".png");
			ImageIO.write(output, "png", f);
			
			
		}catch(IOException e){
			System.err.println("Error in the file writing!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Resize an image
	 * @param originalImage
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
	    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
	    Graphics2D graphics2D = resizedImage.createGraphics();
	    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	    graphics2D.dispose();
	    return resizedImage;
	}
	
	/**
	 * Compute the avarage value, from pixels
	 * @param inputImage
	 * @return
	 */
	public static int averagePixels(BufferedImage inputImage) {
		int x = inputImage.getWidth();
		int y = inputImage.getHeight();
		
		//accumulator value
		int avg = 0;
		
		for (int i = 1; i < x - 1; i++) {
			for (int j = 1; j < y - 1; j++) {
				int c = inputImage.getRGB(i, j);
				Color color = new Color(c);
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				
				avg += (red+green+blue)/3;
			}
		}
		return avg/(x*y);
	}
	
	
	/**
	 * Create a vector of Int, from 0 to 180, euidistanced of step
	 * 
	 * @param step
	 * @return
	 */
	public  static Integer[] generateThetas(int step) {
		int size = (180/step)+1;
		Integer[] thetas = new Integer[size];
		int value = 0;
		for(int i=0; i<size; i++) {
			thetas[i]=value;
			value++;
			
		}
		return thetas;
	}
	
	
	
	/**
	 * Convert a coordinate in the Cartesian reference system to a Coordinate in the upper-left-corner reference system.
	 * @param coordinate
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	public  static Coordinate coordToPixel(Coordinate coordinate, int imageWidth, int imageHeight) {
		return new Coordinate( coordinate.X + imageWidth/2, imageHeight/2-coordinate.Y );
	}
	
	
	/**
	 * Convert a Coordinate in in the upper-left-corner reference system to a Coordinate in the Cartesian reference system. 
	 * @param coordinate
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	public  static Coordinate pixelToCoord(Coordinate pixel, int imageWidth, int imageHeight) {
		return new Coordinate( pixel.X - imageWidth/2, imageHeight/2 - pixel.Y );
	}
	
	/**
	 * Make a copy of a BufferedImage.
	 * @param source
	 * @return
	 */
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	/**
	 * Plot a set of points on a buffered image. 
	 * @param image
	 * @param points
	 * @return
	 */
	public static BufferedImage plotPoints(BufferedImage image, ArrayList<Coordinate> points) {
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.red);
		
		for(Coordinate c : points) {
			Coordinate pixel = coordToPixel(c, image.getWidth(), image.getHeight());
			g.fillRect(pixel.X, pixel.Y, 1, 1);
		}
		g.dispose();
		return image;
	}
	
	
	/**
	 * Get a blank (all black) image of the same size as the argument.
	 * @param source
	 * @return
	 */
	public static BufferedImage blankImage(BufferedImage source){
		BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int i =0; i<b.getWidth(); i++) {
			for(int j =0; j<b.getHeight(); j++) {
				b.setRGB(i, j, 0xff000000);
			}
		}
		return b;
	}

		
	
	
	
	
	
	
}
