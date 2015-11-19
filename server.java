import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class server {
    public static void main (String[] args) {
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
                            writer.write(String.valueOf(value));
                            writer.flush();
                            System.out.println("Waiting for commitment");
                            ObjectInputStream inputmG3 = new ObjectInputStream(socket.getInputStream());
                            String[][] modifiedG3 = (String[][])inputmG3.readObject();
                            for(int counter = 0; counter < modifiedG3.length; counter++) {
                                for(int counter2 = 0; counter2 < modifiedG3.length; counter2++) {
                                    System.out.print(modifiedG3[counter][counter2] + " ");
                                }
                                System.out.println();
                            }
                            if(value == 0) {
                                System.out.println("Requesting for alpha and Q");
                            } else if(value == 1) {
                                System.out.println("Requesting for pi and subgraph Q'");
                            }
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
