package MultiThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9900;
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        while (true){
            System.out.println("> ");
            String command = keyboardInput.readLine();

            out.println(command);


            if (command.equals("quit")) break;

            String serverResponse = input.readLine();
            System.out.println("Server says: " + serverResponse);



    //        JOptionPane.showMessageDialog(null, serverResponse);

        }

        socket.close();
        System.exit(0);

    }
}
