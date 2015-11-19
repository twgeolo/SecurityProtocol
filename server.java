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

public class server {
	
	JScrollPane history;
	HistoryEntry[] entries;
	
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
		
		// History List

		entries = new HistoryEntry[] {};
    	JList<HistoryEntry> list = new JList<HistoryEntry>(entries);
		font = new Font("Helvetica Neue", Font.PLAIN, 15);
    	list.setFont(font);
    	list.setCellRenderer(new HistoryCellRenderer());
    	history = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		history.setBorder(BorderFactory.createEmptyBorder());
		
		// Main

    	JLabel mainBar = new JLabel("Main", JLabel.CENTER);
		font = new Font("Helvetica Neue", Font.BOLD, 18);
    	mainBar.setFont(font);
    	mainBar.setForeground(Color.black);
    	mainBar.setBackground(Color.white);
    	mainBar.setOpaque(true);
		mainBar.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.gray));
		
		// Victor image
		
		ImagePanel victorPanel = new ImagePanel("Victor.png");
		victorPanel.setBackground(Color.white);
		
		// Victor label
		
		JLabel victorLabel = new JLabel("Victor", JLabel.CENTER);
		font = new Font("Helvetica Neue", Font.BOLD, 20);
		victorLabel.setFont(font);
		victorLabel.setForeground(Color.black);
		victorLabel.setBackground(Color.white);
		victorLabel.setOpaque(true);
		
		// Victor chat box
		
		JLabel chatLabel = new JLabel("Let's begin", JLabel.CENTER);
		font = new Font("Helvetica Neue", Font.PLAIN, 15);
		chatLabel.setFont(font);
		chatLabel.setForeground(Color.black);
		chatLabel.setBackground(Color.white);
		chatLabel.setBorder(new RoundedBorder(Color.BLACK, 20));
		chatLabel.setOpaque(true);
		
		// Set the bounds
		
    	historyBar.setBounds(0, 0, 240, 33);
    	history.setBounds(0, 33, 240, 522);
    	mainBar.setBounds(240, 0, 528, 33);
		victorPanel.setBounds(454, 288, 100, 100);
		victorLabel.setBounds(454, 388, 100, 30);
		chatLabel.setBounds(354, 138, 300, 100);
		
		// Add the subviews
		
		mainFrame.getContentPane().add(historyBar);
		mainFrame.getContentPane().add(history);
		mainFrame.getContentPane().add(mainBar);
		mainFrame.getContentPane().add(victorPanel);
		mainFrame.getContentPane().add(victorLabel);
		mainFrame.getContentPane().add(chatLabel);
		
		mainFrame.getContentPane().setBackground(Color.white);
    	mainFrame.setResizable(false);
    	mainFrame.setSize(768,576);
    	mainFrame.setLayout(null);
    	mainFrame.setVisible(true);

//		list.setListData( newdata );
//      	contact.revalidate();
//      	contact.repaint();
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
                        
                        //ObjectInputStream input2 = new ObjectInputStream(socket.getInputStream());
                        String[][] g2matrix = (String[][])input.readObject();
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
                            writer.write(String.valueOf(value));
                            writer.flush();
                            System.out.println("Waiting for commitment");
                            //ObjectInputStream inputmG3 = new ObjectInputStream(socket.getInputStream());
                            String[][] modifiedG3 = (String[][])input.readObject();
                            if(value == 0) {
                                System.out.println("Requesting for alpha and Q");
                            } else if(value == 1) {
                                System.out.println("Requesting for pi and subgraph Q'");
                            }
                            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            if(value == 0) {
                                //ObjectInputStream input3 = new ObjectInputStream(socket.getInputStream());
                                Object[] alphaQ = (Object[])input.readObject();
                                //String[] alpha = (String[])input3.readObject();
                                //ObjectInputStream input4 = new ObjectInputStream(socket.getInputStream());
                                //String[][] g3matrix = (String[][])input4.readObject();
                            } else if(value == 1) {
                                //ObjectInputStream input3 = new ObjectInputStream(socket.getInputStream());
                                Object[] piQprime = (Object[])input.readObject();
                                //ObjectInputStream input4 = new ObjectInputStream(socket.getInputStream());
                                //String[][] g3primematrix = (String[][])input4.readObject();
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
