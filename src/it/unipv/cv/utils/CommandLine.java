/**
 * 
 */
package it.unipv.cv.utils;

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
	 * The path where save output images of the user
	 */
	public String pathname;
	
	/**
	 * The example image for the lazy user
	 */
	public String EXAMPLE = "./images/input/beautifulSquare.png";
	
	
	public CommandLine(String[] args) {
		checkMain(args);
	}

	/**
	 * Command lines operations
	 * @param args
	 */
	private void checkMain(String[] args){
		if(args.length==0) {
			System.err.println("\nbe used: java <<program>> file-name-image.png");
			System.exit(1);
		} if(args[0].equals("--help")) {
			System.out.println("\t\t\t\t------");
			System.out.println("It is a university project, for the examination of computer vision.");
			System.out.println("Authors: Davide Pio Lotito - Aiman Al Masoud\n");
			System.out.println("Overview: This program aims to detect squares." + "Using Sobel, the Hough"
					+ " Transform and some geometry allows you to locate the squares"
					+ " in the image.\n");
			System.out.println("Parameter: It takes as a parameter the name of an image, both color and"
					+ " grayscale images are fine. If If you don't have one, type \"example\" and you will"
					+ " see how the program works with a test image.");
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
					System.err.println("\nbe used: java <<program>> file-name-image.png");
					System.exit(1);
				}
				name = inserted;
				scanner.close();
				condition = false;
			}
		} else {
			name = args[0];
		}
	}
}
