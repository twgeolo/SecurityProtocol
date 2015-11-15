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
    
    public static void main (String[] args) {
        File f;
        int g1flag = 0;
        int g2flag = 0;
        int sflag = 0;
        int iflag = 0;
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
                        } else {
                            g1flag = 1;
                            g1file = store[counter+1];
                        }
                    } else {
                        System.out.println("Input valid file for g1.");
                        break;
                    }
                } else if(args[counter].equals("-g2")) {
                    store[counter] = args[counter];
                    if(counter+1 != args.length) {
                        store[counter+1] = args[counter + 1];
                        f = new File(store[counter+1]);
                        if(!f.exists() || f.isDirectory()) {
                            System.out.println("Input valid file for g2.");
                        } else {
                            g2flag = 1;
                            g2file = store[counter+1];
                        }
                    } else {
                        System.out.println("Input valid file for g2.");
                        break;
                    }
                } else if(args[counter].equals("-s")) {
                    store[counter] = args[counter];
                    if(counter+1 != args.length) {
                        store[counter+1] = args[counter + 1];
                        f = new File(store[counter+1]);
                        if(!f.exists() || f.isDirectory()) {
                            System.out.println("Input valid file for s.");
                        } else {
                            sflag = 1;
                            sfile = store[counter+1];
                        }
                    } else {
                        System.out.println("Input valid file for s.");
                        break;
                    }
                } else if(args[counter].equals("-i")) {
                    store[counter] = args[counter];
                    if(counter+1 != args.length) {
                        store[counter+1] = args[counter + 1];
                        f = new File(store[counter+1]);
                        if(!f.exists() || f.isDirectory()) {
                            System.out.println("Input valid file for i.");
                        } else {
                            iflag = 1;
                            ifile = store[counter+1];
                        }
                    } else {
                        System.out.println("Input valid file for i.");
                        break;
                    }
                }
                counter += 1;
            }
        } else {
            try {
                File file = new File("g1.txt");
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
                
                File file2 = new File("g2.txt");
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
                BufferedWriter bw2 = new BufferedWriter(fw2);
                String g2matrix[][] = new String[1000][1000];
                int range2 = (990 - 10) + 1;
                int randomNum2 = (int)(Math.random()*range2)+10;
                g2matrix = createMatrix(randomNum2);
                for(int row = 0; row < g2matrix.length; row++) {
                    for(int col =0; col < g2matrix[row].length; col++) {
                        bw2.write(g2matrix[row][col]);
                        bw2.write(" ");
                    }
                    bw2.write("\n");
                }
                bw2.close();
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
            String matrix2[][] = new String[1000][1000];
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
            
            ObjectOutputStream out = new ObjectOutputStream(MyClient.getOutputStream());
            out.writeObject(matrixcopy);
            ObjectOutputStream out2 = new ObjectOutputStream(MyClient.getOutputStream());
            out2.writeObject(matrixcopy2);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        System.exit(0);
    }
}