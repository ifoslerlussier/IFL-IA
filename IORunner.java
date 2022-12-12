import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;

import javax.imageio.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.awt.event.MouseEvent;




public class IORunner extends JPanel{
	
	/*int width = 640;
	int height = 654;
	
	ImageModifier img = null;
	*/
	BufferedImage imagehopefully = null;
	int mouseX = 0;
	int mouseY = 0;
	Point mousePos;
	
	public double tileWidth;
	public double tileHeight;
	
	public void paint(Graphics g) {
		Image img = takeInImage();
		
		g.drawImage(img, 20, 20, this);
		/*final Point*/ mousePos = this.getMousePosition();
		if (mousePos != null) {
		  /*final int*/ mouseX = mousePos.x;
		  /*final int*/ mouseY = mousePos.y;
		  
		}
		System.out.println("x: "+mouseX+", y: "+mouseY);
	}
	public void repaint(Graphics g) {
		Image img = takeInImage();
		
		g.drawImage(img, 20, 20, this);
		/*final Point*/ mousePos = this.getMousePosition();
		if (mousePos != null) {
		  /*final int*/ mouseX = mousePos.x;
		  /*final int*/ mouseY = mousePos.y;
		  
		}
		System.out.println("x: "+mouseX+", y: "+mouseY);
	}
	
	
	private Image takeInImage() {
		
		int width = 640;
		int height = 654;
		
		BufferedImage img = imagehopefully;
		
		/*
		///Users/ifoslerlussier/eclipse-workspace/IsaacFoslerLussierIA/src/ringpixelate2.png
		try {
			File input_file = new File("ringpixelate2.png");
			
			
			img = new BufferedImage(width,height,2);
			
			img =  ImageIO.read(input_file);
			System.out.println("Reading complete, in the takeInImage function.");
			Graphics g = img.getGraphics();
		}catch(IOException e){
			System.out.println("UH OH! Error!! here: "+e);
		}
		*/
		return img;
		
		
	}
	
	public Color getColorAtTile(int x, int y) {
		int xTile = (int)((x+0.5)*tileWidth);
		int yTile = (int)((y-0.5)*tileHeight);
		
		
		Color c = new Color(imagehopefully.getRGB(xTile, yTile));
		
		
		return c;
		
	}
	
	public void checkForFolder() throws IOException {
		//try {
		//	File folderfile = (IORunner.class.getResource("image-cache").getFile());
		System.out.println(IORunner.class.getResource("image-cache").getRef());
		///Users/ifoslerlussier/eclipse-workspace/IsaacFoslerLussierIA/src/image_cache
		//}catch(IOException e) {
			System.out.println("Error: ");
		//}
	}
	
	
	
	public IORunner() {
		
		int width = 640;
		int height = 654;
		
		//int tileWidth = 23;
		//int tileHeight = 24;
		tileWidth = 27;
		tileHeight = 27;
		
		
		int gWidth = (int)Math.floor((width/tileWidth));
		int gHeight = (int)Math.floor((height/tileHeight));
		int tWidth = (int)(gWidth * tileWidth);
		int tHeight = (int)(gHeight * tileHeight);
		
		System.out.println("gWidth: "+gWidth+"\ngHeight: "+gHeight+"\ntWidth: "+tWidth+"\ntHeight: "+tHeight+"");
		
		BufferedImage img = null;
		
		try {
			File input_file = new File("ring_pixelate2.png");
			
			
			img = new BufferedImage(width,height,2);
			
			URL fold = IORunner.class.getResource("image_cache");
			System.out.println("folder: "+fold);
			
			
			img =  ImageIO.read(IORunner.class.getResource("ringpixelate2.png"));
			
			//System.out.println("Path: "+IORunner.class.getResource("ringpixelate2.png").getPath());
			///Users/ifoslerlussier/eclipse-workspace/IsaacFoslerLussierIA/src/image_cache/ringpixelate2.png
			imagehopefully = img;
			int x = (int)((((int)(12))+0.5)*tileWidth)+1;
			System.out.println("Tile x: "+x+" Tile x, non-int: "+((((int)(12))+0.5)*tileWidth));
			
			int y = (int)((((int)(13))+0.5)*tileHeight)+1;
			System.out.println("Tile y: "+y+" Tile y, non-int: "+((((int)(13))+0.5)*tileWidth));
			
			/*
			int x = (int)((((int)(Math.random()*20))+0.5)*tileWidth);
			int y = (int)((((int)(Math.random()*20))+0.5)*tileHeight);
			*/
			//int y = (int)(Math.random()*20)+1;
			Color c = new Color(Math.abs(imagehopefully.getRGB(x,y)));
			int red = c.getRed();
			int green = c.getGreen();
			int blue = c.getBlue();
			
			System.out.println("RGB AT: ("+x+","+y+") is: "+imagehopefully.getRGB(x, y)+", or Red: "+c.getRed()+", Green: "+c.getGreen()+", Blue: "+c.getBlue());
			
			System.out.println("RGB AT: ("+x+","+y+") is: "+imagehopefully.getRGB(x, y)+", or Red: "+red+", Green: "+green+", Blue: "+blue);
			
			System.out.println("Tile x: "+(((x)/tileWidth)-0.5));
			System.out.println("Tile y: "+(((y)/tileWidth)-0.5));
			
			Color g = getColorAtTile(12, 13);
			System.out.println("Alternative method: RGB AT: ("+12+","+13+") is: "+g.getRGB()+", or Red: "+g.getRed()+", Green: "+g.getGreen()+", Blue: "+g.getBlue());
			
			System.out.println("Reading complete.");
		}catch(IOException e){
			System.out.println("UH Oh! Error!! (in the class) here: "+e);
		}
		
		
		//WRITE IMAGE
		try {
			//output file path
			File output_file = new File("ringpixelate2.png");
			System.out.println("Path: "+output_file.getPath());
			File output_directory = new File("image_cache"+output_file);
			ImageIO.write(img, "png", output_file);
			//System.out.println("Path: "+output_file.getPath());
			///Users/ifoslerlussier/eclipse-workspace/IsaacFoslerLussierIA/bin/image_cache/ringpixelate2.png
			
			System.out.println("Writing Complete.");
		}
		catch (IOException e) {
			System.out.println("waargh, error! here: "+e);
		}
		//*/
	}

	public static void main(String[] args) throws IOException {

		int width = 640;
		int height = 654;
		
		BufferedImage img = null;
		
		Scanner kb = new Scanner(System.in);
		
		System.out.println("Please enter Grid Tile Size: ");
		int tileSize = kb.nextInt();
		
		IORunner test = new IORunner();
		//test.checkForFolder();
		///Users/ifoslerlussier/eclipse-workspace/IsaacFoslerLussierIA/src/image_cache
		
		JFrame frame = new JFrame();
		frame.getContentPane().add(new IORunner());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,700);
		frame.setVisible(true);
		
		SiteScraperRunner scraper = new SiteScraperRunner("https://www.yarnspirations.com/yarn?prefn1=brand&prefv1=Red%20Heart");
		
		scraper.showResults();
		scraper.getSearchResults();
		
		
		//*/
		///Users/ifoslerlussier/Downloads/Filechooser Test copy
		/*
		try {
			File input_file = new File("ringpixelate2.png");
			
			//System.out.println(""+input_file.getClass().getResource("/images/ringpixelate.png"));
			
			img = new BufferedImage(width,height,2);
			
			img = (ImageModifier) ImageIO.read(getClass().getResource("/"));
			System.out.println("Reading complete.");
		}catch(IOException e){
			System.out.println("oh no! Error!! here: "+e);
		}
		//WRITE IMAGE
		try {
			//output file path
			File output_file = new File("C:/Users/ifoslerlussier/Downloads/FilechooserTestTwo/ringpixelate.png");
			ImageIO.write(img, "png", output_file);
			
			System.out.println("Writing Complete.");
		}
		catch (IOException e) {
			System.out.println("waargh, error! here: "+e);
		}
		*/
    }
	public void mouseClicked(final MouseEvent evt) {
		  final Point pos = evt.getPoint();
		  final int x = pos.x;
		  final int y = pos.y;
		}
	
}
