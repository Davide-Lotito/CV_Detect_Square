package it.unipv.cv.line_detection;

import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.ArrayList;


import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.Utility;

//https://en.wikipedia.org/wiki/Hough_transform

/**
 * 
 * Rapresents the line
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */

public class Line{

	/**
	 * Shortest distance of line from origin.
	 */
	final public int rho;

	/**
	 * Angle from x-axis to line in the direction of rho.
	 */
	final public double theta;
	
	
	/**
	 * Slope of the line.
	 */
	final public double slope;

	/**
	 * y-intercept of the line.
	 */
	final public double yintercept;
	
	/**
	 * Number of votes received by this line.
	 * Mutable attribute, not used in equals nor hashCode 
	 */
	private int numVotes;
	
	
	public Line(int rho, double theta) {
		this.rho = rho;
		this.theta  = theta;
		
		//https://aishack.in/tutorials/converting-lines-normal-slopeintercept-form/
		slope = -Math.cos(theta)/Math.sin(theta);
		yintercept = rho*(1/Math.sin(theta));
		
		numVotes = 0;
	}

	/**
	 * 
	 * Returns a Line that passes through Point p, with chosen value of parameter theta, 
	 * and computed value of parameter rho.
	 * 
	 * Uses this line-parametrization to calculate rho:
	 * rho = x*cos(theta) + y*sin(theta)
	 * 
	 * @param point
	 * @param theta
	 * @return
	 */
	public static Line getLineFor(Coordinate p, double theta) {
		int rho = (int) Math.round((p.X*Math.cos(theta) + p.Y*Math.sin(theta)));
		return new Line(rho, theta);
	}
	
	/**
	 * Like getLineFor but gets a bunch of lines for a predefined set of thetas 
	 * @param p
	 * @return
	 */
	public static ArrayList<Line> getLinesFor(Coordinate p){
		ArrayList<Line> lines = new ArrayList<Line>();
		
		int step = 1;//distance between two adjacent thetas
		Integer[] thetas = Utility.generateThetas(step);
		
		
		for(int theta : thetas){
			Line line = getLineFor(p, Math.toRadians(theta));
			
			//discard lines with negative rho
			if(line.rho<0) {
				continue;
			}
			lines.add( line);
		}
		
		return lines;
	}
	
	
	@Override
	public String toString() {
		return MessageFormat.format("Line(theta={0}, rho={1}, slope={2}, y-intercept={3}, numVotes={4})", theta, rho, slope, yintercept, numVotes);
	}
	
	@Override
	public boolean equals(Object otherLine) {
		
		if(((Line)otherLine).theta != this.theta) {
			return false;
		}
		
		if(((Line)otherLine).rho != this.rho) {
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public int hashCode() {
		return (int)(rho*theta);
	}
	
	/**
	 * Increment the number of votes of this image by one.
	 */
	public void addVote() {
		numVotes++;
	}
	
	/**
	 * Get the number of votes.
	 * @return
	 */
	public int getNumVotes() {
		return numVotes;
	}
	
	
	/**
	 * Get a bunch of fresh coordinates from this line.
	 * @param upperBound
	 * @param lowerBound
	 * @param step
	 * @return
	 */
	public ArrayList<Coordinate> sample(double upperBound, double lowerBound, double step) {
		
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>(); 
		
		for(double x=lowerBound; x<upperBound; x+=step) {
			coordinates.add( new Coordinate((int)x, (int)(slope*x + yintercept)));
		}
		return coordinates;
	}
	
	/**
	 * Draw this line on an image and return a copy.
	 * @param b
	 * @return
	 */
	public BufferedImage draw(BufferedImage b) {
	    
		b = Utility.copyImage(b);
		
		int lowerBound = Utility.pixelToCoord(new Coordinate(0, 0) , b.getWidth(), b.getHeight()).X;
		int upperBound =  Utility.pixelToCoord(new Coordinate( b.getWidth(), b.getHeight()) , b.getWidth(), b.getHeight()).X;
		
		ArrayList<Coordinate> coordinates = sample(upperBound, lowerBound , 1);
		
		
		//convert  coordinates to pixels
		ArrayList<Coordinate> buffer = new ArrayList<Coordinate>(); 
		for(Coordinate c : coordinates) {
			buffer.add(Utility.coordToPixel(c, b.getWidth(), b.getHeight()));
		}
		
		coordinates = buffer;
		
		for(Coordinate coordinate : coordinates ) {
			try {
				b.setRGB(coordinate.X, coordinate.Y, 0xffff0000);
			}catch (Exception e) {}
		}
			
		return b;
	}
	
	
	
	
	

}
