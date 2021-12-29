package MultiThread;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 9900;

    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(8);

    public static void main(String[] args) throws IOException, ParseException {
        ServerSocket listener = new ServerSocket(PORT);
        Socket client;


        File file = new File("dictionary.json");
        if (!file.exists()){
            CreateDictionary.createDictionary();
        }
        else {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader("dictionary.json");

            ClientHandler.obj = (JSONObject) jsonParser.parse(reader);
        }


        while (true){
            System.out.println("[SERVER] IS WAITING FOR A CLIENT");
            client = listener.accept();
            System.out.println("[SERVER] IS CONNECTED TO A CLIENT");
            ClientHandler clientThread = new ClientHandler(client);
            clients.add(clientThread);

            pool.execute(clientThread);

        }






//        client.close();
//        listener.close();


    }
}
