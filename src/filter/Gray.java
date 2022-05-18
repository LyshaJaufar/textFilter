package filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

public class Gray {

	public static void main(String[] args) {
		try {
			File input = new File("C:\\Users\\Admin\\eclipse-workspace\\filter\\src\\filter\\ratioedAayan.jpg");
			File temp = new File("C:\\Users\\Admin\\eclipse-workspace\\filter\\src\\filter\\temp.jpg");
			File output = new File("C:\\Users\\Admin\\eclipse-workspace\\filter\\src\\filter\\Output1.jpg");
			
			ImageInputStream imgInputStream = ImageIO.createImageInputStream(input);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(imgInputStream);
			
			ImageReader reader = iterator.next();												
			String imageFormat = reader.getFormatName();
			
			BufferedImage image = ImageIO.read(imgInputStream);
			int width = image.getWidth();
			int height = image.getHeight();
			
			ArrayList<Color> finalRGB = new ArrayList<Color>();
			int count = 0;
			int spacing = 9;

		    //Graph for sobel operator
		    int[][] graph = { 
		        {-1, 0, 1},
		        {-2, 0, 2},
		        {-1, 0, 1}
		    };
		    
			Font font = new Font("Arial", Font.PLAIN, 12);
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					float gxBlue = 0, gxGreen = 0, gxRed = 0;
					float gyBlue = 0, gyGreen = 0, gyRed = 0;
					float finalRed = 0, finalBlue = 0, finalGreen = 0;

					for (int i_offset = -1; i_offset <= 1; i_offset++) {
						for (int j_offset = -1; j_offset <= 1; j_offset++) {
		                    if (i + i_offset < 0 || i + i_offset > height - 1 || j + j_offset < 0 || j + j_offset > width - 1) {
		                        continue;
		                    }

		                    // Compute Gx for each colour value
		                    gxBlue += new Color(image.getRGB(j + j_offset, i + i_offset)).getRed() * graph[j_offset + 1][i_offset + 1];
		                    gxRed += new Color(image.getRGB(j + j_offset, i + i_offset)).getGreen() * graph[j_offset + 1][i_offset + 1];
		                    gxGreen += new Color(image.getRGB(j + j_offset, i + i_offset)).getBlue() * graph[j_offset + 1][i_offset + 1];
		                    
		                    // Compute Gy for each colour value
		                    gyBlue += new Color(image.getRGB(j + j_offset, i + i_offset)).getRed() * graph[i_offset + 1][j_offset + 1];
		                    gyRed += new Color(image.getRGB(j + j_offset, i + i_offset)).getGreen() * graph[i_offset + 1][j_offset + 1];
		                    gyGreen += new Color(image.getRGB(j + j_offset, i + i_offset)).getBlue() * graph[i_offset + 1][j_offset + 1];
						}
					}
					if (gxRed == 0 && gxGreen == 0 && gxBlue == 0 && gyRed == 0 && gyBlue == 0 && gyGreen == 0) {
						finalRed = 255;																													
						finalBlue = 255;
						finalGreen = 255;

					} else {
						// Compute new total value of each pixel
						finalRed = (float)Math.floor(Math.sqrt((gxRed * gxRed) + (gyRed * gyRed)));
						finalBlue = (float)Math.floor(Math.sqrt((gxBlue * gxBlue) + (gyBlue * gyBlue)));
						finalGreen = (float)Math.floor(Math.sqrt((gxGreen * gxGreen) + (gyGreen * gyGreen)));
					}
						

					
					// Ensure the new value is 0 - 255 & set each colour values of a pixel to the result 
					int rgbRed = (int)((finalRed > 255) ? 255 : finalRed);
					int rgbGreen = (int)((finalGreen > 255) ? 255 : finalGreen);
					int rgbBlue = (int)((finalBlue > 255) ? 255 : finalBlue);


					finalRGB.add(new Color(rgbRed, rgbGreen, rgbBlue));
					count++;
				}
			}
			count = 0;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					
					
					if (spacing % 50 == 0 && finalRGB.get(count).getRed() == 255 && finalRGB.get(count).getGreen() == 255 && finalRGB.get(count).getBlue() == 255) {
						Graphics g = image.getGraphics();
						g.setFont(font);
						g.setColor(Color.GREEN);
						g.drawString("h", j, i);
					} 
					if (finalRGB.get(count).getRed() == 255 && finalRGB.get(count).getGreen() == 255 && finalRGB.get(count).getBlue() == 255) {
						image.setRGB(j, i, Color.BLACK.getRGB());
					}
					else {
						image.setRGB(j, i, finalRGB.get(count).getRGB());
					}

					
					
					count++;
					spacing++;
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
