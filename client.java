import java.io.*;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

public class client {
    public static void main (String[] args) {
        File f;
        if(args.length == 0) {
            //no args. must make own graphs
        } else {
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
                        }
                    } else {
                        System.out.println("Input valid file for i.");
                        break;
                    }
                }
                counter += 1;
            }
        }
        Socket MyClient;
        try {
            MyClient = new Socket("127.0.0.1", 9090);
            BufferedReader input = new BufferedReader(new InputStreamReader(MyClient.getInputStream()));
            String answer = input.readLine();
            JOptionPane.showMessageDialog(null, answer);

        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}