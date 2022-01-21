package it.unipv.cv.line_detection;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.REUtil;

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

	private static final double SIMILARtheta = 1;
	private static final int SIMILARrho = 30;
	private static final double DIFFERENCEslope = 2.0;

	/**
	 * Shortest distance of line from origin.
	 */
	public int rho;

	/**
	 * Angle from x-axis to line in the direction of rho.
	 */
	 public double theta;

	/**
	 * Slope of the line.
	 */
	public double slope;

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
		/**
		 * not equals if the difference of their rho is bigger than DIFFERENCErho
		 */
//		if((Math.abs(((Line)otherLine).rho - this.rho) > DIFFERENCErho)) {
//			return false;
//		}
		if(((Line)otherLine).rho != this.rho) {
			return false;
		}

		//		if(Math.abs(((Line)otherLine).theta - this.theta) > 0.1) {
		//			return false;
		//		}
		if(((Line)otherLine).theta != this.theta) {
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

	/**
	 * Check if two lines are parallels
	 * @param line2
	 * @return
	 */
	public boolean areParallel(Line line2) {
		/**
		 * if the difference of their slope is minor than DIFFERENCEslope
		 */
		return ((Math.abs(this.slope - line2.slope))<DIFFERENCEslope);
	}
	
	public boolean isVertical() {
		return (Math.abs(this.slope)>50);
	}
	
	public boolean isHorizontal() {
		return (Math.abs(this.slope)<0.3);
	}
	
	/**
	 * Check if two lines are "similar"
	 * 
	 * @param line
	 * @return
	 */
	public boolean similarLines(Line line) {
		/**
		 * True if the two lines are too much "similiar"
		 */
//		if((this.rho*this.theta) * (line.rho*line.theta)<0) {
//			return false;
//		}
//		if(this.isVertical() && line.isHorizontal()) {
//			return false;
//		}
//		if(this.isHorizontal() && line.isVertical()) {
//			return false;
//		}
//		only for parallel lines
//		if(((Math.abs((line).rho - this.rho)) >= SIMILARrho)) {
//			return false;
//		}
		if((Math.abs((line).rho - (this.rho)) >= SIMILARrho) 
				|| ((Math.abs((line).theta - this.theta)) >= SIMILARtheta)) {
			return false;
		}
		return true;
	}

	/**
	 * Compute the intersection between two lines, but before does some checks
	 * @param otherLine
	 * @return
	 * @throws Exception 
	 */
	public Coordinate intersects(Line otherLine) throws Exception {
		
		if(otherLine.similarLines(this))
			throw new Exception("Almost coincident lines, all intersections!");

		if(otherLine.areParallel(this))
			throw new Exception("Parallel lines, no intersections!");

		double x =  (otherLine.yintercept - this.yintercept)/(this.slope - otherLine.slope);
		double y =  evaluate(x);
		return new Coordinate( (int)Math.round(x), (int)Math.round(y) );
	}

	/**
	 * Draw this line on an image and return a new image with the line drawn.
	 * @param b
	 * @return
	 */
	public BufferedImage draw(BufferedImage b) {

		b = Utility.copyImage(b);
		Graphics2D g = (Graphics2D) b.getGraphics();
		//the color of the lines
		g.setColor(Color.red);
		int lowerBoundX = Utility.pixelToCoord(new Coordinate(0, 0) , b.getWidth(), b.getHeight()).X;
		int upperBoundX = Utility.pixelToCoord(new Coordinate( b.getWidth(), b.getHeight()) , b.getWidth(), b.getHeight()).X;		
		int lowerBoundY = (int)evaluate(lowerBoundX);
		int upperBoundY = (int)evaluate(upperBoundX);
		Coordinate c1 = Utility.coordToPixel(new Coordinate(lowerBoundX, lowerBoundY), b.getWidth(), b.getHeight());
		Coordinate c2 = Utility.coordToPixel(new Coordinate(upperBoundX, upperBoundY), b.getWidth(), b.getHeight());
		g.drawLine(c1.X, c1.Y, c2.X, c2.Y);
		g.dispose();

		return b;
	}

	/**
	 * Only to test! REMOVE IT BEFORE THE DELIVERY
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Line line = new Line(311, 1.553);
		Line line2 = new Line(312, 1.553);
		Line line3 = new Line(-313, -1.588);
		System.out.println(line.toString());
		System.out.println(line2.toString());
		System.out.println(line3.toString());
		System.out.println("SIMILAR:");
		System.out.println(line.similarLines(line2));
		System.out.println(line2.similarLines(line3));
	}	
}
