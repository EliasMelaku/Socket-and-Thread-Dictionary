package MultiThread;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

import static MultiThread.ClientHandler.obj;

public class CreateDictionary {
    @SuppressWarnings("unchecked")
    public static void createDictionary() {

        // three words in dictionary
        obj = new JSONObject();
        obj.put("java", "A programming language that is very strongly typed");
        obj.put("python", "A programming language that doesn't use semi colons");
        obj.put("c", "the 3rd letter of the Roman alphabet, and a programming language");

        try (FileWriter file = new FileWriter("dictionary.json")) {

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
