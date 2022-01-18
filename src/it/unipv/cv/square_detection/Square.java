package it.unipv.cv.square_detection;

import it.unipv.cv.utils.Coordinate;

/**
 * A class for the square
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class Square {
	/**
	 * the TWO DIAGONAL POINTS of the square to identify it
	 */
	private Coordinate upperRight;
	private Coordinate bottomLeft;
	
	public Square(Coordinate upperRight, Coordinate bottomLeft) {
		super();
		this.upperRight = upperRight;
		this.bottomLeft = bottomLeft;
	}
	
	public double getEdge() {
		return bottomLeft.distance(upperRight);
	}
	

}
