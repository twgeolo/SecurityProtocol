import java.io.*;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import java.io.ObjectInputStream;

public class client {
    private static int tl = 0;
    private static int rb = 0;
    private static String[] isofunc;
    
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
    
    public static String[][] isomorphism(String[][] matrix) {
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
        String newMatrix[][] = new String[matrix.length][matrix.length];
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
    
    public static void main (String[] args) {
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
                            System.out.println("Input valid file for g1.");
                            breakflag = 1;
                        } else {
                            g1flag = 1;
                            g1file = store[counter+1];
                        }
                    } else {
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
                            System.out.println("Input valid file for g2.");
                            breakflag = 1;
                        } else {
                            g2flag = 1;
                            g2file = store[counter+1];
                        }
                    } else {
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
                            System.out.println("Input valid file for s.");
                            breakflag = 1;
                        } else {
                            sflag = 1;
                            sfile = store[counter+1];
                        }
                    } else {
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
                            System.out.println("Input valid file for i.");
                            breakflag = 1;
                        } else {
                            iflag = 1;
                            ifile = store[counter+1];
                        }
                    } else {
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
                    smatrix = isomorphism(g1matrixcopy);
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
                    System.out.println(e);
                }
            } else {
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
                smatrix = isomorphism(g1matrix);
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
            int num;
            int runnum = 1;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(MyClient.getInputStream()));
            while((num = stdIn.read()) != -1) {
                if(Integer.parseInt(String.valueOf((char)num)) == 0) {
                    System.out.println("Run #" + runnum);
                    runnum++;
                    System.out.println("alpha and Q requested");
                } else if(Integer.parseInt(String.valueOf((char)num)) == 1) {
                    System.out.println("Run #" + runnum);
                    runnum++;
                    System.out.println("pi and subgraph Q' requested");
                } else if(Integer.parseInt(String.valueOf((char)num)) == 2) {
                    break;
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        
        System.exit(0);
    }
}