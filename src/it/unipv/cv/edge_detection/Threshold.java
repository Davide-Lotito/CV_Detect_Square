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

	private int requiredThresholdValue = 100; //??
	public BufferedImage outputImage;
	
	/**
	 * Generate a list of edge points (coordinate) referenced to the center of the image
	 * @param inputImage
	 * @return
	 */
	public ArrayList<Coordinate> thresholding(BufferedImage inputImage){
		ArrayList<Coordinate> output = new ArrayList<Coordinate>();
		outputImage = new BufferedImage(inputImage.getTileWidth(), inputImage.getHeight(), inputImage.getType());
		int w = inputImage.getWidth();
		int h = inputImage.getHeight();
		for (int i = 1; i < w - 1; i++) {
			for (int j = 1; j < h - 1; j++) {
				int c = inputImage.getRGB(i, j);
				Color color = new Color(c);
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				if((red+green+blue)/3 > requiredThresholdValue) {
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
