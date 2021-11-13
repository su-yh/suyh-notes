import java.awt.image.BufferedImage;

{
	BufferedImage image = null;
	
	try {
		image =	ImageIO.read(FlyingObject.class.getResource(fileName));
	} catch (IOException e) {
		e.printStackTrace();
	}
}



