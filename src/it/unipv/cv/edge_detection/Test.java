package it.unipv.cv.edge_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import it.unipv.cv.utils.*;


public class Test {

	public static void main(String[] args) {
		String InputPath = "./images/input/vertical_line_C.png";
		
		/*test images names*/
		//architecture_C.png
		//vertical_line_C.png
		//horizontal_line_C.png
		
		/*---			display one image			---*/
		DisplayImage d = new DisplayImage();
		//d.displayOneImage(InputPath,"input image");
		BufferedImage img = Utility.read(InputPath);
		
		BufferedImage grayScaleImage = Utility.toGrayScale(img, img.getWidth(), img.getHeight());
		//d.displayOneImage(grayScaleImage, "gray scale image");
		
		BufferedImage filteredImage = (new SobelFilter()).filtering(grayScaleImage);
		//d.displayOneImage(filteredImage, "filtered image");
		
		Threshold t = new Threshold();
		t.thresholding(filteredImage);
		BufferedImage thImage = t.outputImage;
		//d.displayOneImage(thImage, "");
		
		
		/*---			display more images			---*/
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		images.add(img);
		images.add(grayScaleImage);
		images.add(filteredImage);
		images.add(thImage);
		d.displayMoreImages(images);
				
	}
}
