package it.unipv.cv.edge_detection;

import java.awt.image.BufferedImage;

import it.unipv.cv.utils.Utility;

public class SobelFilter implements EdgeDetector{
	
	/**
	 * Produce an image, after Sobel Filtering
	 * @param image The image to analize
	 */
	public BufferedImage filtering(BufferedImage image) {
		int x = image.getWidth();
		int y = image.getHeight();
		BufferedImage outputImage = new BufferedImage(x, y, image.getType());
		//2D array for the new image
        int[][] edgeColors = new int[x][y];
        int maxGradient = -1;//default value

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {

                int val00 = Utility.getGrayScalePixel(image.getRGB(i - 1, j - 1));
                int val01 = Utility.getGrayScalePixel(image.getRGB(i - 1, j));
                int val02 = Utility.getGrayScalePixel(image.getRGB(i - 1, j + 1));

                int val10 = Utility.getGrayScalePixel(image.getRGB(i, j - 1));
                int val11 = Utility.getGrayScalePixel(image.getRGB(i, j));
                int val12 = Utility.getGrayScalePixel(image.getRGB(i, j + 1));

                int val20 = Utility.getGrayScalePixel(image.getRGB(i + 1, j - 1));
                int val21 = Utility.getGrayScalePixel(image.getRGB(i + 1, j));
                int val22 = Utility.getGrayScalePixel(image.getRGB(i + 1, j + 1));

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
		Utility.writeImage(outputImage, "sobel");
		return outputImage;
	}
}


