package Server.Domain;


import Server.Model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PersistanceHandler {

    static String messagePath="Messages/";

    static ObjectMapper mapper = new ObjectMapper();

    public static void persistMessage(Message message) throws JsonProcessingException {
        try {
            Files.writeString(Path.of(messagePath+message.getId()+".json"),mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message));
        } catch (IOException e) {
            System.out.println("An error occurred while saving the message");
            e.printStackTrace();
        }
    }

    public static void readMessage(int id) throws JsonProcessingException {
        try {
            mapper.readValue(Files.readString(Path.of(messagePath+id+".json")), Message.class);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the message");
            e.printStackTrace();
        }
    }


}
