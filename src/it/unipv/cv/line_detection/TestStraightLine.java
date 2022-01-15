package it.unipv.cv.line_detection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import it.unipv.cv.utils.CommandLine;
import it.unipv.cv.utils.DisplayImage;
import it.unipv.cv.utils.Utility;

/**
 * Test the straight line detection
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class TestStraightLine {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		// command lines operations
		//CommandLine command = new CommandLine(args);
		//String pathname = command.name;

		// paths to a bunch of test images
		String root = "images"+File.separator+"input"+File.separator;
		String diagLines = root+"diagonal_lines.png";
		String square = root+"square.png";
		String horizontal = root+"horizontal_line_C.png";
		String sudoku =  root+"sudoku.png";
		String sudoku2 =  root+"sudoku_settimana.jpg";
		String test1 = root+"test_square.png";

		// pick a test image
		String pathname = square;

		//read the image and make a copy of it
		//BufferedImage image = Utility.read(pathname);

		BufferedImage image = Utility.read(pathname);

		//detect the lines on the image
		LineFinder lineFinder = new LineFinder();
		ArrayList<Line> lines = lineFinder.detectLines(image);

		//draw each line
		//for(Line line : lines) {
		//remove equals lines
		for(int i=0; i<lines.size()-1; i++) {
			Line line1 = lines.get(i);
			Line line2 = lines.get(i+1);
			if(line1.equals(line2)) {
				//lines.remove(line1);
				continue;
			}	
			image = line1.draw(image);
		}
		LineFinder.imageSequence.add(image);

		//display the image with the detected lines
		new DisplayImage().displayOneImage(LineFinder.imageSequence.get(LineFinder.imageSequence.size()-2), "thresholding");
		new DisplayImage().displayOneImage(image, "detected lines");
		//display all the images
		//new DisplayImage().displayMoreImages(LineFinder.imageSequence);
	}
}
