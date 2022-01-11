package it.unipv.cv.line_detection;

import java.util.ArrayList;
import java.util.List;

import it.unipv.cv.utils.Coordinate;



public class LineFinder {

	
	
	public static ArrayList<Line> detectLines(List<Coordinate> edgePoints){
		
		
		ArrayList<Line> lines = new ArrayList<Line>();
		
		//get 'all possible' lines passing through the edge-points
		for(Coordinate point : edgePoints) {
			System.out.println(point);
			System.out.println(Line.getLinesFor(point));
		}
		
		// find lines with same theta and very similar rho
		
		
		return null;
	}
	
	
	
	
	

}
