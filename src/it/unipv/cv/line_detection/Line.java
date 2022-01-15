package it.unipv.cv.line_detection;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.Utility;

/**
 * 
 * Represents a straight line.
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
		
	
	public Line(int rho, double theta) {
		
		this.rho = rho;
		this.theta  = theta;
		
		if (theta == 0 || theta > 3.1) {
			theta = 0.01;
		}
		
		//https://aishack.in/tutorials/converting-lines-normal-slopeintercept-form/
		slope = - (Math.cos(theta)/Math.sin(theta));
		yintercept = rho*(1/Math.sin(theta));
			
	}

	@Override
	public String toString() {
		return MessageFormat.format("Line(theta={0}, rho={1}, slope={2}, y-intercept={3})", theta, rho, slope, yintercept);
	}
	
	@Override
	public boolean equals(Object otherLine) {
		
		if(((Line)otherLine).theta != this.theta) {
			return false;
		}
		
//		if(Math.abs(((Line)otherLine).theta - this.theta) > 0.1) {
//			return false;
//		}
		
		
//		if(((Line)otherLine).rho != this.rho) {
//			return false;
//		}
		
		if(Math.abs(((Line)otherLine).rho - this.rho) > 4) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return (int)(rho+theta);
	}
	
	/**
	 * Evaluates y for a given x. (In the Cartesian space).
	 * @param x
	 * @return
	 */
	public double evaluate(double x) {
		return (slope * x) + yintercept;
	}
	
	
	public Coordinate intersects(Line otherLine) {
		
		if(otherLine.equals(this)) {
			throw new IllegalArgumentException("Ma sei, scemo? "+this+" "+otherLine+" rette coincidenti");
		}
		
		double x =  (otherLine.yintercept - this.yintercept)/(this.slope - otherLine.slope);
		double y =   evaluate(x);
		return new Coordinate( (int)Math.round(x), (int)Math.round(y) );
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
	
	
	
//	public static void main(String[] args) {
//		Line line = new Line(3, 0.5);
//		Line line2 = new Line(4, 1.2);
//		System.out.println(line);
//		System.out.println(line2);
//		System.out.println(line.intersects(line2));
//	}
//	
	
}
