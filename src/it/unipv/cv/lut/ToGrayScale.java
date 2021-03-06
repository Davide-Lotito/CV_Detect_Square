package it.unipv.cv.lut;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * To get a gray scale image
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 * 
 */
public class ToGrayScale extends AbstractLutOperation {
	
	/**
	 * Used to log messages of this class.
	 */
	private Logger logger;

	public ToGrayScale(BufferedImage image) {
		super(image);
		logger = Logger.getLogger("CVlogger");
	}

	@Override
	protected int singlePixel(int pixel) {
		int r = (pixel>>16)&255;
		int g = (pixel>>8)&255;
		int b = (pixel)&255;

		//int avg = (r+g+b)/3;
		int avg = (int) (0.299*r + 0.587*g + 0.114*b);//luminance
		
		avg = (avg<<16) | (avg<<8) | avg;
		return avg;
	}
	
	@Override
	public BufferedImage perform() {
		logger.log(Level.INFO, "DONE: Obtained grey-level image.");
		return super.perform();
	}	
}
