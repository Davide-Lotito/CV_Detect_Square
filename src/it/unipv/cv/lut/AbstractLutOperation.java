package it.unipv.cv.lut;

import java.awt.image.BufferedImage;
/**
 * An abstract implementation of a LUT operation
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 * 
 */
import it.unipv.cv.utils.Utility;

/**
 * An abstract implementation of a LUT operation
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 * 
 */
public abstract class AbstractLutOperation implements LutOperation {

	/**
	 * Image that undergoes operations.
	 */

	BufferedImage image;
	
	public AbstractLutOperation(BufferedImage image) {
		this.image =image;
	}


	@Override
	public BufferedImage perform() {
		
		BufferedImage output = Utility.copyImage(image); 
		for(int i=0; i<image.getWidth();i++) {
			for(int j=0; j<image.getHeight();j++) {
				output.setRGB(i, j, singlePixel(image.getRGB(i, j)));
			}
		}
		return output;
	}

	/**
	 * To be implemented by subclasses.
	 * @param pixel
	 * @return
	 */
	abstract protected int singlePixel(int pixel);
}





