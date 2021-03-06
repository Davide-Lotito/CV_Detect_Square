package it.unipv.cv.line_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import it.unipv.cv.edge_detection.EdgeDetector;
import it.unipv.cv.edge_detection.SobelFilter;
import it.unipv.cv.edge_detection.Threshold;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.Counter;
import it.unipv.cv.utils.DisplayImage;
import it.unipv.cv.utils.Utility;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Useful to find line
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 * //https://en.wikipedia.org/wiki/Hough_transform

 */
public class LineFinder {

	/**
	 * Minimum number of votes (edge-points) that a line needs to be considered a real edge-line.
	 */
	public int MIN_VOTES;

	/**
	 * Used to log messages of this class.
	 */
	private Logger logger;

	/**
	 * Lines
	 */
	public static ArrayList<Line> lines;


	public ArrayList<Line> detectLines(BufferedImage img){

		logger = Logger.getLogger("CVlogger");

		/**
		 * Min votes for the Hough Transform
		 */
		MIN_VOTES = (int)(0.3*Math.min(img.getWidth(), img.getHeight()));

		BufferedImage grayScale = Utility.toGrayScale(img);

		EdgeDetector edgeDetector = new SobelFilter();
		BufferedImage sobelImage = edgeDetector.filtering(grayScale);

		Threshold threshold = new Threshold();
		ArrayList<Coordinate> edgePoints = threshold.thresholding(sobelImage);

		/**
		 * Get 'all possible' lines passing through the edge-points
		 */
		ArrayList<Line> lines = new ArrayList<Line>();
		for(Coordinate point : edgePoints) {
			lines.addAll(getLinesFor(point));
		}
		logger.log(Level.INFO, "DONE: Obtained all possible lines.");

		/**
		 * Count votes for each line
		 * BOTTLE-NECK
		 */
		ArrayList<Line> results = new ArrayList<Line>();		
		Counter counter = new Counter();
		ProgressBarBuilder pbb = new ProgressBarBuilder()
			    .setStyle(ProgressBarStyle.ASCII)
			    .setTaskName("Finding square")
			    .setUpdateIntervalMillis(100)
			    .showSpeed();
		for(Line line : ProgressBar.wrap(lines, pbb)) {
			counter.add(line);
		}
		logger.log(Level.INFO, "DONE: Obtained all count votes.");
		
		/**
		 * Threshold lines by votes
		 */
		for(Object line : counter.keySet()) {
			if(counter.get(line) < MIN_VOTES) {
				continue;
			}
			results.add((Line)line);
		}
		logger.log(Level.INFO, "DONE: Threshold lines by votes.");
		
		/**
		 * Remove similar lines
		 */
		logger.log(Level.WARNING, "These lines are similar:");	
		ArrayList<Line> resultsNew = new ArrayList<Line>();
		for(Line line : results) {
			boolean addMe =true;
			for(Line alreadyThereLine : resultsNew) {
				if(alreadyThereLine.similarLines(line)) {
					//don't add 'coord'
					addMe = false;
					logger.log(Level.WARNING,"not added "+line.toString());
				}
			}
			if(addMe) {
				resultsNew.add(line);
			}
		}

		logger.log(Level.INFO, "CORRECT LINES");
		int COUNTER = 1;
		for(Line line : resultsNew) {
			logger.log(Level.INFO, ("["+COUNTER+"] "+line.toString()));
			COUNTER++;
		}
		
		LineFinder.lines = resultsNew;
		return resultsNew;
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
		
		Integer[] thetas = Utility.generateThetas(1,-90,90);

		for(int theta : thetas){
			Line line = getLineFor(p, Math.toRadians(theta));
			lines.add(line);
		}
		return lines;
	}

	public Boolean contains(Line lineToCheck) {
		for(Line line: lines) {
			if(line.similarLines(lineToCheck)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Draw all the lines
	 * @param image
	 * @param lines
	 * @return
	 */
	public static BufferedImage drawLines(BufferedImage image) {
		for(int i=0; i<lines.size(); i++) {
			Line line1 = lines.get(i);
			image = line1.draw(image);
		}
		return image;
	}

	/**
	 * Only to test! REMOVE IT BEFORE THE DELIVERY
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage image = Utility.read("./images/input/beautifulSquare.png");
		//detect the lines on the image
		LineFinder lineFinder = new LineFinder();
		lineFinder.detectLines(image);

		//draw each line
		image = drawLines(image);
	
		new DisplayImage().displayOneImage(image, "detected lines");
	}
}