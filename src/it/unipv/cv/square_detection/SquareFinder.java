package it.unipv.cv.square_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

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


		//System.out.println("Searching for intersections....");
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
