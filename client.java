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
            /*g1file = "g1.txt";
             g2file = "g2.txt";
             sfile = "s.txt";
             ifile = "i.txt";*/
            try {
                File file = new File("g1.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                String matrix[][] = new String[2][2];
                matrix[0][0]="0";
                matrix[1][0]="1";
                matrix[0][1]="2";
                matrix[1][1]="3";
                for(int row = 0; row < matrix.length; row++) {
                    for(int col =0; col < matrix[row].length; col++) {
                        bw.write(matrix[row][col]);
                        bw.write(" ");
                    }
                    bw.write("\n");
                }
                bw.close();
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
             String matrix[][] = new String[10000][10000];
            
            FileInputStream inputStream = new FileInputStream(g1file);
            char current;
            while (inputStream.available() > 0) {
                current = (char) inputStream.read();
                if(current == '\n') {
                    x++;
                    y = 0;
                    //System.out.println("\n");
                    continue;
                } else if(current == ' ') {
                    continue;
                }
                matrix[x][y] = String.valueOf(current);
                //System.out.print(matrix[x][y]);
                y++;
            }
            String matrixcopy[][] = new String[x][x];
            for(int counter = 0; counter < x; counter++) {
                for(int counter2 = 0; counter2 < x; counter2++) {
                    matrixcopy[counter][counter2] = matrix[counter][counter2];
                }
            }
            String matrix2[][] = new String[2][2];
            matrix2[0][0]="00";
            matrix2[1][0]="10";
            matrix2[0][1]="20";
            matrix2[1][1]="30";
            ObjectOutputStream out = new ObjectOutputStream(MyClient.getOutputStream());
            out.writeObject(matrixcopy);
            ObjectOutputStream out2 = new ObjectOutputStream(MyClient.getOutputStream());
            out2.writeObject(matrix2);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        /*catch (ClassNotFoundException e) {
         System.out.println(e);
         }*/
        System.exit(0);
    }
}