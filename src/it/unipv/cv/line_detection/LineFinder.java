package it.unipv.cv.line_detection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


import it.unipv.cv.edge_detection.EdgeDetector;
import it.unipv.cv.edge_detection.SobelFilter;
import it.unipv.cv.edge_detection.Threshold;
import it.unipv.cv.utils.Coordinate;
import it.unipv.cv.utils.Utility;

/**
 * Useful to find line
 * 
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class LineFinder {



	public static final int MIN_VOTES = 300;


	public static ArrayList<Line> detectLines(BufferedImage img){


		//get the edge-points as coordinates on a CARTESIAN plane. 
		BufferedImage grey = Utility.toGrayScale(img, img.getWidth(), img.getHeight());
		EdgeDetector edgeDetector = new SobelFilter();
		BufferedImage i = edgeDetector.filtering(grey);
		Threshold threshold = new Threshold();
		ArrayList<Coordinate> edgePoints = threshold.thresholding(i);


		//get 'all possible' lines passing through the edge-points
		ArrayList<Line> lines = new ArrayList<Line>();
		for(Coordinate point : edgePoints) {
			lines.addAll(Line.getLinesFor(point));
		}

		// get the number of votes for each line
		Set<Line> linesSet = new HashSet<Line>(lines);
		HashMap<Line, Line> voteAccumulator = new HashMap<Line, Line>();

		for (Line line : linesSet) {
			voteAccumulator.put(line, line);
		}

		for(Line line : lines) {
			voteAccumulator.get(line).addVote();
		}
		
		// filter out lines with too few votes
		ArrayList<Line> result = new ArrayList<Line>();
		for(Line line : voteAccumulator.values()) {
			if(line.getNumVotes()<MIN_VOTES) {
				continue;
			}
			/*---------*/
			System.out.println(line);
			result.add(line);
		}

		return result;
	}





}
