import java.io.*;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import java.io.ObjectInputStream;
import javax.crypto.Mac;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.*;
import javax.swing.text.*;

public class Client {
    
    private static JTextPane chatLabel;
    private static DefaultListModel model;
    private static JFrame mainFrame;
    
    public static void setupUI() {
        mainFrame = new JFrame("Client UI");
        
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
        model.addElement(new HistoryEntry("Let's begin", "Past.png"));
        JList list = new JList(model);
        font = new Font("Helvetica Neue", Font.PLAIN, 15);
        list.setFont(font);
        list.setCellRenderer(new HistoryCellRenderer());
        JScrollPane history = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        history.setBorder(BorderFactory.createEmptyBorder());
        history.setVisible(true);
        
        // Main
        
        JLabel mainBar = new JLabel("Main", JLabel.CENTER);
        font = new Font("Helvetica Neue", Font.BOLD, 18);
        mainBar.setFont(font);
        mainBar.setForeground(Color.black);
        mainBar.setBackground(Color.white);
        mainBar.setOpaque(true);
        mainBar.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.gray));
        
        // Victor image
        
        ImagePanel peggyPanel = new ImagePanel("Peggy.png");
        peggyPanel.setBackground(Color.white);
        
        // Victor label
        
        JLabel peggyLabel = new JLabel("Peggy", JLabel.CENTER);
        font = new Font("Helvetica Neue", Font.BOLD, 20);
        peggyLabel.setFont(font);
        peggyLabel.setForeground(Color.black);
        peggyLabel.setBackground(Color.white);
        peggyLabel.setOpaque(true);
        
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
        peggyPanel.setBounds(494, 288, 100, 100);
        peggyLabel.setBounds(494, 388, 100, 30);
        chatLabel.setBounds(344, 163, 400, 40);
        
        // Add the subviews
        
        mainFrame.getContentPane().add(historyBar);
        mainFrame.getContentPane().add(history);
        mainFrame.getContentPane().add(mainBar);
        mainFrame.getContentPane().add(peggyPanel);
        mainFrame.getContentPane().add(peggyLabel);
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
                model.addElement(new HistoryEntry(listText, "Past.png"));
                chatLabel.setText(str);
            }
        });
    }
    
    private static int tl = 0;
    private static int rb = 0;
    private static String[] isofunc;
    private static String[] isofunc2;
    private static ObjectOutputStream out;
    
    
    
    public static String[][] createMatrix(int size) {
        String matrix[][] = new String[size][size];
        for(int counter = 0; counter < size; counter++) {
            for(int counter2 = 0; counter2 < size; counter2++) {
                int store = (int)Math.floor((Math.random()*2));
                matrix[counter][counter2] = String.valueOf(store);
            }
        }
        for(int counter3 = 0; counter3 < size; counter3++) {
            for(int counter4 = 0; counter4 < size; counter4++) {
                matrix[counter3][counter4] = matrix[counter4][counter3];
            }
        }
        return matrix;
    }
    
    public static String[][] isomorphism(String[][] matrix, int number) {
        String newMatrix[][] = new String[matrix.length][matrix.length];
        if(number == 0) {
            isofunc = new String[matrix.length];
            int store[] = new int[matrix.length];
            for(int counter = 0; counter < matrix.length; counter++) {
                store[counter] = counter;
            }
            int check, flag = 0;
            for(int counter = 0; counter < matrix.length; counter++) {
                check = (int)(Math.random()*(matrix.length));
                for(int counter2 = 0; counter2 < matrix.length; counter2++) {
                    if(store[check] == (matrix.length+1)) {
                        flag = 1;
                        break;
                    }
                }
                if(flag == 1) {
                    flag = 0;
                    counter--;
                    continue;
                } else {
                    isofunc[counter] = String.valueOf(check);
                }
                store[check] = matrix.length+1;
            }
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
        } else {
            isofunc2 = new String[matrix.length];
            int store[] = new int[matrix.length];
            for(int counter = 0; counter < matrix.length; counter++) {
                store[counter] = counter;
            }
            int check, flag = 0;
            for(int counter = 0; counter < matrix.length; counter++) {
                check = (int)(Math.random()*(matrix.length));
                for(int counter2 = 0; counter2 < matrix.length; counter2++) {
                    if(store[check] == (matrix.length+1)) {
                        flag = 1;
                        break;
                    }
                }
                if(flag == 1) {
                    flag = 0;
                    counter--;
                    continue;
                } else {
                    isofunc2[counter] = String.valueOf(check);
                }
                store[check] = matrix.length+1;
            }
            String oneOnly[][] = new String[matrix.length][matrix.length];
            int counter3;
            String newGraph[][] = new String[matrix.length][matrix.length];
            for(int counter = 0; counter < matrix.length; counter++) {
                counter3 = 0;
                for(int counter2 = 0; counter2 < matrix.length; counter2++) {
                    if(Integer.parseInt(matrix[counter][counter2]) == 1) {
                        oneOnly[counter][counter3] = String.valueOf(counter2);
                        newGraph[counter][counter3] = isofunc2[Integer.parseInt(oneOnly[counter][counter3])];
                        counter3++;
                    }
                }
            }
            String rowSwitch[][] = new String[matrix.length][matrix.length];
            for(int counter = 0; counter < matrix.length; counter++) {
                rowSwitch[Integer.parseInt(isofunc2[counter])] = newGraph[counter];
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
        }
        return newMatrix;
    }
    
    private static String convertHex(byte[] input) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            int mid = (input[i] >>> 4) & 0x0F;
            int counter = 0;
            do {
                if ((0 <= mid) && (mid <= 9))
                    buffer.append((char) ('0' + mid));
                else
                    buffer.append((char) ('a' + (mid - 10)));
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
    
    public static String[][] getRandMatrix(int size, int maxRandValue){
        Random random = new Random();
        String[][] matrix = new String[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                matrix[i][j] = Integer.toString(random.nextInt(maxRandValue));
            }
        }
        return matrix;
    }
    
    public static String[][] supergraph(String[][] subgraph) {
        tl = (int)(Math.random()*41+10);
        rb = (int)(Math.random()*41+10);
        int rowLen = subgraph.length + tl + rb;
        String supgraph[][] = new String[rowLen][rowLen];
        for(int counter = 0; counter < tl; counter++) {
            for(int counter2 = 0; counter2 < rowLen; counter2++) {
                int store = (int)Math.floor((Math.random()*2));
                supgraph[counter][counter2] = String.valueOf(store);
            }
        }
        for(int counter = rowLen-rb; counter < rowLen; counter++) {
            for(int counter2 = 0; counter2 < rowLen; counter2++) {
                int store = (int)Math.floor((Math.random()*2));
                supgraph[counter][counter2] = String.valueOf(store);
            }
        }
        for(int counter = tl; counter < rowLen-rb; counter++) {
            for(int counter2 = 0; counter2 < tl; counter2++) {
                int store = (int)Math.floor((Math.random()*2));
                supgraph[counter][counter2] = String.valueOf(store);
            }
        }
        for(int counter = tl; counter < rowLen-rb; counter++) {
            for(int counter2 = rowLen-rb; counter2 < rowLen; counter2++) {
                int store = (int)Math.floor((Math.random()*2));
                supgraph[counter][counter2] = String.valueOf(store);
            }
        }
        int a = 0, b = 0;
        for(int counter = tl; counter < rowLen-rb; counter++) {
            b = 0;
            for(int counter2 = tl; counter2 < rowLen-rb; counter2++) {
                supgraph[counter][counter2] = subgraph[a][b];
                b++;
            }
            a++;
        }
        return supgraph;
    }
    
    public static String[][] subgraph(String[][] supergraph) {
        int rowLen = supergraph.length - tl - rb;
        String matrixOne[][] = new String[supergraph.length][supergraph.length];
        int a = 0;
        for(int counter = 0; counter < supergraph.length; counter++) {
            a = 0;
            for(int counter2 = 0; counter2 < supergraph.length; counter2++) {
                if(Integer.parseInt(supergraph[counter][counter2]) == 1) {
                    matrixOne[counter][a] = String.valueOf(counter2);
                    a++;
                }
            }
        }
        String todelete[] = new String[rowLen];
        for(int counter = 0; counter < tl; counter++) {
            todelete[counter] = isofunc2[counter];
        }
        int tdcounter = 0;
        for(int counter = supergraph.length-rb; counter < supergraph.length; counter++) {
            todelete[tl+tdcounter] = isofunc2[counter];
            tdcounter++;
        }
        a = 0;
        int continueflag = 0, i = 0;
        String subgraph[][] = new String[supergraph.length][supergraph.length];
        for(int counter = 0; counter < supergraph.length; counter++) {
            a = 0;
            i = 0;
            while(a < todelete.length) {
                if(todelete[a] == null) {
                    break;
                }
                if(counter == Integer.parseInt(todelete[a])) {
                    continueflag = 1;
                    break;
                }
                a++;
            }
            a = 0;
            if(continueflag == 1) {
                continueflag = 0;
                continue;
            }
            for(int counter2 = 0; counter2 < supergraph.length; counter2++) {
                if(matrixOne[counter][counter2] == null) {
                    break;
                }
                while(a < todelete.length) {
                    if(todelete[a] == null) {
                        break;
                    }
                    if(Integer.parseInt(matrixOne[counter][counter2]) == Integer.parseInt(todelete[a])) {
                        continueflag = 1;
                        break;
                    }
                    a++;
                }
                a = 0;
                if(continueflag == 1) {
                    continueflag = 0;
                    continue;
                }
                subgraph[counter][i] = matrixOne[counter][counter2];
                i++;
            }
        }
        /*for(int counter = 0; counter < supergraph.length; counter++) {
         for(int counter2 = 0; counter2 < supergraph.length; counter2++) {
         System.out.print(subgraph[counter][counter2] + " ");
         }
         System.out.println();
         }*/
        int edge = 0;
        String qprime[][] = new String[supergraph.length][supergraph.length];
        for(int counter = 0; counter < supergraph.length; counter++) {
            for(int counter2 = 0; counter2 < supergraph.length; counter2++) {
                qprime[counter][counter2] = String.valueOf(-1);
            }
        }
        int edgeplus = 0;
        String store[] = new String[supergraph.length];
        for(int counter = 0; counter < supergraph.length; counter++) {
            for(int counter2 = 0; counter2 < supergraph.length; counter2++) {
                if(subgraph[counter][counter2] == null) {
                    break;
                } else {
                    edgeplus = 1;
                }
                qprime[counter][Integer.parseInt(subgraph[counter][counter2])] = String.valueOf(1);
                store[edge] = String.valueOf(counter);
            }
            if(edgeplus == 1) {
                edge++;
                edgeplus = 0;
            }
        }
        
        for(int counter = 0; counter < store.length; counter++) {
            for(int counter2 = 0; counter2 < store.length; counter2++) {
                if(store[counter] == null || store[counter2] == null) {
                    break;
                }
                if(Integer.parseInt(qprime[Integer.parseInt(store[counter])][Integer.parseInt(store[counter2])]) == -1) {
                    qprime[Integer.parseInt(store[counter])][Integer.parseInt(store[counter2])] = String.valueOf(0);
                }
            }
        }
        return qprime;
    }
    
    public static String[] generatepi(String[] iso1, String[] iso2, int top, int bot) {
        String[] pi = new String[iso1.length];
        for(int counter = 0; counter < iso1.length; counter++) {
            int phi = Integer.parseInt(iso1[counter]);
            int phitop =  phi + top;
            String alphavalue = iso2[phitop];
            pi[counter] = alphavalue;
        }
        return pi;
    }
    
    public static void main (String[] args) {
        setupUI();
        
        //input graphs
        String g1file = "g1.txt";
        String g2file = "g2.txt";
        String sfile = "s.txt";
        String ifile = "i.txt";
        
        String[] options = {"G1+G2+S+I","G1", "Random"};
        String option = (JOptionPane.showInputDialog(null,"Choose input option:","",JOptionPane.QUESTION_MESSAGE,null,options,options[0])).toString();
        
        if (option.equals("G1+G2+S+I"))	 {
            JOptionPane.showMessageDialog(null, "Select G1", "", JOptionPane.PLAIN_MESSAGE);
            String g1filename = null;
            while (g1filename == null) {
                FileDialog fd = new FileDialog(mainFrame, "Select G1:", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir"));
                fd.setFile("*.txt");
                fd.setVisible(true);
                g1filename = fd.getFile();
            }
            g1file = g1filename;
            
            JOptionPane.showMessageDialog(null, "Select G2", "", JOptionPane.PLAIN_MESSAGE);
            String g2filename = null;
            while (g2filename == null) {
                FileDialog fd = new FileDialog(mainFrame, "Select G2:", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir"));
                fd.setFile("*.txt");
                fd.setVisible(true);
                g2filename = fd.getFile();
            }
            g2file = g2filename;
            
            JOptionPane.showMessageDialog(null, "Select S", "", JOptionPane.PLAIN_MESSAGE);
            String sfilename = null;
            while (sfilename == null) {
                FileDialog fd = new FileDialog(mainFrame, "Select S:", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir"));
                fd.setFile("*.txt");
                fd.setVisible(true);
                sfilename = fd.getFile();
            }
            sfile = sfilename;
            
            JOptionPane.showMessageDialog(null, "Select I", "", JOptionPane.PLAIN_MESSAGE);
            String ifilename = null;
            while (ifilename == null) {
                FileDialog fd = new FileDialog(mainFrame, "Select I:", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir"));
                fd.setFile("*.txt");
                fd.setVisible(true);
                ifilename = fd.getFile();
            }
            ifile = ifilename;
            
        } else if (option.equals("G1")){
            // choose G1
            String filename = null;
            while (filename == null) {
                FileDialog fd = new FileDialog(mainFrame, "Select G1:", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.dir"));
                fd.setFile("*.txt");
                fd.setVisible(true);
                filename = fd.getFile();
            }
            g1file = filename;
            // create other graphs
            try {
                int x = 0;
                int y = 0;
               	String g1matrix[][] = new String[1000][1000];
                FileInputStream inputStream = new FileInputStream(g1file);
                char current;
                while (inputStream.available() > 0) {
                    current = (char) inputStream.read();
                    if(current == '\n') {
                        x++;
                        y = 0;
                        continue;
                    } else if(current == ' ') {
                        continue;
                    }
                    g1matrix[x][y] = String.valueOf(current);
                    y++;
                }
                String g1matrixcopy[][] = new String[x][x];
                for(int c = 0; c < x; c++) {
                    for(int c2 = 0; c2 < x; c2++) {
                        g1matrixcopy[c][c2] = g1matrix[c][c2];
                    }
                }
                
                setTextToLabel("Making G2'");
                System.out.println("Making G2'");
                File file2 = new File(sfile);
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
                BufferedWriter bw2 = new BufferedWriter(fw2);
                String smatrix[][] = new String[1000][1000];
                smatrix = isomorphism(g1matrixcopy, 0);
                for(int row = 0; row < smatrix.length; row++) {
                    for(int col =0; col < smatrix[row].length; col++) {
                        bw2.write(smatrix[row][col]);
                        bw2.write(" ");
                    }
                    bw2.write("\n");
                }
                bw2.close();
                
                setTextToLabel("Making G2");
                System.out.println("Making G2");
                File file3 = new File(g2file);
                if (!file3.exists()) {
                    file3.createNewFile();
                }
                FileWriter fw3 = new FileWriter(file3.getAbsoluteFile());
                BufferedWriter bw3 = new BufferedWriter(fw3);
                String g2matrix[][] = new String[1100][1100];
                g2matrix = supergraph(smatrix);
                for(int row = 0; row < g2matrix.length; row++) {
                    for(int col =0; col < g2matrix[row].length; col++) {
                        bw3.write(g2matrix[row][col]);
                        bw3.write(" ");
                    }
                    bw3.write("\n");
                }
                bw3.close();
                
                setTextToLabel("Making phi");
                System.out.println("Making phi");
                File file4 = new File(ifile);
                if (!file4.exists()) {
                    file4.createNewFile();
                }
                FileWriter fw4 = new FileWriter(file4.getAbsoluteFile());
                BufferedWriter bw4 = new BufferedWriter(fw4);
                bw4.write(String.valueOf(tl));
                bw4.write("\n");
                bw4.write(String.valueOf(rb));
                bw4.write("\n");
                for(int row = 0; row < isofunc.length; row++) {
                    bw4.write(isofunc[row]);
                    bw4.write("\n");
                }
                bw4.close();
            }
            catch (IOException e) {
                setTextToLabel(e.toString());
                System.out.println(e);
            }
        } else {
            try {
                setTextToLabel("Making G1");
                System.out.println("Making G1");
                File file = new File(g1file);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                String g1matrix[][] = new String[1000][1000];
                int range = (990 - 10) + 1;
                int randomNum = (int)(Math.random()*range)+10;
                g1matrix = createMatrix(randomNum);
                for(int row = 0; row < g1matrix.length; row++) {
                    for(int col =0; col < g1matrix[row].length; col++) {
                        bw.write(g1matrix[row][col]);
                        bw.write(" ");
                    }
                    bw.write("\n");
                }
                bw.close();
                
                setTextToLabel("Making G2'");
                System.out.println("Making G2'");
                File file2 = new File(sfile);
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
                BufferedWriter bw2 = new BufferedWriter(fw2);
                String smatrix[][] = new String[1000][1000];
                smatrix = isomorphism(g1matrix, 0);
                for(int row = 0; row < smatrix.length; row++) {
                    for(int col =0; col < smatrix[row].length; col++) {
                        bw2.write(smatrix[row][col]);
                        bw2.write(" ");
                    }
                    bw2.write("\n");
                }
                bw2.close();
                
                setTextToLabel("Making G2");
                System.out.println("Making G2");
                File file3 = new File(g2file);
                if (!file3.exists()) {
                    file3.createNewFile();
                }
                FileWriter fw3 = new FileWriter(file3.getAbsoluteFile());
                BufferedWriter bw3 = new BufferedWriter(fw3);
                String g2matrix[][] = new String[1100][1100];
                g2matrix = supergraph(smatrix);
                for(int row = 0; row < g2matrix.length; row++) {
                    for(int col =0; col < g2matrix[row].length; col++) {
                        bw3.write(g2matrix[row][col]);
                        bw3.write(" ");
                    }
                    bw3.write("\n");
                }
                bw3.close();
                
                setTextToLabel("Making phi");
                System.out.println("Making phi");
                File file4 = new File(ifile);
                if (!file4.exists()) {
                    file4.createNewFile();
                }
                FileWriter fw4 = new FileWriter(file4.getAbsoluteFile());
                BufferedWriter bw4 = new BufferedWriter(fw4);
                bw4.write(String.valueOf(tl));
                bw4.write("\n");
                bw4.write(String.valueOf(rb));
                bw4.write("\n");
                for(int row = 0; row < isofunc.length; row++) {
                    bw4.write(isofunc[row]);
                    bw4.write("\n");
                }
                bw4.close();
            }
            catch (IOException e) {
                setTextToLabel(e.toString());
                System.out.println(e);
            }
        }
        
        //proving
        Socket MyClient;
        try {
            MyClient = new Socket("127.0.0.1", 9090);
            int x = 0;
            int y = 0;
            String matrix[][] = new String[1000][1000];
            FileInputStream inputStream = new FileInputStream(g1file);
            char current;
            while (inputStream.available() > 0) {
                current = (char) inputStream.read();
                if(current == '\n') {
                    x++;
                    y = 0;
                    continue;
                } else if(current == ' ') {
                    continue;
                }
                matrix[x][y] = String.valueOf(current);
                y++;
            }
            String matrixcopy[][] = new String[x][x];
            for(int counter = 0; counter < x; counter++) {
                for(int counter2 = 0; counter2 < x; counter2++) {
                    matrixcopy[counter][counter2] = matrix[counter][counter2];
                }
            }
            
            x = 0;
            y = 0;
            String matrix2[][] = new String[1100][1100];
            FileInputStream inputStream2 = new FileInputStream(g2file);
            while (inputStream2.available() > 0) {
                current = (char) inputStream2.read();
                if(current == '\n') {
                    x++;
                    y = 0;
                    continue;
                } else if(current == ' ') {
                    continue;
                }
                matrix2[x][y] = String.valueOf(current);
                y++;
            }
            String matrixcopy2[][] = new String[x][x];
            for(int counter = 0; counter < x; counter++) {
                for(int counter2 = 0; counter2 < x; counter2++) {
                    matrixcopy2[counter][counter2] = matrix2[counter][counter2];
                }
            }
            
            x = 0;
            String matrix3[] = new String[1100];
            FileInputStream inputStream3 = new FileInputStream(ifile);
            while (inputStream3.available() > 0) {
                current = (char) inputStream3.read();
                if(x == 0) {
                    tl = (int)(current-'0');
                    x++;
                    continue;
                }
                if(x == 2) {
                    rb = (int)(current-'0');
                    x++;
                    continue;
                }
                if(current == '\n') {
                    x++;
                    continue;
                }
                matrix3[x-4] = String.valueOf(current);
            }
            isofunc = new String[x-4];
            for(int counter = 0; counter < x-4; counter++) {
                isofunc[counter] = matrix3[counter];
            }
            
            out = new ObjectOutputStream(MyClient.getOutputStream());
            out.writeObject(matrixcopy);
            out.writeObject(matrixcopy2);
            int num;
            int runnum = 1;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(MyClient.getInputStream()));
            while((num = stdIn.read()) != -1) {
                String g3matrix[][] = new String[matrixcopy2.length][matrixcopy2.length];
                g3matrix = isomorphism(matrixcopy2, 1);
                String pi[] = new String[isofunc.length];
                pi = generatepi(isofunc,isofunc2,tl,rb);
                String matrixcopy3[][] = new String[matrixcopy.length][matrixcopy.length];
                String g3primematrix[][] = new String[1000][1000];
                g3primematrix = subgraph(g3matrix);
                int list1maxvalue = (int)(Math.random()*11+11);
                int list2maxvalue = (int)(Math.random()*11+11);
                String list1[][] = getRandMatrix(g3matrix.length, list1maxvalue);
                String list2[][] = getRandMatrix(g3matrix.length, list2maxvalue);
                String[][] modifiedG3 = bitCommit(list1, list2, g3matrix);
                if(Integer.parseInt(String.valueOf((char)num)) != 2) {
                    System.out.println("Run #" + runnum);
                    out.writeObject(modifiedG3);
                    setTextToLabel("Run #" + runnum + ": Commitment sent");
                    System.out.println("Commitment sent");
                }
                if(Integer.parseInt(String.valueOf((char)num)) == 0) {
                    setTextToLabel("Run #" + runnum + ": Request for alpha and Q received");
                    runnum++;
                    Object[] alphaQ = {isofunc2,g3matrix,list1,list2};
                    System.out.println("Request for alpha and Q received");
                    out.writeObject(alphaQ);
                    setTextToLabel("Run #" + (runnum - 1) + ": Sent alpha and Q");
                    System.out.println("Sent alpha and Q");
                } else if(Integer.parseInt(String.valueOf((char)num)) == 1) {
                    setTextToLabel("Run #" + runnum + ": Request for pi and subgraph Q' received");
                    runnum++;
                    Object[] piQprime = {pi,g3primematrix,list1,list2};
                    System.out.println("Request for pi and subgraph Q' received");
                    out.writeObject(piQprime);
                    setTextToLabel("Run #" + (runnum - 1) + ": Sent pi and subgraph Q'");
                    System.out.println("Sent pi and subgraph Q'");
                } else if(Integer.parseInt(String.valueOf((char)num)) == 2) {
                    break;
                }
                out.flush();
            }
        }
        catch (IOException e) {
            setTextToLabel(e.toString());
            System.out.println(e);
        }
    }
}
