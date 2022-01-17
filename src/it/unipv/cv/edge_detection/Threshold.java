package it.unipv.cv.edge_detection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unipv.cv.lut.ToGrayScale;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.DisplayImage;
import it.unipv.cv.utils.Utility;

/**
 * A basic thresholding algorithm for images
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class Threshold {
	
	/**
	 * Used to log messages of this class.
	 */
	private Logger logger;
	
	private int OLDTHRESHOLD = 60; //60 
//	private int DIFFERENCE = 3; //
//	private int NEWTHRESHOLD;  //
	
	public BufferedImage outputImage;
	
	/**
	 * Generate a list of coordinate after the thresholding
	 * @param inputImage
	 * @return
	 */
	public ArrayList<Coordinate> thresholding(BufferedImage inputImage){
		logger = Logger.getLogger("");
//		ArrayList<Integer> outputU = new ArrayList<Integer>();
//		ArrayList<Integer> outputL = new ArrayList<Integer>();
		ArrayList<Coordinate> output = new ArrayList<Coordinate>();
	
		outputImage = new BufferedImage(inputImage.getTileWidth(), inputImage.getHeight(), inputImage.getType());
		
		int w = inputImage.getWidth();
		int h = inputImage.getHeight();
//		int m1 = Utility.getMin(inputImage);
//		int m2 = Utility.getMax(inputImage);
//		
//		while (true) {
//			for (int i = 1; i < w - 1; i++) {
//				for (int j = 1; j < h - 1; j++) {
//					int c = inputImage.getRGB(i, j);
//					Color color = new Color(c);
//					int red = color.getRed();
//					int green = color.getGreen();
//					int blue = color.getBlue();
//					int avg = (red+green+blue)/3;
//					if( avg > OLDTHRESHOLD) {
//						outputU.add(avg);//"white"
//					} else {
//						outputL.add(avg);//"black"
//					}	
//				}
//			}
//			m1 = Utility.average(outputL);
//			m2 = Utility.average(outputU);
//			NEWTHRESHOLD = (m1+m2)/2;
//			/*----------------------------*/
//			System.out.println(NEWTHRESHOLD);
//			if ((Math.abs(NEWTHRESHOLD - OLDTHRESHOLD) >= DIFFERENCE)) {
//				break;
//			} else {
//				OLDTHRESHOLD = NEWTHRESHOLD;
//			}
//		}
		
		for (int i = 1; i < w - 1; i++) {
			for (int j = 1; j < h - 1; j++) {
				int c = inputImage.getRGB(i, j);
				Color color = new Color(c);
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				if((red+green+blue)/3 > OLDTHRESHOLD) {
					output.add(Utility.pixelToCoord(new Coordinate(i, j),w,h));//white pixels

					outputImage.setRGB(i, j, 0xffffff);
				} else {
					outputImage.setRGB(i, j, 0);//black pixels
				}	
			}
		}
		logger.log(Level.INFO, "DONE: Applied threshold to edge-points.");
		return output;
	}
	
	/**
	 * Only to test! REMOVE IT BEFORE THE DELIVERY
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "horizontal_line_C.png";	
		BufferedImage img = Utility.read("./images/input/"+name);	
		BufferedImage grayScale = new ToGrayScale(img).perform();
		BufferedImage sobelImage = new SobelFilter().filtering(grayScale);
		Threshold th = new Threshold();
		th.thresholding(sobelImage);//apply the thresholding
		new DisplayImage().displayOneImage(th.outputImage, "Thresholded image");
	}
}
