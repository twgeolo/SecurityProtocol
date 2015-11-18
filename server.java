import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.*;

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

class HistoryCellRenderer extends JLabel implements ListCellRenderer {
  private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

  public HistoryCellRenderer() {
    setOpaque(true);
    setIconTextGap(12);
  }

  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    HistoryEntry entry = (HistoryEntry) value;
    setText(entry.getTitle());
    setIcon(entry.getImage());
    if (isSelected) {
      setBackground(HIGHLIGHT_COLOR);
      setForeground(Color.white);
    } else {
      setBackground(Color.white);
      setForeground(Color.black);
    }
    return this;
  }
}

public class server {
	
	public server() {
    	JFrame mainFrame = new JFrame("Server UI");

		// History Bar

    	JLabel historyBar = new JLabel("History", JLabel.CENTER);
		Font font = new Font("Helvetica Neue", Font.BOLD, 18);
    	historyBar.setFont(font);
    	historyBar.setForeground(Color.black);
    	historyBar.setBackground(Color.white);
    	historyBar.setOpaque(true);
		historyBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
    	historyBar.setBounds(0, 0, 240, 33);
		mainFrame.getContentPane().add(historyBar);
		
		// History List

		HistoryEntry[] entries = {
		      new HistoryEntry("Requesting 1", "Past.png"),
		      new HistoryEntry("Requesting 2", "Past.png"),
		      new HistoryEntry("Requesting 3", "Past.png"),
		      new HistoryEntry("Requesting 4", "Past.png"),
		      new HistoryEntry("Requesting 5", "Past.png"),
		      new HistoryEntry("Requesting 6", "Past.png"),
		      new HistoryEntry("Requesting 7", "Past.png"),
		      new HistoryEntry("Requesting 8", "Past.png"),
		      new HistoryEntry("Requesting 9", "Past.png")
		};
    	JList<HistoryEntry> list = new JList<HistoryEntry>(entries);
		font = new Font("Helvetica Neue", Font.PLAIN, 15);
    	list.setFont(font);
    	list.setCellRenderer(new HistoryCellRenderer());
    	JScrollPane history = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		history.setBorder(BorderFactory.createEmptyBorder());
    	history.setBounds(0, 33, 240, 522);
		mainFrame.getContentPane().add(history);
		
		// Main

    	JLabel mainBar = new JLabel("Main", JLabel.CENTER);
		font = new Font("Helvetica Neue", Font.BOLD, 18);
    	mainBar.setFont(font);
    	mainBar.setForeground(Color.black);
    	mainBar.setBackground(Color.white);
    	mainBar.setOpaque(true);
		mainBar.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.gray));
    	mainBar.setBounds(240, 0, 528, 33);
		mainFrame.getContentPane().add(mainBar);
		
		// Victor image
		
		JLabel imageLabel = new JLabel(this.getImage("Victor.png"));
		imageLabel.setBounds(454, 238, 100, 100);
		mainFrame.getContentPane().add(imageLabel);
		
		mainFrame.pack();
    	mainFrame.setResizable(false);
    	mainFrame.setSize(768,576);
    	mainFrame.setLayout(null);
    	mainFrame.setVisible(true);
	}
	
	private ImageIcon getImage(String path) {
	    URL url = getClass().getResource(path);
	    if (url != null)
	        return new ImageIcon(url);
	    return null;
	}
	
    public static void main (String[] args) {
		new server();
        int runtimes = (int)(Math.random()*11+5);  //5 to 15 times
        int flag = 0, flag2 = 0;
        try {
            ServerSocket listener = new ServerSocket(9090);
            try {
                while (true) {
                    Socket socket = listener.accept();
                    try {
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        String[][] g1matrix = (String[][])input.readObject();
                        /*for(int counter = 0; counter < g1matrix.length; counter++) {
                         for(int counter2 = 0; counter2 < g1matrix[0].length; counter2++) {
                         System.out.print(g1matrix[counter][counter2] + " ");
                         }
                         System.out.println();
                         }
                         System.out.println();*/
                        
                        ObjectInputStream input2 = new ObjectInputStream(socket.getInputStream());
                        String[][] g2matrix = (String[][])input2.readObject();
                        /*for(int counter = 0; counter < g2matrix.length; counter++) {
                         for(int counter2 = 0; counter2 < g2matrix[0].length; counter2++) {
                         System.out.print(g2matrix[counter][counter2] + " ");
                         }
                         System.out.println();
                         }
                         System.out.println();*/
                        
                        /*ObjectInputStream input3 = new ObjectInputStream(socket.getInputStream());
                         String[][] array3 = (String[][])input3.readObject();*/
                        /*for(int counter = 0; counter < array3.length; counter++) {
                         for(int counter2 = 0; counter2 < array3[0].length; counter2++) {
                         System.out.print(array3[counter][counter2] + " ");
                         }
                         System.out.println();
                         }
                         System.out.println();*/
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        while(flag < runtimes) {
                            int runnum = flag + 1;
                            System.out.println("Run #" + runnum);
                            int value = (int)(Math.random()*2);
                            if(value == 0) {
                                System.out.println("Requesting for alpha and Q");
                            } else if(value == 1) {
                                System.out.println("Requesting for pi and subgraph Q'");
                            }
                            writer.write(String.valueOf(value));
                            writer.flush();
                            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            if(value == 0) {
                                ObjectInputStream input3 = new ObjectInputStream(socket.getInputStream());
                                String[] alpha = (String[])input.readObject();
                                ObjectInputStream input4 = new ObjectInputStream(socket.getInputStream());
                                String[][] g3matrix = (String[][])input.readObject();
                            } else if(value == 1) {
                                ObjectInputStream input3 = new ObjectInputStream(socket.getInputStream());
                                String[] pi = (String[])input.readObject();
                                ObjectInputStream input4 = new ObjectInputStream(socket.getInputStream());
                                String[][] g3primematrix = (String[][])input.readObject();
                            }
                            flag++;
                        }
                        writer.write(String.valueOf(2));
                        writer.flush();
                        writer.close();
                        flag = 0;
                    } finally {
                        socket.close();
                    }
                }
            }
            finally {
                listener.close();
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
