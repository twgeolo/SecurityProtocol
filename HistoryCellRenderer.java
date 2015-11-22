import java.awt.*;
import javax.swing.*;

class HistoryCellRenderer extends JLabel implements ListCellRenderer {
    
    public HistoryCellRenderer() {
        setIconTextGap(10);
        setOpaque(true);
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        HistoryEntry entry = (HistoryEntry) value;
        setIcon(entry.getImage());
        setText(entry.getTitle());
        
        if (isSelected) {
            setForeground(Color.white);
            setBackground(new Color(0, 0, 128));
        } else {
            setForeground(Color.black);
            setBackground(Color.white);
        }
        
        return this;
    }
}
