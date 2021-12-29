package MultiThread;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String request;
    public static JSONObject obj;


    public ClientHandler(Socket clientSocket) throws IOException {
        this.client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream() ,true);
    }

    @Override
    public void run() {
        try{
            while (true){
                request = in.readLine();
                if (request.equals("quit")) {
                    System.out.println("[SERVER] CLIENT DISCONNECTED EXITING");
                    break;
                }

                if (request.contains("@")){
                    addToMap();
                }

                if (request.contains("#")){
                    deleteFromMap();
                }

                else{
                    returnFromMap();
                }

                System.out.println("[SERVER] RESPONSE IS SET");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void addToMap() throws IOException {
        String[] newWord = request.split("@");
        if (obj.containsKey(newWord[0])){
            out.println("There was an error adding the word: Word already exists");
            out.flush();
        }
        else{
            obj.put(newWord[0], newWord[1]);
            applyToFile();
            out.println("Word was successfully added");
            out.flush();
        }

    }
    public void deleteFromMap() throws IOException {
        String[] newWord = request.split("#");
        String deleteWord = newWord[0];
        System.out.println(deleteWord);
        if (obj.containsKey(deleteWord)){
               obj.remove(deleteWord);
               applyToFile();
               out.println("Word successfully deleted");
               out.flush();
        }
        else{
            out.println("Can't delete word: Word doesn't exist");
            out.flush();
        }
    }
    public void returnFromMap(){
        if (obj.containsKey(request)){
            out.println(request+"@"+obj.get(request));
            out.flush();
        }
        else {
            out.println("Couldn't find the word, try with a different search parameter");
            out.flush();
        }
    }

    public void applyToFile() throws IOException {
        FileWriter file = new FileWriter("dictionary.json");
        file.write(obj.toJSONString());
        file.flush();
    }
}
