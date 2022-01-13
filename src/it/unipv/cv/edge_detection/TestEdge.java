package it.unipv.cv.edge_detection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import it.unipv.cv.utils.*;

/**
 * Test of the edge points
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class TestEdge {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		// paths to a bunch of test images
		String root = "images"+File.separator+"input"+File.separator;
		String diagLines = root+"diagonal_lines.png";
		String square = root+"square.png";
		String horizontal = root+"horizontal_line_C.png";
		String sudoku =  root+"sudoku.png";
		String sudoku2 =  root+"sudoku_settimana.jpg";
		
		// pick a test image
		String pathname = sudoku2;
		
		/*---			display one image			---*/
		DisplayImage d = new DisplayImage();
		//d.displayOneImage(InputPath,"input image");
		BufferedImage img = Utility.read(pathname);
		
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
