package it.unipv.cv.line_detection;

import java.awt.Color;
import java.awt.Graphics2D;
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
	public double slope;

	/**
	 * y-intercept of the line.
	 */
	public double yintercept;
	
	/**
	 * Number of votes received by this line.
	 * Mutable attribute, not used in equals nor hashCode 
	 */
	private int numVotes;
	
	
	public Line(int rho, double theta) {
		
		numVotes = 0;
		
		this.rho = rho;
		this.theta  = theta;
		
		//https://aishack.in/tutorials/converting-lines-normal-slopeintercept-form/
		slope = - (Math.cos(theta)/Math.sin(theta));
		yintercept = rho*(1/Math.sin(theta));
		
		if (theta == 0 | theta > 3.1) {
			theta = 0.01;
			slope = - (Math.cos(theta)/Math.sin(theta));
			yintercept = rho*(1/Math.sin(theta));
		}
	
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
		double rho = (p.X*Math.cos(theta) + p.Y*Math.sin(theta));
		int rho2 = (int) ((Math.round(rho)));
		return new Line(rho2, theta);
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
	 * Evaluates y for a given x. (In the Cartesian space).
	 * @param x
	 * @return
	 */
	public double evaluate(double x) {
		return (slope * x) + yintercept;
	}
	
	
	/**
	 * Draw this line on an image and return a copy.
	 * @param b
	 * @return
	 */
	public BufferedImage draw(BufferedImage b) {
	    
		b = Utility.copyImage(b);
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setColor(Color.red);
		int lowerBoundX = Utility.pixelToCoord(new Coordinate(0, 0) , b.getWidth(), b.getHeight()).X;
		int upperBoundX =  Utility.pixelToCoord(new Coordinate( b.getWidth(), b.getHeight()) , b.getWidth(), b.getHeight()).X;		
		int lowerBoundY = (int)evaluate(lowerBoundX);
		int upperBoundY = (int)evaluate(upperBoundX);
		Coordinate c1 = Utility.coordToPixel(new Coordinate(lowerBoundX, lowerBoundY), b.getWidth(), b.getHeight());
		Coordinate c2 = Utility.coordToPixel(new Coordinate(upperBoundX, upperBoundY), b.getWidth(), b.getHeight());
        g.drawLine(c1.X, c1.Y, c2.X, c2.Y);
        g.dispose();
			
		return b;
	}
	
	
	
	
	

}
