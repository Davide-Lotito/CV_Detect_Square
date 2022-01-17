package it.unipv.cv.edge_detection;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unipv.cv.lut.ToGrayScale;
import it.unipv.cv.utils.DisplayImage;
import it.unipv.cv.utils.Utility;

/**
 * Implement the Sobel Filtering algorithm
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class SobelFilter implements EdgeDetector{
	
	/**
	 * Used to log messages of this class.
	 */
	private Logger logger;
	
	/**
	 * Produce an image, after Sobel Filtering
	 * @param image gray scale image to analize
	 */
	public BufferedImage filtering(BufferedImage image) {
		logger = Logger.getLogger("");
		int x = image.getWidth();
		int y = image.getHeight();
		BufferedImage outputImage = new BufferedImage(x, y, image.getType());
		//2D array for the new image
        int[][] edgeColors = new int[x][y];
        int maxGradient = -1;//default value

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {

                //int val00 = Utility.getGrayScalePixel(image.getRGB(i - 1, j - 1));
            	int val00 = (image.getRGB(i - 1, j - 1))&0xFF;
                //int val01 = Utility.getGrayScalePixel(image.getRGB(i - 1, j));
            	int val01 = (image.getRGB(i - 1, j))&0xFF;
            	//int val02 = Utility.getGrayScalePixel(image.getRGB(i - 1, j + 1));
            	int val02 = (image.getRGB(i - 1, j + 1))&0xFF;
            	
                //int val10 = Utility.getGrayScalePixel(image.getRGB(i, j - 1));
            	int val10 = (image.getRGB(i, j - 1))&0xFF;
                //int val11 = Utility.getGrayScalePixel(image.getRGB(i, j));
            	int val11 = (image.getRGB(i, j))&0xFF;
                //int val12 = Utility.getGrayScalePixel(image.getRGB(i, j + 1));
            	int val12 = (image.getRGB(i, j + 1))&0xFF;

                //int val20 = Utility.getGrayScalePixel(image.getRGB(i + 1, j - 1));
            	int val20 = (image.getRGB(i + 1, j - 1))&0xFF;
                //int val21 = Utility.getGrayScalePixel(image.getRGB(i + 1, j));
            	int val21 = (image.getRGB(i + 1, j))&0xFF;
                //int val22 = Utility.getGrayScalePixel(image.getRGB(i + 1, j + 1));
            	int val22 = (image.getRGB(i + 1, j + 1))&0xFF;
            	

                int gx =  ((-1 * val00) + (0 * val01) + (1 * val02)) 
                        + ((-2 * val10) + (0 * val11) + (2 * val12))
                        + ((-1 * val20) + (0 * val21) + (1 * val22));

                int gy =  ((-1 * val00) + (-2 * val01) + (-1 * val02))
                        + ((0 * val10) + (0 * val11) + (0 * val12))
                        + ((1 * val20) + (2 * val21) + (1 * val22));
                
                double gval = Math.sqrt((gx * gx) + (gy * gy));
                int g = (int) gval;

                if(maxGradient < g) {
                    maxGradient = g;
                    //to get the max gradient, useful to normalize
                }

                edgeColors[i][j] = g;
            }
        }

        double scale = 255.0 / maxGradient;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int)(edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;
                outputImage.setRGB(i, j, edgeColor);
            }
        }
        logger.log(Level.INFO, "DONE: Detected edges with Sobel Filtering");
		return outputImage;
	}
	
	/**
	 * Only to test! REMOVE IT BEFORE THE DELIVERY
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "horizontal_line_C.png";	
		BufferedImage img = Utility.read("./images/input/"+name);
		BufferedImage grayScale = new ToGrayScale(img).perform();
		new DisplayImage().displayOneImage(new SobelFilter().filtering(grayScale), "Sobel Result");
	}
}


