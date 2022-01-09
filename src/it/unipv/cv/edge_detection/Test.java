package it.unipv.cv.edge_detection;

import java.awt.image.BufferedImage;


public class Test {

	public static void main(String[] args) {
		String InputPath = "./images/input/architecture_C.png";
		new DisplayImage(InputPath,"input image");
		BufferedImage img = Utility.read(InputPath);
		BufferedImage output = Utility.toGrayScale(img, img.getWidth(), img.getHeight());
		//print directly with the BufferedImage
		new DisplayImage(output, "gray scale image");
	}

	
}
