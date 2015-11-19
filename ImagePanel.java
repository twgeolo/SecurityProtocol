import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(String filepath) {
       try {                
          image = ImageIO.read(new File(filepath));
       } catch (Exception e) {
			e.printStackTrace();
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }

}