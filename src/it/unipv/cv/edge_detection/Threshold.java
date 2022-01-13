package it.unipv.cv.edge_detection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.Utility;

/**
 * A basic thresholding algorithm for images
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class Threshold {

	private int OLDTHRESHOLD = 75;
//	private int DIFFERENCE = 15;
//	private int NEWTHRESHOLD;
	public BufferedImage outputImage;
	
	/**
	 * Generate a list of edge points (coordinate) referenced to the center of the image
	 * @param inputImage
	 * @return
	 */
	public ArrayList<Coordinate> thresholding(BufferedImage inputImage){
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
//			//System.out.println(NEWTHRESHOLD);
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
		
		
		Utility.writeImage(outputImage, "threshold");
		return output;
	}
	

}
