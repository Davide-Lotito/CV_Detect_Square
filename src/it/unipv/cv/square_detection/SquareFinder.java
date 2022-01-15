package it.unipv.cv.square_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import it.unipv.cv.line_detection.Line;
import it.unipv.cv.line_detection.LineFinder;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.DisplayImage;
import it.unipv.cv.utils.Utility;

public class SquareFinder {
	
	
	public ArrayList<Square> detectSquares(BufferedImage image){
		
		LineFinder lineFinder = new LineFinder();
		ArrayList<Line> lines = lineFinder.detectLines(image);
		ArrayList<Coordinate> intersections = new ArrayList<Coordinate>(); 
		
		//System.out.println("Before "+lines.size());
		//remove equals lines
		for(int i=0; i<lines.size()-1; i++) {
			Line line1 = lines.get(i);
			Line line2 = lines.get(i+1);
			if(line1.equals(line2))
				lines.remove(line1);
		}
		//System.out.println("After "+lines.size());
		
		
		//System.out.println("Searching for intersections....");
		for(Line line1 : lines) {
			for(Line line2 : lines) {
				if(line1.isParallel(line2)) continue;
				Coordinate c;
				try {
					c = line1.intersects(line2);
					intersections.add(c);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
//				try {
//					Coordinate c = line1.intersects(line2);
//					if(c.X == 0 || c.Y == 0) {
//						System.out.println(line1);
//						System.out.println(line2);
//						System.out.println("---");
//					}
//					intersections.add(c);
//				} catch (Exception e) {
//					System.err.println(e.getMessage());
//				}
//			}
			}
		}
		
		
//		// Removing points that are too close to the origin.
//		Coordinate origin = new Coordinate(0, 0);
//		// This is crazy, but it works ¯\_(ツ)_/¯:
//		for(int i = 0; i< intersections.size()+1 /*BUT WHY? ¯\_(ツ)_/¯ */; i++) {
//			Coordinate pt = intersections.get(i);
//			if( origin.distance(pt) < 10 ) {				
//				intersections.remove(i);
//				intersections.remove(i);// BUT WHY? ¯\_(ツ)_/¯
//			}
//		}
//		// PS: Also, Shrugging Emoji makes eclipse's editor go bonkers! ¯\_(ツ)_/¯ ¯\_(ツ)_/¯
		
		
		// Plotting the lines and the square-edges that where found:
		for(Line line : lines) {
			image  = line.draw(image);
		}
		image = Utility.plotPoints(image, intersections,5);		
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
		BufferedImage image = Utility.read("./images/input/test_square.png");
		squareFinder.detectSquares(image);
		
	}
}
