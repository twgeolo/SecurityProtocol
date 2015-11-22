import javax.swing.*;
import javax.imageio.*;

class HistoryEntry {
    private final String title;
    private final String imagePath;
    
    public HistoryEntry(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }
    
    public String getTitle() {
        return title;
    }
    
    public ImageIcon getImage() {
        return new ImageIcon(imagePath);
    }
}
