/**
 * 
 */
package it.unipv.cv.utils;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Davide Pio Lotito - Aiman Al Masoud
 * Computer Vision Project - 2022 - UniPV
 *
 */
public class CommandLine {
	
	/**
	 * The filename of the user's image
	 */
	public String name;
		
	/**
	 * The example image for the lazy user
	 */
	public String EXAMPLE = "./images/input/beautifulSquare.png";
	
	/**
	 * path where to save image, in the same directory by default
	 */
	public String path = "./output";
	
	/**
	 * flag which says if the user want to save the image
	 */
	public Boolean isOutput = false;
	
	
	public CommandLine(String[] args) {
		checkMain(args);
	}

	/**
	 * Command lines operations
	 * @param args
	 */
	private void checkMain(String[] args){
		if(args.length==0) {
			System.err.println("\nbe used: java -jar <<program>> file-name-image.png [--output]");
			System.exit(1);
		} if(args[0].equals("--help")) {
			System.out.println("\t\t\t\t------");
			System.out.println("It is a university project, for the examination of computer vision.");
			System.out.println("Authors: Davide Pio Lotito - Aiman Al Masoud\n");
			System.out.println("Overview: This program aims to detect square." + "Using Sobel, the Hough"
					+ " Transform and some geometry allows you to locate the squares"
					+ " in the image.\n");
			System.out.println("Parameters: "+"\n 1)It takes as a parameter the name of an image, both color and"
					+ " grayscale images are fine. If If you don't have one, type \"example\" and you will"
					+ " see how the program works with a test image." + "\n 2)It optionally takes the path" 
					+ " where to save the final image. If the path is empty the dafault is the current directory,"
					+ " otherwise it uses the path inserted saving as \"png\". ");
			Scanner scanner = new Scanner(System.in);
			boolean condition = true;
			while(condition) {
				System.out.println("\t\t\t\t****");
				System.out.println("\nNow you can enter the path/name of the image...");
				//get the next line of user input.
				String inserted = scanner.next();
				if(inserted.equals("example")) {
					name = EXAMPLE;
					break;
				}
				if(inserted.equals("")) {
					System.err.println("\nbe used: java -jar <<program>> file-name-image.png");
					System.exit(1);
				}
				name = inserted;
				scanner.close();
				condition = false;
			}
		} else if (Arrays.asList(args).contains("--output")) {
			isOutput = true;
			path = args[Arrays.asList(args).indexOf("--output")+1];
			name = args[0];
		} else {
			name = args[0];
		}
	}
}
