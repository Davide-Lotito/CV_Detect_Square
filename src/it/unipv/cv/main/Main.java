/**
 * 
 */
package it.unipv.cv.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;

import it.unipv.cv.edge_detection.SobelFilter;
import it.unipv.cv.line_detection.LineFinder;
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
public class Main {
	
	public static void main(String[] args) {
		
		Logger logger = Logger.getLogger("CVlogger");
		/**
		 * master switch for print logs
		 */
		logger.setUseParentHandlers(false);
		
		// command lines operations
		CommandLine command = new CommandLine(args);
		String pathname = command.name;
		
		// pick a test image
		BufferedImage imageOriginal = Utility.read(pathname);
		
		//do its computation
		ArrayList<BufferedImage> display = new ArrayList<BufferedImage>();
		SquareFinder squareFinder  = new SquareFinder();		
		Square square = squareFinder.detectSquare(imageOriginal);
		
		display.add(imageOriginal);
		display.add(new SobelFilter().filtering(imageOriginal));
		display.add(LineFinder.drawLines(imageOriginal));
		display.add(square.draw(imageOriginal));
				
		new DisplayImage().displayMoreImages(display);
	}
}
