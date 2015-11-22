import javax.swing.*;
import javax.imageio.ImageIO;

class HistoryEntry {
    private final String title;
    
    private final String imagePath;
    
    private ImageIcon image;
    
    public HistoryEntry(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }
    
    public String getTitle() {
        return title;
    }
    
    public ImageIcon getImage() {
        if (image == null) {
            image = new ImageIcon(imagePath);
        }
        return image;
    }
    
    // Override standard toString method to give a useful result
    public String toString() {
        return title;
    }
}
