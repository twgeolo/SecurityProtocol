import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;

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
        g.drawImage(image, 0, 0, null);
    }
    
}