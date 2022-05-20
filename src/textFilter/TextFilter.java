package textFilter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

public class TextFilter {

	public static void main(String[] args) {
		try {
			
			// Output image file name
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter file name of output image: ");
			String outputFileName = scanner.next();
			
			File input = new File("C:\\Users\\Admin\\eclipse-workspace\\textFilter\\src\\textFilter\\endgame.jpg");
			File output = new File("C:\\Users\\Admin\\eclipse-workspace\\textFilter\\src\\textFilter\\" + outputFileName + ".jpg");
			
			// Input image
			ImageInputStream imgInputStream = ImageIO.createImageInputStream(input);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(imgInputStream);
			
			ImageReader reader = iterator.next();												
			String imageFormat = reader.getFormatName();
			
			BufferedImage image = ImageIO.read(imgInputStream);
			int width = image.getWidth();
			int height = image.getHeight();
			Hashtable<Integer, Color>[] finalRGB = new Hashtable[height];		// Store Image Colour Values & their coords

			// Read Shrek Script
	        FileReader fileReader = new FileReader("C:\\Users\\Admin\\eclipse-workspace\\textFilter\\src\\textFilter\\shrek.txt");
	        ArrayList<Character> text = new ArrayList<>();
	        int x;
	        while ((x = fileReader.read()) != -1) {
	        	if (Character.isLetter((char)x) || ((char)x == '.') || ((char)x == '!') || ((char)x == '?') || ((char)x == '-')) {
		        	text.add((char)x);
	        	}
	        }

			// Get Colour Values from Image
			for (int i = 0; i < height; i++) {
				finalRGB[i] = new Hashtable<Integer, Color>();
				for (int j = 0; j < width; j++) {
					int rgbRed = new Color(image.getRGB(j, i)).getRed();
					int rgbGreen = new Color(image.getRGB(j, i)).getGreen();
					int rgbBlue = new Color(image.getRGB(j, i)).getBlue();
					
					finalRGB[i].put(j, new Color(rgbRed, rgbGreen, rgbBlue));
				}
			}

			// Set Background to Black
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					image.setRGB(j, i, Color.BLACK.getRGB());
				}
			}
			
			// Read Text onto Output Image		    
			Font font = new Font("Arial", Font.PLAIN, 12);
			int counter = 0;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					// Leave space between characters
					if (j + (j * 8) < width && i + (i * 8) < height) {

						// Check if end of script/current character is a space
						if (counter == text.size()) {
							counter = 0;
						}
						while (text.get(counter) == ' ') {
							counter++;
							if (counter == text.size()) {
								counter = 0;
							}
						}

						Color currentColour = new Color(finalRGB[i + (i * 8)].get(j + (j * 8)).getRed(),
								finalRGB[i + (i * 8)].get(j + (j * 8)).getGreen(), finalRGB[i + (i * 8)].get(j + (j * 8)).getBlue());
						if (currentColour.getRed() == currentColour.getBlue() && currentColour.getBlue() == currentColour.getGreen()) {
							if (currentColour.getBlue() < 100) {
								currentColour = Color.DARK_GRAY;
							}

						}
						
						Graphics g = image.getGraphics();
						g.setFont(font);
						g.setColor(currentColour);
	
						g.drawString(Character.toString(text.get(counter)), j + (j * 8), i + (i * 8));
						image.setRGB(j + (j * 8), i + (i * 8), Color.BLACK.getRGB());
						counter++;
					}
				}
			}
			
			ImageIO.write(image, imageFormat, output);
			System.out.println("Completed.");
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
