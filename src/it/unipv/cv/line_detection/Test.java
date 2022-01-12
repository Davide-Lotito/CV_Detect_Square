package it.unipv.cv.line_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


import it.unipv.cv.utils.DisplayImage;
import it.unipv.cv.utils.Utility;

/**
 * Test the straight line detection
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class Test {

	public static void main(String[] args) {


		// currently works for 'diag_lines' but not for 'square'
		String diagLines = "images/input/diagonal_lines.png";
		String square = "images/input/square.png";
		String horizontal = "images/input/horizontal_line_C.png";
		//String pathname = diagLines;
		String pathname = square;


		//read the image and make a copy of it
		BufferedImage original = Utility.read(pathname);
		BufferedImage image = Utility.copyImage(original);

		//detect the lines on the image
		ArrayList<Line> lines = LineFinder.detectLines(image);


		//draw each line
		for(Line line : lines) {
			image = line.draw(image);
		}
		
		//show results
		ArrayList<BufferedImage> imagesList = new ArrayList<BufferedImage>();
		imagesList.add(original);
		imagesList.add(image);
		new DisplayImage().displayMoreImages(imagesList );

	}






}
