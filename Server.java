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
import javax.crypto.Mac;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Server {
    
    static JScrollPane history;
    static JTextPane chatLabel;
    static DefaultListModel model;
    static JList list;
    static JFrame mainFrame;
    
    public static void setupUI() {
        mainFrame = new JFrame("Server UI");
        
        // History Bar
        
        JLabel historyBar = new JLabel("History", JLabel.CENTER);
        Font font = new Font("Helvetica Neue", Font.BOLD, 18);
        historyBar.setFont(font);
        historyBar.setForeground(Color.black);
        historyBar.setBackground(Color.white);
        historyBar.setOpaque(true);
        historyBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
        
        // History List
        
        model = new DefaultListModel();
        model.addElement(new HistoryEntry("Let's begin", "Images/Past.png"));
        list = new JList(model);
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
        
        ImagePanel victorPanel = new ImagePanel("Images/Victor.png");
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
    
    public static void setTextToLabel(final String str) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String listText = "<html>" + str.replaceAll(": ", "<br>") + "</html>";
                model.addElement(new HistoryEntry(listText, "Images/Past.png"));
                chatLabel.setText(str);
            }
        });
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
    
    public static boolean matrixEqualCommitment(String[][] array1, String[][] array2) {
        for(int counter = 0; counter < array1.length; counter++) {
            for(int counter2 = 0; counter2 < array1.length; counter2++) {
                if(!array1[counter][counter2].equals(array2[counter][counter2])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static String convertHex(byte[] input) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            int mid = (input[i] >>> 4) & 0x0F;
            int counter = 0;
            do {
                if ((0 <= mid) && (mid <= 9)){
                    buffer.append((char) ('0' + mid));
                }
                else{
                    buffer.append((char) ('a' + (mid - 10)));
                }
                mid = input[i] & 0x0F;
            } while(counter++ < 1);
        }
        return buffer.toString();
    }
    
    public static String crypto_hash(String plaintext) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hash = new byte[40];
        md.update(plaintext.getBytes("iso-8859-1"), 0, plaintext.length());
        hash = md.digest();
        return convertHex(hash);
    }
    
    public static boolean qprimeCommitment(String[][] list1, String[][] list2, String[][] qprime, String[][] modifiedQ) {
        for(int counter = 0; counter < qprime.length; counter++) {
            for(int counter2 = 0; counter2 < qprime.length; counter2++) {
                if(Integer.parseInt(qprime[counter][counter2]) == -1) {
                    continue;
                } else {
                    try {
                        if(!crypto_hash(list1[counter][counter2] + list2[counter][counter2] + qprime[counter][counter2]).equals(modifiedQ[counter][counter2])){
                            return false;
                        }
                    } catch(NoSuchAlgorithmException e) {
                    } catch(UnsupportedEncodingException e) {
                    }
                }
            }
        }
        return true;
    }
    
    
    public static String[][] bitCommit(String[][] list1, String[][] list2, String[][] list3){
        String[][] commitment = new String[list3.length][list3.length];
        for (int i = 0; i < list3.length; i++){
            for (int j = 0; j < list3.length; j++){
                StringBuilder sb = new StringBuilder();
                sb.append(list1[i][j]);
                sb.append(list2[i][j]);
                sb.append(list3[i][j]);
                try{
                    commitment[i][j] = crypto_hash(sb.toString());
                }catch (Exception e){
                };
            }
        }
        return commitment;
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
        
        // input number of runs
        int runtimes = 0;
        while (runtimes == 0) {
            String runnuminput = JOptionPane.showInputDialog(null,"Enter number of runs ( 'rand' for a random number):","",
                                                             JOptionPane.PLAIN_MESSAGE);
            if (runnuminput == null || runnuminput.equals("rand")) {
                runtimes = (int)(Math.random()*11+5);  //5 to 15 times
            } else {
                try {
                    runtimes = Integer.parseInt(runnuminput);
                }
                catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "This is not a number!", "Error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        
        //main
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
                            setTextToLabel("Run #" + runnum + ": Commitment received");
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
                                String[][] list1 = (String[][])alphaQ[2];
                                String[][] list2 = (String[][])alphaQ[3];
                                setTextToLabel("Run #" + runnum + ": Received alpha and Q");
                                System.out.println("Received alpha and Q");
                                String[][] modifiedG3_2 = bitCommit(list1, list2, g3matrix);
                                boolean commitmentCheck = matrixEqualCommitment(modifiedG3,modifiedG3_2);
                                if(commitmentCheck == false) {
                                    flag++;
                                    setTextToLabel("Run #" + runnum + ": Commitment does not match");
                                    System.out.println("Commitment does not match");
                                    break;
                                } else {
                                    setTextToLabel("Run #" + runnum + ": Commitment does match");
                                    System.out.println("Commitment does match");
                                }
                                String[][] maybeG3 = validateAlpha(g2matrix,alpha);
                                setTextToLabel("Run #" + runnum + ": Calculating Q using G2 and alpha");
                                System.out.println("Calculating Q using G2 and alpha");
                                boolean check = matrixEqual(g3matrix,maybeG3);
                                if(check == true) {
                                    setTextToLabel("Run #" + runnum + ": Calculated Q is the same as received Q");
                                    System.out.println("Calculated Q is the same as received Q");
                                } else {
                                    setTextToLabel("Run #" + runnum + ": Calculated Q is not the same as received Q");
                                    System.out.println("Calculated Q is not the same as received Q");
                                }
                            } else if(value == 1) {
                                Object[] piQprime = (Object[])input.readObject();
                                String[] pi = (String[])piQprime[0];
                                String[][] g3primematrix = (String[][])piQprime[1];
                                String[][] list1 = (String[][])piQprime[2];
                                String[][] list2 = (String[][])piQprime[3];
                                setTextToLabel("Run #" + runnum + ": Received pi and subgraph Q'");
                                System.out.println("Received pi and subgraph Q'");
                                boolean commitmentCheck = qprimeCommitment(list1, list2, g3primematrix, modifiedG3);
                                if(commitmentCheck == false) {
                                    flag++;
                                    setTextToLabel("Run #" + runnum + ": Commitment does not match");
                                    System.out.println("Commitment does not match");
                                    break;
                                } else {
                                    setTextToLabel("Run #" + runnum + ": Commitment does match");
                                    System.out.println("Commitment does match");
                                }
                                String[][] maybeG3prime = validatePi(g1matrix,pi,g2matrix.length);
                                boolean check = matrixEqual(maybeG3prime,g3primematrix);
                                if(check == true) {
                                    setTextToLabel("Run #" + runnum + ": Calculated Q' is the same as received Q'");
                                    System.out.println("Calculated Q' is the same as received Q'");
                                } else {
                                    setTextToLabel("Run #" + runnum + ": Calculated Q is the same as received Q'");
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
