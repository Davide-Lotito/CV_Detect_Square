package it.unipv.cv.edge_detection;

import java.awt.image.BufferedImage;


public class Test {

	public static void main(String[] args) {
		String InputPath = "./images/input/vertical_line_C.png";
		//architecture_C.png
		//vertical_line_C.png
		//horizontal_line_C.png
		new DisplayImage(InputPath,"input image");
		BufferedImage img = Utility.read(InputPath);
		
		BufferedImage grayScaleImage = Utility.toGrayScale(img, img.getWidth(), img.getHeight());
		new DisplayImage(grayScaleImage, "gray scale image");
	}


}
