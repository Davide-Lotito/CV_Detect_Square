package it.unipv.cv.line_detection;

import java.util.ArrayList;

import it.unipv.cv.utils.Coordinate;


//https://en.wikipedia.org/wiki/Hough_transform


public class Line {

	/**
	 * Shortest distance of line from origin.
	 */
	final public double rho;

	/**
	 * Angle from x-axis to line in the direction of rho.
	 */
	final public double theta;
	
	
	public Line(double rho, double theta) {
		this.rho = rho;
		this.theta  = theta;
	}

	/**
	 * 
	 * Returns a Line that passes through Point p, with chosen value of parameter theta, 
	 * and computed value of parameter rho.
	 * 
	 * Uses this line-parametrization :
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
		
		for(int theta : new Integer[]{15, 30, 45, 60, 75}) {
			lines.add(getLineFor(p, Math.toRadians(theta) ));
		}
		return lines;
	}
	
	
	//https://aishack.in/tutorials/converting-lines-normal-slopeintercept-form/
	@Override
	public String toString() {
		return "Line( slope="+(-Math.cos(theta)/Math.sin(theta))+", y-intercept="+rho*(1/Math.sin(theta))+" )";
	}
	
	
	
	
	
	
	

}
