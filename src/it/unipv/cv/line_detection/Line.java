package it.unipv.cv.line_detection;

import java.text.MessageFormat;
import java.util.ArrayList;

import it.unipv.cv.utils.Coordinate;


//https://en.wikipedia.org/wiki/Hough_transform


public class Line{

	/**
	 * Shortest distance of line from origin.
	 */
	final public double rho;

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

	
	public Line(double rho, double theta) {
		this.rho = rho;
		this.theta  = theta;
		
		//https://aishack.in/tutorials/converting-lines-normal-slopeintercept-form/
		this.slope = -Math.cos(theta)/Math.sin(theta);
		this.yintercept = rho*(1/Math.sin(theta));
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
		double rho = p.X*Math.cos(theta) + p.Y*Math.sin(theta);
		return new Line(rho, theta);
	}
	
	/**
	 * Like getLineFor but gets a bunch of lines for a predefined set of thetas 
	 * @param p
	 * @return
	 */
	public static ArrayList<Line> getLinesFor(Coordinate p){
		ArrayList<Line> lines = new ArrayList<Line>();
		
		Integer[] thetas = new Integer[]{0, 15, 30, 45, 60, 75, 90, 105, 120, 135, 150, 165, 180, 195, 210, 225, 240, 255, 270, 285, 300, 315, 330, 345};
		
		for(int theta : thetas){
			lines.add(getLineFor(p, Math.toRadians(theta) ));
		}
		return lines;
	}
	
	
	@Override
	public String toString() {
		return MessageFormat.format("Line(slope={0}, y-intercept={1})", slope, yintercept);
	}

	
	
	

}
