package it.unipv.cv.square_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unipv.cv.line_detection.Line;
import it.unipv.cv.line_detection.LineFinder;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.DisplayImage;
import it.unipv.cv.utils.Utility;

/**
 * To find the squares
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class SquareFinder {

	/**
	 * Used to log messages of this class
	 */
	Logger logger;
	
	public ArrayList<Square> detectSquares(BufferedImage image){
		logger = Logger.getLogger("");
				
		LineFinder lineFinder = new LineFinder();
		ArrayList<Line> lines = lineFinder.detectLines(image);
		ArrayList<Coordinate> intersections = new ArrayList<Coordinate>(); 

		for(Line line1 : lines) {
			for(Line line2 : lines) {
				try {
					Coordinate  c = line1.intersects(line2);
					intersections.add(c);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
		logger.log(Level.INFO, "DONE: Compute all interesections.");

		intersections.sort(new Comparator<Coordinate>() {
			@Override
			public int compare(Coordinate o1, Coordinate o2) {
				return (int)(o1.distance(o2)*100);
			}
		});
		
		ArrayList<Coordinate> newIntersections = new ArrayList<Coordinate>();
		for(int i=0; i<intersections.size()-1; i++) {
			Coordinate c1 = intersections.get(i);
			Coordinate c2 = intersections.get(i+1);
			if(c1.distance(c2) > 700) {
				newIntersections.add(c1);
			}
		}

		// Plotting the lines and the square-edges that where found:
		for(Line line : lines) {
			image  = line.draw(image);
		}
		
		image = Utility.plotPoints(image, newIntersections,5);	
		new DisplayImage().displayOneImage(image, "Square Edges");

		// USE THE INTERSECTION POINTS TO BUILD SQUARES
		return null;

	}

	/**
	 * Only to test! REMOVE IT BEFORE THE DELIVERY
	 * @param args
	 */
	public static void main(String[] args) {
		SquareFinder squareFinder  = new SquareFinder();
		BufferedImage image = Utility.read("./images/input/square.png");
		squareFinder.detectSquares(image);
	}
}
