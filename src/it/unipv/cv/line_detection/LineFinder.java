package it.unipv.cv.line_detection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import it.unipv.cv.utils.Coordinate;



public class LineFinder {

	
	
	public static ArrayList<Line> detectLines(List<Coordinate> edgePoints){
		
		
		ArrayList<Line> lines = new ArrayList<Line>();
		
		//get 'all possible' lines passing through the edge-points
		for(Coordinate point : edgePoints) {
			System.out.println(point);
			System.out.println(Line.getLinesFor(point));
			lines.addAll(Line.getLinesFor(point));
		}
		
		// 1) Group lines by slope. 
		// 2) For each slope-group, try to find groups of lines with similar y-intercepts
		
		
		
		lines.sort(new Comparator<Line>() {

			@Override
			public int compare(Line arg0, Line arg1) {
				return (int) ((arg0.slope - arg1.slope)*1000);
			}
			
		});
		
//		
//		lines.sort(new Comparator<Line>() {
//
//			@Override
//			public int compare(Line arg0, Line arg1) {
//				return (int) ((arg0.yintercept - arg1.yintercept)*1000);
//			}
//			
//		});
		
		System.out.println(lines.size());
		for (Line line : lines) {
			System.out.println(line);
		}
//		System.out.println(lines);
		
		return null;
	}
	
	
	
	
	

}
