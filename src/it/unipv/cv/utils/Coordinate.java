package it.unipv.cv.utils;

import java.text.MessageFormat;

/**
 * Useful Class for the coordinate
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class Coordinate {
	
	public final int X;
	public final int Y;
	
	public Coordinate(int x, int y) {
		X = x;
		Y = y;
	}	
	
	@Override
		public String toString() {
			return MessageFormat.format("Coordinate({0},{1})", X, Y);
	}
	
	public double distance(Coordinate coord2) {
		return Math.sqrt(Math.pow(X - coord2.X, 2) + Math.pow(Y - coord2.Y, 2));
	}
}
