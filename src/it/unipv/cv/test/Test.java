/**
 * 
 */
package it.unipv.cv.test;

import java.awt.image.BufferedImage;
import java.io.File;

import it.unipv.cv.square_detection.Square;
import it.unipv.cv.square_detection.SquareFinder;
import it.unipv.cv.utils.CommandLine;
import it.unipv.cv.utils.DisplayImage;
import it.unipv.cv.utils.Utility;

/**
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
@SuppressWarnings("unused")
public class Test {
	
	public static void main(String[] args) {
		
		// command lines operations
		CommandLine command = new CommandLine(args);
		String pathname = command.name;

		// paths to a bunch of test images
		String root = "images"+File.separator+"input"+File.separator;
		String test2 = root+"beautifulSquare.png";
		String test1 = root+"test_square.png";
		

		// pick a test image
		BufferedImage image = Utility.read(pathname);
		
		//find and plot the squares
		SquareFinder squareFinder  = new SquareFinder();		
		Square square = squareFinder.detectSquare(image);
		new DisplayImage().displayOneImage(square.draw(image), "square");
	}
}
