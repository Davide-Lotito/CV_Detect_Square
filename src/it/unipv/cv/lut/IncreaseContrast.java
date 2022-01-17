package it.unipv.cv.lut;

import java.awt.Color;
import java.awt.image.BufferedImage;

import it.unipv.cv.utils.Utility;

/**
 * To increase constrast of an image
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 * 
 */
public class IncreaseContrast extends AbstractLutOperation{
	
	int min;
	int max;
	
	public IncreaseContrast(BufferedImage image) {
		super(image);
		min = Utility.getMin(image);
		max = Utility.getMax(image);
	}

	@Override
	protected int singlePixel(int pixel) {
		
		Color c =new Color(pixel);
		pixel = (c.getRed()+c.getGreen()+c.getBlue())/3;
		
		if (pixel < min) {
			return 0xff000000;
		}
		
		if (pixel>max) {
			return 0xffffffff;
		}
		
		
		pixel = 255*(pixel-min)/(max-min);
		pixel = (pixel<<16) | (pixel<<8) | pixel;
		return pixel;
	}
}
