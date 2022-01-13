package it.unipv.cv.utils;

import java.util.HashMap;

import it.unipv.cv.line_detection.Line;

/**
 * 
 * Keeps a count of added objects. Object1 is considered equal
 * to Object2 based on their hashCode/equals methods.
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class Counter extends HashMap<Object, Integer>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void add(Object object) {
		Integer value = get(object);
		
		if(value==null) {
			put(object, 1);
		}else {
			put(object, value+1);
		}
	}
	
	
	public static void main(String[] args) {
		Line line = new Line(1,2);
		Line lineCopy = new Line(1,2);
		Line differentLine = new Line(3,3);

		Counter counter = new Counter();
		counter.add(line);
		counter.add(lineCopy);
		counter.add(differentLine);
		System.out.println(counter);
		
	}
	
	

}
