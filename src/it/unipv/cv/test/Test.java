/**
 * 
 */
package it.unipv.cv.test;

import java.awt.image.BufferedImage;
import java.io.File;

import it.unipv.cv.square_detection.SquareFinder;
import it.unipv.cv.utils.CommandLine;
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
//		CommandLine command = new CommandLine(args);
//		String pathname = command.name;

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
		BufferedImage image = Utility.read(pathname);
		
		//find and plot the squares
		SquareFinder squareFinder  = new SquareFinder();
		squareFinder.detectSquares(image);
	}
}
