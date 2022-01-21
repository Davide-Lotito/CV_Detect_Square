package it.unipv.cv.edge_detection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	/**
	 * Used to log messages of this class.
	 */
	private Logger logger;
	
	private int OLDTHRESHOLD = 60; //60 
	
	public BufferedImage outputImage;
	
	/**
	 * Generate a list of coordinate after the thresholding
	 * @param inputImage
	 * @return
	 */
	public ArrayList<Coordinate> thresholding(BufferedImage inputImage){
		logger = Logger.getLogger("CVlogger");
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
}
