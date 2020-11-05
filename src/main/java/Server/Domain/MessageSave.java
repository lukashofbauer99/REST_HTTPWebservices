package Server.Domain;

import Server.Model.Message;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageSave {
    static int currentID=0;
    public static List<Message> messages =new ArrayList<>();

    public static int getNextID()
    {
        int oldId= currentID;
        currentID++;
        return oldId;
    }
}
