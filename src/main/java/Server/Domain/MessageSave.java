package Server.Domain;

import Server.Model.Message;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
public class MessageSave {
    static int currentID=0;
    static String idPath="Messages/id.txt";
    public static List<Message> messages =new ArrayList<>();

    public static int getNextID() throws IOException {
        currentID = Integer.parseInt(Files.readString(Path.of("Messages/id.txt")));
        currentID++;
        Files.writeString(Path.of("Messages/id.txt"),String.valueOf(currentID));
        return currentID;
    }
}
