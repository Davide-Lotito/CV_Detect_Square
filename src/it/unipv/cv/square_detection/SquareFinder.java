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

	private static final int MAGICNUMBER = 20;
	/**
	 * Used to log messages of this class
	 */
	Logger logger;
	
	public Square detectSquare(BufferedImage image){
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
		
		//if we already have a coordinate too close to a new one we're adding, 
		//then we don't add the new one.
		ArrayList<Coordinate> newIntersections = new ArrayList<Coordinate>();
		for(Coordinate coord : intersections) {
			boolean addMe =true;
			for(Coordinate alreadyThereCoord : newIntersections) {
				if(alreadyThereCoord.distance(coord)< MAGICNUMBER) {
					//don't add 'coord'
					addMe = false;
				}
			}
			if(addMe) {
				newIntersections.add(coord);
			}	
		}
		
		// Plotting the lines and the square-edges that where found:
		for(Line line : lines) {
			image  = line.draw(image);
		}
				
		/**
		 * Get the upperLeft coordinate
		 */
		newIntersections.sort(new Comparator<Coordinate>() {
			@Override
			public int compare(Coordinate o1, Coordinate o2) {
				return o2.Y - o1.Y;
			}
		});
		
		Coordinate first = newIntersections.get(0);
		Coordinate second = newIntersections.get(1);
		Coordinate upperLeft = first;
		if(first.X < second.X) {
			upperLeft = first;
		} else {
			upperLeft = second;
		}
		
		/**
		 * Get the bottomRight coordinate
		 */
		newIntersections.sort(new Comparator<Coordinate>() {
			@Override
			public int compare(Coordinate o1, Coordinate o2) {
				return o2.X - o1.X;
			}
		});
		
		first = newIntersections.get(0);
		second = newIntersections.get(1);
		Coordinate bottomRight = first;
		if(first.Y < second.Y) {
			bottomRight = first;
		} else {
			bottomRight = second;
		}
		
		logger.log(Level.INFO, "DONE: Filtering, remained "+newIntersections.size()+" intersections:");
		int COUNTER = 1;
		for(Coordinate c : newIntersections) {
			logger.log(Level.INFO, "["+COUNTER+"] "+c.toString());
			COUNTER++;
		}

		return new Square(Utility.coordToPixel(upperLeft, image.getWidth(), image.getHeight()), 
				Utility.coordToPixel(bottomRight, image.getWidth(), image.getHeight()));
	}

	/**
	 * Only to test! REMOVE IT BEFORE THE DELIVERY
	 * @param args
	 */
	public static void main(String[] args) {
		SquareFinder squareFinder  = new SquareFinder();
		BufferedImage image = Utility.read("./images/input/test_square.png");
		Square square = squareFinder.detectSquare(image);
		new DisplayImage().displayOneImage(square.draw(image), "square");
	}
}
