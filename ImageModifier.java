import java.awt.image.BufferedImage;


public class ImageModifier extends BufferedImage{

	public int gWidth;
	public int gHeight;
	public int tileWidth;
	public int tileHeight;
	
	public ImageModifier(int width, int height, int imageType) {
		super(width, height, imageType);
		gWidth = 23;
		gHeight = 24;
		tileWidth = 27;
		tileHeight = 27;
		// TODO Auto-generated constructor stub
	}
	public ImageModifier(int width, int height, int imageType, int gridWidth, int gridHeight) {
		super(width, height, imageType);
		
		tileWidth = gridWidth;
		tileHeight = gridHeight;
		gWidth = (int)Math.floor((width/tileWidth));
		gHeight = (int)Math.floor((height/tileHeight));
		
		
	}
	
	
}
