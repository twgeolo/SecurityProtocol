import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import java.io.*;
public class server {
    public static void main (String[] args) {
        try {
            ServerSocket listener = new ServerSocket(9090);
            try {
                while (true) {
                    Socket socket = listener.accept();
                    try {
                        //PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        //out.println("hello");
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        String[][] array = (String[][])input.readObject();
                        for(int counter = 0; counter < array.length; counter++) {
                            for(int counter2 = 0; counter2 < array[0].length; counter2++) {
                                System.out.print(array[counter][counter2] + " ");
                            }
                            System.out.println();
                        }
                        ObjectInputStream input2 = new ObjectInputStream(socket.getInputStream());
                        String[][] array2 = (String[][])input2.readObject();
                        for(int counter = 0; counter < array2.length; counter++) {
                            for(int counter2 = 0; counter2 < array2[0].length; counter2++) {
                                System.out.print(array2[counter][counter2] + " ");
                            }
                            System.out.println();
                        }
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
