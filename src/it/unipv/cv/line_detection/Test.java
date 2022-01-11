package it.unipv.cv.line_detection;

import java.util.ArrayList;
import java.util.Arrays;

import it.unipv.cv.utils.Coordinate;

public class Test {

	public static void main(String[] args) {
		
		//generate points on a line
		
		Coordinate[] edgePoints = new Coordinate[] {new Coordinate(1, 2), new Coordinate(2, 4)};
		
		//noise new Point(1, 3), new Point(0, 9), new Point(2, 5)
		
		
		ArrayList<Line> lines = LineFinder.detectLines(Arrays.asList(edgePoints));
		
//		System.out.println(lines);
		

	}

}
