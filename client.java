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

public class client {

	static JScrollPane history;
	static JTextPane chatLabel;
	static HistoryEntry[] entries;
	static JList<HistoryEntry> list;

	public static void setupUI() {
    	JFrame mainFrame = new JFrame("Client UI");

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
		chatLabel.setBounds(369, 163, 350, 40);

		// Add the subviews

		mainFrame.getContentPane().add(historyBar);
		mainFrame.getContentPane().add(history);
		mainFrame.getContentPane().add(mainBar);
		mainFrame.getContentPane().add(peggyPanel);
		mainFrame.getContentPane().add(peggyLabel);
		mainFrame.getContentPane().add(chatLabel);

		mainFrame.getContentPane().setBackground(Color.white);
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

    private static int tl = 0;
    private static int rb = 0;
    private static String[] isofunc;
    private static String[] isofunc2;

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

    private static String convToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convToHex(sha1hash);
    }


    public static String[][] bitCommit_HASH_SHA2_list(String[][] list1, String[][] list2, String[][] bitList){
        String[][] commitment = new String[bitList.length][bitList.length];
        for (int i = 0; i < bitList.length; i++){
            for (int j = 0; j < bitList.length; j++){
                StringBuilder sb = new StringBuilder();
                sb.append(list1[i][j]);
                sb.append(list2[i][j]);
                sb.append(bitList[i][j]);
                try{
                    commitment[i][j] = SHA1(sb.toString());
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
        tl = 1;
        rb = 1;
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
        String subgraph[][] = new String[rowLen][rowLen];
        int a = 0, b = 0;
        for(int counter = tl; counter < supergraph.length-rb; counter++) {
            b = 0;
            for(int counter2 = tl; counter2 < supergraph.length-rb; counter2++) {
                subgraph[a][b] = supergraph[counter][counter2];
                b++;
            }
            a++;
        }
        return subgraph;
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
        File f;
        int g1flag = 0;
        int g2flag = 0;
        int sflag = 0;
        int iflag = 0;
        int breakflag = 0;
        String g1file = "g1.txt";
        String g2file = "g2.txt";
        String sfile = "s.txt";
        String ifile = "i.txt";
        if(args.length != 0) {
            String store[] = new String[args.length];
            int counter = 0;
            while (counter < args.length) {
                if(args[counter].equals("-g1")) {
                    store[counter] = args[counter];
                    if(counter+1 != args.length) {
                        store[counter+1] = args[counter + 1];
                        f = new File(store[counter+1]);
                        if(!f.exists() || f.isDirectory()) {
							setTextToLabel("Input valid file for g1.");
                            System.out.println("Input valid file for g1.");
                            breakflag = 1;
                        } else {
                            g1flag = 1;
                            g1file = store[counter+1];
                        }
                    } else {
						setTextToLabel("Input valid file for g1.");
                        System.out.println("Input valid file for g1.");
                        breakflag = 1;
                        break;
                    }
                } else if(args[counter].equals("-g2")) {
                    store[counter] = args[counter];
                    if(counter+1 != args.length) {
                        store[counter+1] = args[counter + 1];
                        f = new File(store[counter+1]);
                        if(!f.exists() || f.isDirectory()) {
							setTextToLabel("Input valid file for g2.");
                            System.out.println("Input valid file for g2.");
                            breakflag = 1;
                        } else {
                            g2flag = 1;
                            g2file = store[counter+1];
                        }
                    } else {
						setTextToLabel("Input valid file for g2.");
                        System.out.println("Input valid file for g2.");
                        breakflag = 1;
                        break;
                    }
                } else if(args[counter].equals("-s")) {
                    store[counter] = args[counter];
                    if(counter+1 != args.length) {
                        store[counter+1] = args[counter + 1];
                        f = new File(store[counter+1]);
                        if(!f.exists() || f.isDirectory()) {
							setTextToLabel("Input valid file for s.");
                            System.out.println("Input valid file for s.");
                            breakflag = 1;
                        } else {
                            sflag = 1;
                            sfile = store[counter+1];
                        }
                    } else {
						setTextToLabel("Input valid file for s.");
                        System.out.println("Input valid file for s.");
                        breakflag = 1;
                        break;
                    }
                } else if(args[counter].equals("-i")) {
                    store[counter] = args[counter];
                    if(counter+1 != args.length) {
                        store[counter+1] = args[counter + 1];
                        f = new File(store[counter+1]);
                        if(!f.exists() || f.isDirectory()) {
							setTextToLabel("Input valid file for i.");
                            System.out.println("Input valid file for i.");
                            breakflag = 1;
                        } else {
                            iflag = 1;
                            ifile = store[counter+1];
                        }
                    } else {
						setTextToLabel("Input valid file for i.");
                        System.out.println("Input valid file for i.");
                        breakflag = 1;
                        break;
                    }
                }
                counter += 1;
            }
            if(breakflag == 1) {
                System.exit(1);
            }
            if(g1flag == 1 && g2flag == 1 && sflag == 1 && iflag == 1) {
            } else if(g1flag == 1 && g2flag == 0 && sflag == 0 && iflag == 0) {
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
				setTextToLabel("Files needed");
                System.out.println("Files needed");
                System.exit(0);
            }

        } else {
            try {
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

            /*x = 0;
             y = 0;
             String matrix3[][] = new String[1100][1100];
             FileInputStream inputStream3 = new FileInputStream(g2file);
             while (inputStream3.available() > 0) {
             current = (char) inputStream3.read();
             if(current == '\n') {
             x++;
             y = 0;
             continue;
             } else if(current == ' ') {
             continue;
             }
             matrix3[x][y] = String.valueOf(current);
             y++;
             }
             String matrixcopy3[][] = new String[x][x];
             for(int counter = 0; counter < x; counter++) {
             for(int counter2 = 0; counter2 < x; counter2++) {
             matrixcopy3[counter][counter2] = matrix3[counter][counter2];
             }
             }*/

            ObjectOutputStream out = new ObjectOutputStream(MyClient.getOutputStream());
            out.writeObject(matrixcopy);
            ObjectOutputStream out2 = new ObjectOutputStream(MyClient.getOutputStream());
            out2.writeObject(matrixcopy2);
            /*ObjectOutputStream out3 = new ObjectOutputStream(MyClient.getOutputStream());
             out3.writeObject(matrixcopy3);*/
            /*for(int counter = 0; counter < matrixcopy.length; counter++) {
             for(int counter2 = 0; counter2 < matrixcopy.length; counter2++) {
             System.out.print(matrixcopy[counter][counter2] + " ");
             }
             System.out.println();
             }
             System.out.println();
             for(int counter = 0; counter < matrixcopy2.length; counter++) {
             for(int counter2 = 0; counter2 < matrixcopy2.length; counter2++) {
             System.out.print(matrixcopy2[counter][counter2] + " ");
             }
             System.out.println();
             }
             System.out.println();*/
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
                String[][] modifiedG3 = bitCommit_HASH_SHA2_list(list1, list2, g3matrix);
                ObjectOutputStream outmG3 = new ObjectOutputStream(MyClient.getOutputStream());
                outmG3.writeObject(modifiedG3);
                outmG3.flush();
                if(Integer.parseInt(String.valueOf((char)num)) == 0) {
                    System.out.println("Run #" + runnum);
                    runnum++;
					setTextToLabel("Run #" + runnum + ": alpha and Q requested");
                    System.out.println("alpha and Q requested");
                    /*for(int counter = 0; counter < modifiedG3.length; counter++) {
                        for(int counter2 = 0; counter2 < modifiedG3.length; counter2++) {
                            System.out.print(modifiedG3[counter][counter2] + " ");
                        }
                        System.out.println();
                    }*/
                    ObjectOutputStream out5 = new ObjectOutputStream(MyClient.getOutputStream());
                    out5.writeObject(isofunc2);
                    out5.flush();
                    ObjectOutputStream out6 = new ObjectOutputStream(MyClient.getOutputStream());
                    out6.writeObject(g3matrix);
                    out6.flush();
                } else if(Integer.parseInt(String.valueOf((char)num)) == 1) {
                    System.out.println("Run #" + runnum);
                    runnum++;
					setTextToLabel("Run #" + runnum + ": pi and subgraph Q' requested");
                    System.out.println("pi and subgraph Q' requested");
                    /*for(int counter = 0; counter < modifiedG3.length; counter++) {
                        for(int counter2 = 0; counter2 < modifiedG3.length; counter2++) {
                            System.out.print(modifiedG3[counter][counter2] + " ");
                        }
                        System.out.println();
                    }*/
                    ObjectOutputStream out3 = new ObjectOutputStream(MyClient.getOutputStream());
                    out3.writeObject(pi);
                    out3.flush();
                    ObjectOutputStream out4 = new ObjectOutputStream(MyClient.getOutputStream());
                    out4.writeObject(g3primematrix);
                    out4.flush();
                } else if(Integer.parseInt(String.valueOf((char)num)) == 2) {
                    break;
                }
            }
        }
        catch (IOException e) {
			setTextToLabel(e.toString());
            System.out.println(e);
        }

        //System.exit(0);
    }
}
