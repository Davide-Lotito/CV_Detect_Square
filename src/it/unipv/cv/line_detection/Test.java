package it.unipv.cv.line_detection;

import java.awt.image.BufferedImage;
import java.io.File;
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


		// paths to a bunch of test images
		String root = "images"+File.separator+"input"+File.separator;
		String diagLines = root+"diagonal_lines.png";
		String square = root+"square.png";
		String horizontal = root+"horizontal_line_C.png";
		String sudoku =  root+"sudoku.png";
		String sudoku2 =  root+"sudoku_settimana.jpg";

		// pick a test image
		String pathname = horizontal;
		
		
		//read the image and make a copy of it
		BufferedImage image = Utility.read(pathname);
		

		//detect the lines on the image
		LineFinder lineFinder = new LineFinder();
		ArrayList<Line> lines = lineFinder.detectLines(image);
		
		//draw each line
		for(Line line : lines) {
			image = line.draw(image);
		}
		
		for(BufferedImage img : lineFinder.imageSequence) {
			new DisplayImage().displayOneImage(img, "");
		}
		
		//display the images with the detected lines
		new DisplayImage().displayOneImage(image, "detected lines");


	}






}
