package it.unipv.cv.line_detection;

import java.util.ArrayList;
import java.util.Arrays;

import it.unipv.cv.utils.Coordinate;

public class Test {

	public static void main(String[] args) {
				
		// contains points from y = 2*x (slope=2) and y = x (slope=1)		
        Coordinate[] edgePoints = new Coordinate[] {new Coordinate(1, 2), new Coordinate(2, 4), new Coordinate(1, 1), new Coordinate(3, 3)};

		
		
		ArrayList<Line> lines = LineFinder.detectLines(Arrays.asList(edgePoints));
		
//		System.out.println(lines);
		
		
		

	}

}
