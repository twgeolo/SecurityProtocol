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
import javax.swing.text.*;

public class server {
    
    static JScrollPane history;
    static JTextPane chatLabel;
    static HistoryEntry[] entries;
    static JList<HistoryEntry> list;
    
    public static void setupUI() {
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
        
        entries = new HistoryEntry[] {
            new HistoryEntry("Let's begin", "Past.png")
        };
        list = new JList<HistoryEntry>(entries);
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
        
        chatLabel = new JTextPane();
        font = new Font("Helvetica Neue", Font.PLAIN, 15);
        chatLabel.setFont(font);
        chatLabel.setText("Let's begin");
        chatLabel.setForeground(Color.black);
        chatLabel.setBackground(Color.white);
        chatLabel.setBorder(new RoundedBorder(Color.BLACK, 20));
        chatLabel.setOpaque(true);
        StyledDocument doc = chatLabel.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        // Set the bounds
        
        historyBar.setBounds(0, 0, 320, 33);
        history.setBounds(0, 33, 320, 522);
        mainBar.setBounds(320, 0, 448, 33);
        victorPanel.setBounds(494, 288, 100, 100);
        victorLabel.setBounds(494, 388, 100, 30);
        chatLabel.setBounds(369, 163, 350, 40);
        
        // Add the subviews
        
        mainFrame.getContentPane().add(historyBar);
        mainFrame.getContentPane().add(history);
        mainFrame.getContentPane().add(mainBar);
        mainFrame.getContentPane().add(victorPanel);
        mainFrame.getContentPane().add(victorLabel);
        mainFrame.getContentPane().add(chatLabel);
        
        mainFrame.getContentPane().setBackground(Color.white);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(768,576);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }
    
    public static void setTextToLabel(String str) {
        String listText = "<html>" + str.replaceAll(": ", "<br>") + "</html>";
        int length = entries.length;
        HistoryEntry[] array = Arrays.copyOf(entries, length + 1);
        array[length] = new HistoryEntry(listText, "Past.png");
        entries = array;
        list.setListData(entries);
        history.revalidate();
        history.repaint();
        chatLabel.setText(str);
    }
    
    public static boolean matrixEqual(String[][] array1, String[][] array2) {
        for(int counter = 0; counter < array1.length; counter++) {
            for(int counter2 = 0; counter2 < array1.length; counter2++) {
                if(Integer.parseInt(array1[counter][counter2]) != Integer.parseInt(array2[counter][counter2])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String[][] validateAlpha(String[][] matrix, String[] isofunc) {
        String newMatrix[][] = new String[matrix.length][matrix.length];
        String oneOnly[][] = new String[matrix.length][matrix.length];
        int counter3;
        String newGraph[][] = new String[matrix.length][matrix.length];
        for(int counter = 0; counter < matrix.length; counter++) {
            counter3 = 0;
            for(int counter2 = 0; counter2 < matrix.length; counter2++) {
                if(Integer.parseInt(matrix[counter][counter2]) == 1) {
                    oneOnly[counter][counter3] = String.valueOf(counter2);
                    newGraph[counter][counter3] = isofunc[Integer.parseInt(oneOnly[counter][counter3])];
                    counter3++;
                }
            }
        }
        String rowSwitch[][] = new String[matrix.length][matrix.length];
        for(int counter = 0; counter < matrix.length; counter++) {
            rowSwitch[Integer.parseInt(isofunc[counter])] = newGraph[counter];
        }
        for(int counter = 0; counter < matrix.length; counter++) {
            for(int counter2 = 0; counter2 < matrix.length; counter2++) {
                newMatrix[counter][counter2] = String.valueOf(0);
            }
        }
        for(int counter = 0; counter < matrix.length; counter++) {
            for(int counter2 = 0; counter2 < matrix.length; counter2++) {
                if(rowSwitch[counter][counter2] == null) {
                    break;
                }
                newMatrix[counter][Integer.parseInt(rowSwitch[counter][counter2])] = String.valueOf(1);
            }
        }
        return newMatrix;
    }
    
    public static String[][] validatePi(String[][] matrix, String[] isofunc, int length) {
        String matrixOne[][] = new String[length][length];
        int a = 0;
        for(int counter = 0; counter < matrix.length; counter++) {
            a = 0;
            for(int counter2 = 0; counter2 < matrix.length; counter2++) {
                if(Integer.parseInt(matrix[counter][counter2]) == 1) {
                    matrixOne[counter][a] = String.valueOf(counter2);
                    a++;
                }
            }
        }
        String copy[][] = new String[length][length];
        for(int counter = 0; counter < isofunc.length; counter++) {
            for(int counter2 = 0; counter2 < length; counter2++) {
                if(matrixOne[counter][counter2] == null) {
                    break;
                }
                copy[counter][counter2] = isofunc[Integer.parseInt(matrixOne[counter][counter2])];
            }
        }
        int edge = 0;
        String qprime[][] = new String[length][length];
        for(int counter = 0; counter < length; counter++) {
            for(int counter2 = 0; counter2 < length; counter2++) {
                qprime[counter][counter2] = String.valueOf(-1);
            }
        }
        int edgeplus = 0;
        String store[] = new String[length];
        for(int counter = 0; counter < isofunc.length; counter++) {
            for(int counter2 = 0; counter2 < length; counter2++) {
                if(copy[counter][counter2] == null) {
                    break;
                } else {
                    edgeplus = 1;
                }
                qprime[Integer.parseInt(isofunc[counter])][Integer.parseInt(copy[counter][counter2])] = String.valueOf(1);
                store[edge] = copy[counter][counter2];
            }
            if(edgeplus == 1) {
                edge++;
                edgeplus = 0;
            }
        }
        
        for(int counter = 0; counter < isofunc.length; counter++) {
            for(int counter2 = 0; counter2 < isofunc.length; counter2++) {
                if(isofunc[counter] == null || isofunc[counter2] == null) {
                    break;
                }
                if(Integer.parseInt(qprime[Integer.parseInt(isofunc[counter])][Integer.parseInt(isofunc[counter2])]) == -1) {
                    qprime[Integer.parseInt(isofunc[counter])][Integer.parseInt(isofunc[counter2])] = String.valueOf(0);
                }
            }
        }
        return qprime;
    }
    
    public static void main (String[] args) {
        setupUI();
        
        int runtimes = (int)(Math.random()*11+5);  //5 to 15 times
        System.out.println(runtimes);
        int flag = 0, flag2 = 0;
        try {
            ServerSocket listener = new ServerSocket(9090);
            listener.setReceiveBufferSize(999999999);
            try {
                while (true) {
                    Socket socket = listener.accept();
                    try {
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        String[][] g1matrix = (String[][])input.readObject();
                        String[][] g2matrix = (String[][])input.readObject();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        while(flag < runtimes) {
                            int runnum = flag + 1;
                            System.out.println("Run #" + runnum);
                            int value = (int)(Math.random()*2);
                            writer.write(String.valueOf(value));
                            writer.flush();
                            setTextToLabel("Run #" + runnum + ": Waiting for commitment");
                            System.out.println("Waiting for commitment");
                            String[][] modifiedG3 = (String[][])input.readObject();
                            System.out.println("Commitment received");
                            if(value == 0) {
                                setTextToLabel("Run #" + runnum + ": Requesting for alpha and Q");
                                System.out.println("Requesting for alpha and Q");
                            } else if(value == 1) {
                                setTextToLabel("Run #" + runnum + ": Requesting for pi and subgraph Q'");
                                System.out.println("Requesting for pi and subgraph Q'");
                            }
                            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            if(value == 0) {
                                Object[] alphaQ = (Object[])input.readObject();
                                String[] alpha = (String[])alphaQ[0];
                                String[][] g3matrix = (String[][])alphaQ[1];
                                System.out.println("Received alpha and Q");
                                String[][] maybeG3 = validateAlpha(g2matrix,alpha);
                                System.out.println("Calculating Q using G2 and alpha");
                                boolean check = matrixEqual(g3matrix,maybeG3);
                                if(check == true) {
                                    System.out.println("Calculated Q is the same as received Q");
                                } else {
                                    System.out.println("Calculated Q is not the same as received Q");
                                }
                            } else if(value == 1) {
                                Object[] piQprime = (Object[])input.readObject();
                                String[] pi = (String[])piQprime[0];
                                String[][] g3primematrix = (String[][])piQprime[1];
                                System.out.println("Received pi and subgraph Q'");
                                String[][] maybeG3prime = validatePi(g1matrix,pi,g2matrix.length);
                                boolean check = matrixEqual(maybeG3prime,g3primematrix);
                                if(check == true) {
                                    System.out.println("Calculated Q' is the same as received Q'");
                                } else {
                                    System.out.println("Calculated Q is the same as received Q'");
                                }
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
            setTextToLabel(e.toString());
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            setTextToLabel(e.toString());
            System.out.println(e);
        }
    }
}
